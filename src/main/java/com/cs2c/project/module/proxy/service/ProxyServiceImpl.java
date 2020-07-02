package com.cs2c.project.module.proxy.service;

import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.config.JoenasConfig;
import com.cs2c.framework.web.domain.AjaxResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import cn.hutool.json.JSONUtil;
import net.sf.json.JSONObject;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.FileUtils;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.proxy.mapper.ProxyMapper;
import com.cs2c.project.module.proxy.domain.Proxy;
import com.cs2c.project.module.proxy.service.IProxyService;
import com.cs2c.project.module.proxy.util.JsonToSchameUitl;
import com.cs2c.common.support.Convert;

import org.springframework.web.multipart.MultipartFile;
import org.wiztools.xsdgen.ParseException;
import org.wiztools.xsdgen.XsdGen;
import org.xml.sax.SAXException;

/**
 * 代理配置 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-24
 */
@Service
public class ProxyServiceImpl implements IProxyService {
	@Autowired
	private JoenasConfig joenasConfig;

	// private String proxy_config_path = ruoYiConfig.getProxy_config_path();

	@Autowired
	private ProxyMapper proxyMapper;

//	@Autowired
//	private WdiodeConfigMapper wdiodeConfigMapper;
//
//	@Autowired
//	private ICommandInfoService commandInfoService;

	private codezip codeZip = new codezip();

	/**
	 * 查询代理配置信息
	 * 
	 * @param id 代理配置ID
	 * @return 代理配置信息
	 */
	@Override
	public Proxy selectProxyById(Integer id) {
		return proxyMapper.selectProxyById(id);
	}

	/**
	 * 查询代理配置列表
	 * 
	 * @param proxy 代理配置信息
	 * @return 代理配置集合
	 */
	@Override
	public List<Proxy> selectProxyList(Proxy proxy) {
		return proxyMapper.selectProxyList(proxy);
	}

	/**
	 * 新增代理配置
	 * 
	 * @param proxy 代理配置信息
	 * @return 结果
	 */
	@Override
	public int insertProxy(Proxy proxy) {
		String[] allow_ips = proxy.getAllows().split(";");
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(joenasConfig.getProxy_config_path(), true)));
			bw.write(getConfString(proxy));
			bw.newLine();
			// bw.append(sb.toString());
			bw.flush();
			bw.close();
			AddConf(allow_ips, proxy.getHost());
			return proxyMapper.insertProxy(proxy);
		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("创建proxy配置文件描述符时， 发生异常, 参数为 " + proxy, e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("写入proxy配置时， 发生异常, 参数为 " + proxy, e);
		}

		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 修改代理配置
	 * 
	 * @param proxy 代理配置信息
	 * @return 结果
	 */
	@Override
	public int updateProxy(Proxy proxy) {
		if (!proxy.getElementType().equals(proxyMapper.selectProxyById(proxy.getId()).getElementType())) {
			proxy.setElementTypeFileIn("");
			proxy.setElementTypeFileOut("");
		}
		if (proxy.getElementTypeFileIn() == null || proxy.getElementTypeFileIn().equals("")) {
			proxy.setElementTypeCheckIn("no");
		}
		if (proxy.getElementTypeFileOut() == null || proxy.getElementTypeFileOut().equals("")) {
			proxy.setElementTypeCheckOut("no");
		}

		Wini ini = null;
		String[] old_allow_ips = proxyMapper.selectProxyById(proxy.getId()).getAllows().split(";");
		String[] allow_ips = proxy.getAllows().split(";");
		try {
			ini = new Wini(new File(joenasConfig.getProxy_config_path()));
			ini.remove(proxyMapper.selectProxyById(proxy.getId()).getServiceId());
			ini.store();
			try {
				BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(joenasConfig.getProxy_config_path(), true)));
				bw.write(getConfString(proxy));
				bw.newLine();
				// bw.append(sb.toString());
				bw.flush();
				bw.close();
				DropConf(old_allow_ips, proxyMapper.selectProxyById(proxy.getId()).getHost());
				AddConf(allow_ips, proxy.getHost());
				return proxyMapper.updateProxy(proxy);
			} catch (FileNotFoundException e) {
				LogUtils.ERROR_LOG.error("修改proxy配置文件创建描述符时， 发生异常, 参数为 " + proxy, e);
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("修改proxy配置文件写入proxy配置时， 发生异常, 参数为 " + proxy, e);
			}
		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("修改proxy配置文件删除配置项时，代理 的配置文件不存在", e);
		} catch (InvalidFileFormatException e) {
			LogUtils.ERROR_LOG.error("修改proxy配置文件删除配置项时， 代理 的配置文件格式不正确", e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("修改proxy配置文件删除配置项时， 发生IO异常", e);
		}
		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 删除代理配置对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteProxyByIds(String ids) {
		try {

			for (Integer index : Convert.toIntArray(ids)) {
				Proxy proxy = proxyMapper.selectProxyById(index);

				DropConf(proxy.getAllows().split(";"), proxy.getHost());
				ini_remove(proxy.getServiceId());

				removefiles(proxy);

				proxyMapper.deleteProxyById(proxy.getId());
			}

		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("删除配置项时，代理 的配置文件不存在", e);
		} catch (InvalidFileFormatException e) {
			LogUtils.ERROR_LOG.error("删除配置项时， 代理 的配置文件格式不正确", e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("删除配置项时， 发生IO异常", e);
		}

		return UserConstants.CHANGE_1_RECORD;
	}

	public String generateServiceID() {
		Character[] charSequence = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'l', 'k', 'j', 'h', 'g', 'f',
				'd', 's', 'a', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A',
				'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', '0' };
		Random random = new Random(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		return "" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE) + "-"
				+ charSequence[random.nextInt(62)] + charSequence[random.nextInt(62)]
				+ charSequence[random.nextInt(62)];
	}

	/**
	 * 上传配置文件
	 *
	 * @param file
	 * @return 结果
	 */
	@Override
	public AjaxResult upload(String serviceId, String gofor, MultipartFile file) {
		Proxy proxy = proxyMapper.selectProxyByServiceId(serviceId);

		String proxy_path = null, req_xml = null;

		if (proxy.getElementType().equals("xml")) {
			proxy_path = joenasConfig.getProxy_xsd_path();
			req_xml = "req_elementtype_xmlxsd";
		} else if (proxy.getElementType().equals("json")) {
			proxy_path = joenasConfig.getProxy_json_path();
			req_xml = "req_elementtype_jsonschema";
		}

		if (file.isEmpty())
			return AjaxResult.error("不存在上传的文件");

		File tmp2path = null, tmp1path = null;
		try {
			LogUtils.ERROR_LOG.error(
					"proxy_config = " + joenasConfig.getProxy_config_path() + "      proxy_path = " + proxy_path);

			File path = new File(proxy_path);
			if (path.exists() && path.isDirectory() && path.canWrite()) {
				// pass
			} else {
				LogUtils.ERROR_LOG.error("上传" + proxy.getElementType() + "配置文件时， 目录 " + proxy_path + " 不存在");
				return AjaxResult.error("系统目录存在异常");
			}

			path = new File(proxy_path + File.separator + serviceId + gofor + "." + proxy.getElementType());
			if (path.exists() && !path.canWrite()) {
				LogUtils.ERROR_LOG.error("上传XSD配置文件时， 文件已存在并且不可修改， 文件为 " + path.getAbsolutePath());
				return AjaxResult.error("系统目录存在异常");
			}
			tmp1path = new File(path.getPath() + ".tmp1");
			FileUtils.copyInputStreamToFile(file.getInputStream(), tmp1path);
			BufferedReader reader;
			if (file.getOriginalFilename().endsWith(".xml") || file.getOriginalFilename().endsWith(".json")) {
				System.out.println(file.getOriginalFilename() + " : " + path.getPath());
				if (!path.exists())
					return AjaxResult.error("请先上传xsd/schame文件");
				if (!checkupload(path, file))
					return AjaxResult.error("文件格式检验未通过");
				tmp2path = new File(path.getPath() + ".tmp2");

				changeload(tmp2path, tmp1path, file.getOriginalFilename());
//				return AjaxResult.success();
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(tmp2path)));
			} else {
				reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			}
			String line = null;
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("上传XSD，写入 " + path.getAbsolutePath() + " 之后，关闭输出流时发生异常", e);
				return AjaxResult.error("写入失败");
			}

			Wini ini = new Wini(new File(joenasConfig.getProxy_config_path()));
			if (gofor.equals("in")) {
				proxy.setElementTypeFileIn(path.getAbsolutePath());
				proxy.setElementTypeCheckIn("yes");
				ini.put(proxy.getServiceId(), req_xml + "in", path.getAbsolutePath());
				ini.put(proxy.getServiceId(), "req_check_element_in", "yes");
			} else if (gofor.equals("out")) {
				proxy.setElementTypeFileOut(path.getAbsolutePath());
				proxy.setElementTypeCheckOut("yes");
				ini.put(proxy.getServiceId(), req_xml + "out", path.getAbsolutePath());
				ini.put(proxy.getServiceId(), "req_check_element_out", "yes");
			}
			ini.store();

			int rows = proxyMapper.updateProxy(proxy);
			if (rows == 0) {
				LogUtils.ERROR_LOG.error("写入xsd配置时，同步数据库状态失败, " + proxy);
			}

			try {
				reader.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("上传XSD配置时， 关闭BufferedReader流失败", e);
				return AjaxResult.error();
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("上传XSD配置时，读取配置文件内容失败", e);
			return AjaxResult.error("配置文件上传失败");
		} finally {
			if (tmp2path != null)
				tmp2path.delete();
			if (tmp1path != null)
				tmp1path.delete();
		}
		return AjaxResult.success();
	}

	/*
	 * 校验上传文件
	 */
	public Boolean checkupload(File localfile, MultipartFile checkfile) {
		String fileRealName = checkfile.getOriginalFilename();
		String tmp_path1 = localfile.getAbsolutePath() + ".tmp";
		File tmpfile1 = null;
		try {
			tmpfile1 = new File(tmp_path1);
//			File tmpfile2=new File(tmp_path2);
			FileUtils.copyInputStreamToFile(checkfile.getInputStream(), tmpfile1);
			if (fileRealName.endsWith(".xml")) {
				// filepath+fileNewName.concat(".xsd"
				Source xmlFile = new StreamSource(tmpfile1);
				Source xsdFile = new StreamSource(localfile);
				SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = schemaFactory.newSchema(xsdFile);
				Validator validator = schema.newValidator();
				validator.validate(xmlFile);
				FileUtils.forceDelete(tmpfile1);

			} else if (fileRealName.endsWith(".json")) {
				JsonNode jsonNode = getJsonNodeFromFile(tmpfile1.getPath());
				JsonNode schemaNode = getJsonNodeFromFile(localfile.getPath());
				ProcessingReport report = null;
				report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
				if (report.isSuccess()) {
					// 校验成功
					System.out.println("校验成功！");

				} else {
					System.out.println("校验失败！");
					return false;
				}

				FileUtils.forceDelete(tmpfile1);
			} else {
				return false;
			}
		} catch (IOException | SAXException e) {
			e.printStackTrace();

			return false;
		} finally {
			if (tmpfile1 != null)
				tmpfile1.delete();
		}
		return true;
	}

	public JsonNode getJsonNodeFromFile(String filePath) {
		JsonNode jsonNode = null;
		try {
			jsonNode = new JsonNodeReader().fromReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonNode;
	}

	/**
	 * 
	 * @param goFile     转换至
	 * @param sourceFile 源文件
	 * @param type       文件名
	 * @return 暂时没啥用
	 */
	public File changeload(File goFile, File sourceFile, String type) {

		try {
			if (type.endsWith(".xml")) {

				XsdGen gen = new XsdGen();
				gen.parse(sourceFile);
				gen.write(new FileOutputStream(goFile));
				FileUtils.forceDelete(sourceFile);

			} else {
				String json = null;
				JsonToSchameUitl jsonToSchame = new JsonToSchameUitl();

				try {
					json = JsonToSchameUitl.JsonToStr(sourceFile.getPath());
					JSONObject jsonObject1 = JSONObject.fromObject(json);

					jsonToSchame.write(JSONUtil.formatJsonStr(jsonToSchame.generateJsonSchema(jsonObject1).toString()),
							goFile.getPath());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new File("");
	}

	/**
	 * 校验serviceId
	 */
	@Override
	public String checkServiceId(String serviceId, int id) {
		if (serviceId == null || serviceId.trim().length() == 0) {
			return UserConstants.EXCEPTION;
		}

		Proxy proxy = proxyMapper.selectProxyByServiceId(serviceId);

		if (proxy != null && proxy.getId() != id)
			return UserConstants.EXCEPTION;

		if (serviceId.matches(UserConstants.SERVICEID_PATTERN)) {
			return UserConstants.NORMAL;
		}
		return UserConstants.EXCEPTION;
	}

	@Override
	public AjaxResult downloadF() {
		List<Proxy> list = selectProxyList(new Proxy());
		StringBuffer sb = new StringBuffer();
		for (Proxy proxy : list) {
			sb.append(proxy.getServiceId() + "\t");

			if (proxy.getServiceName().length() >= 8)
				sb.append(proxy.getServiceName() + "\t");
			else
				sb.append(proxy.getServiceName() + "\t\t");

			sb.append(proxy.getMethod() + "\t");

			sb.append(proxy.getProtocol() + "\t");

			if (proxy.getHost().length() >= 8)
				sb.append(proxy.getHost() + "\t");
			else
				sb.append(proxy.getHost() + "\t\t");

			sb.append(proxy.getProtocol() + "\t");
			sb.append(proxy.getPort() + "\t");
			sb.append(proxy.getAttachment() + "\t");
			sb.append(proxy.getProtocol() + "\t");

			sb.append(proxy.getElementType() + "\t");
			sb.append(proxy.getElementTypeCheckIn() + "\t");
			sb.append(proxy.getElementTypeCheckOut() + "\t");
			sb.append(proxy.getAttachmentFn() + "\t");
			sb.append(proxy.getAttachmentCon() + "\t");
			sb.append(proxy.getPath() + "\n");

		}
		// System.out.println("stdout :"+stdout);

		Calendar cal = Calendar.getInstance();
		// System.out.println("Calendar");
		String fname = "数据交换服务注册-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + ".zip";
		// System.out.println(fname);
		byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
		// System.out.println(data.length);
		return AjaxResult.success2(fname, data);
	}

	@Override
	public int AddConf(String[] IpList, String host) {
		StringBuilder sb = new StringBuilder();
		for (String ip : IpList) {
			if (ip.length() != 0) {
				sb.append("Allow " + ip).append(System.getProperty("line.separator"));
			}
		}

		File conf = new File("/etc/wdiodeptcn.conf");
		File filter = new File("/etc/wdiodeptcn_filter");
		if (filter.exists()) {
			if (host.length() != 0) {
				try {
					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream("/etc/wdiodeptcn_filter", true)));
					bw.write(host);
					bw.newLine();
					bw.flush();
					bw.close();
				} catch (IOException e) {
					System.out.println("proxy AddConf filter erro : " + e);
				}
			}
		}
		if (!conf.exists()) {
			System.out.println("dont have /etc/wdiodeptcn.conf ");
			return 0;
		} else {
			try {
				BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream("/etc/wdiodeptcn.conf", true)));
				bw.write(sb.toString());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				System.out.println("proxy AddConf wdiodeptcn erro :" + e);
			}
		}
		return 1;
	}

	@Override
	public int DropConf(String[] IpList, String host) {
		for (String ip : IpList) {
			if (ip.length() != 0) {
				String command = new String("sed -i '0,/^Allow[[:space:]]" + ip + "/{/^Allow[[:space:]]" + ip
						+ "/d}' /etc/wdiodeptcn.conf"); // 删去第一个Allow <ip>
//            System.out.println("proxy DropConf command:"+command);
				CommandLine commandLine = CommandLine.parse(command);
				try {
					ShellUtils.run(commandLine, 5000, false);
				} catch (IOException e) {
				}
			}
		}
		if (host.length() != 0) {
			String command = new String("sed -i '0,/^" + host + "/{/^" + host + "/d}' /etc/wdiodeptcn_filter");
//            System.out.println("command:"+command);
			CommandLine commandLine = CommandLine.parse(command);
			try {
				ShellUtils.run(commandLine, 5000, false);
			} catch (IOException e) {
			}
		}

		return 1;
	}

	/*
	 * 获取service文件写入信息
	 */
	public String getConfString(Proxy proxy) {
		StringBuilder sb = new StringBuilder();
		String method = proxy.getMethod().replace(",", ";");

		if (proxy.getElementTypeFileIn() == null || proxy.getElementTypeFileIn().trim().equals(""))
			proxy.setElementTypeFileIn(" ");
		if (proxy.getElementTypeFileOut() == null || proxy.getElementTypeFileOut().trim().equals(""))
			proxy.setElementTypeFileOut(" ");

		sb.append("[").append(proxy.getServiceId()).append("]").append(System.getProperty("line.separator"))
				.append("req_method=").append(method).append(System.getProperty("line.separator"))
				.append("req_protocol=").append(proxy.getProtocol()).append(System.getProperty("line.separator"))
				.append("req_host=").append(proxy.getHost()).append(System.getProperty("line.separator"))
				.append("req_allow=").append(proxy.getAllows()).append(System.getProperty("line.separator"))
				// .append("req_path=").append(proxy.getPath()).append(System.getProperty("line.separator"))
				.append("req_port=").append(proxy.getPort()).append(System.getProperty("line.separator"))

				.append("req_elementtype=").append(proxy.getElementType()).append(System.getProperty("line.separator"))

				.append("req_check_element_in=").append(proxy.getElementTypeCheckIn())
				.append(System.getProperty("line.separator"))
				.append(proxy.getElementType().equals("xml") ? "req_elementtype_xmlxsdin="
						: "req_elementtype_jsonschemain=")
				.append(proxy.getElementTypeFileIn()).append(System.getProperty("line.separator"))

				.append("req_check_element_out=").append(proxy.getElementTypeCheckOut())
				.append(System.getProperty("line.separator"))
				.append(proxy.getElementType().equals("xml") ? "req_elementtype_xmlxsdout="
						: "req_elementtype_jsonschemaout=")
				.append(proxy.getElementTypeFileOut()).append(System.getProperty("line.separator"))

				.append("req_backgate=").append(proxy.getReqBackgate()).append(System.getProperty("line.separator"))
				.append("req_header=").append(proxy.getReqHeader()).append(System.getProperty("line.separator"))
				.append("req_header_c1=").append(proxy.getReqHeaderC1()).append(System.getProperty("line.separator"))
				.append("req_header_c2=").append(proxy.getReqHeaderC2()).append(System.getProperty("line.separator"))
				.append("req_urllock=").append(proxy.getReqUrllock()).append(System.getProperty("line.separator"))
				.append("req_path=").append(proxy.getReqPath()).append(System.getProperty("line.separator"))

				.append("req_attachment=").append(proxy.getAttachment()).append(System.getProperty("line.separator"))
				.append("req_attachment_fn=").append(proxy.getAttachmentFn())
				.append(System.getProperty("line.separator")).append("req_attachment_con=")
				.append(proxy.getAttachmentCon()).append(System.getProperty("line.separator"));

		return sb.toString();
	}

	/*
	 * 删除对应配置文件
	 */
	public void removefiles(Proxy proxy) throws FileNotFoundException {
		String file_path = proxy.getElementType().equals("xsd") ? joenasConfig.getProxy_xsd_path()
				: joenasConfig.getProxy_json_path();

		if (proxy.getElementTypeFileIn() != null && !proxy.getElementTypeFileIn().trim().equals(""))
			rm(file_path + File.separator + proxy.getServiceId() + "in." + proxy.getElementType());

		if (proxy.getElementTypeFileOut() != null && !proxy.getElementTypeFileOut().trim().equals(""))
			rm(file_path + File.separator + proxy.getServiceId() + "out." + proxy.getElementType());
//		File path = new File(proxy_xsd_path + File.separator + proxy.getServiceId() + "in.xsd");
//		if (path.exists() && path.isFile() && path.canWrite()) {
//			// pass
//			try {
//				path.delete();
//			} catch (SecurityException e) {
//				LogUtils.ERROR_LOG.error("删除代理配置文件的xsd文件时， 发生异常， 请排查原因， 并清理 " + path.getAbsolutePath());
//			}
//		} else {
//			LogUtils.ERROR_LOG.error("删除配置时，检查XSD配置的有效性检查失败， 文件 " + path.getAbsolutePath() + " 不存在 或 存在异常");
//		}
	}

	public void rm(String path) throws FileNotFoundException {
		File file = new File(path);
		if (file.exists() && file.isFile() && file.canWrite()) {
			// pass
			try {
				file.delete();
			} catch (SecurityException e) {
				LogUtils.ERROR_LOG.error("删除代理配置文件的xsd文件时， 发生异常， 请排查原因， 并清理 " + file.getAbsolutePath());
			}
		} else {
			LogUtils.ERROR_LOG.error("删除配置时，检查XSD配置的有效性检查失败， 文件 " + file.getAbsolutePath() + " 不存在 或 存在异常");
			throw new FileNotFoundException();
		}
	}

	/*
	 * 删除service内对应信息
	 */
	public void ini_remove(String Id) throws InvalidFileFormatException, IOException {
		Wini ini = new Wini(new File(joenasConfig.getProxy_config_path()));
		ini.remove(Id);
		ini.store();
	}
}
