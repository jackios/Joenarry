package com.cs2c.project.module.wdiodeumtConfig.service;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.config.Cs2cConfig;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.project.module.wdiodeumtConfig.domain.EditViewWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.ParseWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.ViewWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.WdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.mapper.WdiodeumtConfigMapper;
import com.cs2c.project.system.config.service.IConfigService;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 端口转发配置文件 服务层实现
 *
 * @author Joenas
 * @date 2020-03-05
 */
@Service
public class WdiodeumtConfigServiceImpl implements IWdiodeumtConfigService {
	@Autowired
	private WdiodeumtConfigMapper wdiodeumtConfigMapper;

	@Autowired
	private ICommandInfoService commandInfoService;

	@Autowired
	private IConfigService configService;
	private codezip codeZip = new codezip();

	@Value("${joenas.wdiodeumt_config_path}")
	private String wdiodeumtConfigPath;
	/*
	 * @Value("${cs2c.fileFerry}") private String fileFerry;
	 */

	/**
	 * 查询端口转发配置文件信息
	 *
	 * @param id 端口转发配置文件ID
	 * @return 端口转发配置文件信息
	 */
	@Override
	public WdiodeumtConfig selectWdiodeumtConfigById(Integer id) {
		return wdiodeumtConfigMapper.selectWdiodeumtConfigById(id);
	}

	@Override
	public List<WdiodeumtConfig> selectMulWdiodeumtConfigList(Integer number) {
		return wdiodeumtConfigMapper.selectWdiodeumtConfigList(number);
	}

	/**
	 * 查询端口转发配置文件列表
	 *
	 * @param viewWdiodeumtConfig 端口转发配置文件信息
	 * @return 端口转发配置文件集合
	 */
	@Override
	public List<ViewWdiodeumtConfig> selectWdiodeumtConfigList(ViewWdiodeumtConfig viewWdiodeumtConfig) {
		List<WdiodeumtConfig> wdiodeumtConfig = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
		if (wdiodeumtConfig.size() == 0) {
			return new ArrayList<>();
		}
		return (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE.parseWdiodeumtConfig(wdiodeumtConfig.get(0))
				.get("configinfo");
	}

	@Override
	public List<ViewWdiodeumtConfig> selectWdiodeumtConfigList(ViewWdiodeumtConfig viewWdiodeumtConfig, Integer id) {
		WdiodeumtConfig wdiodeumtConfig = wdiodeumtConfigMapper.selectWdiodeumtConfigById(id); // 查询最后一条记录

		return (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE.parseWdiodeumtConfig(wdiodeumtConfig)
				.get("configinfo");

	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateWdiodeumtConfig(EditViewWdiodeumtConfig editViewWdiodeumtConfig, String reversion) {
		List<WdiodeumtConfig> wdiodeumtConfigs = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
		// System.out.println(wdiodeumtConfigs.toString());
		List<ViewWdiodeumtConfig> configinfo = (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE
				.parseWdiodeumtConfig(wdiodeumtConfigs.get(0)).get("configinfo");
//        WdiodeumtConfig oldWdiodeumtConfig = new WdiodeumtConfig();
//        oldWdiodeumtConfig.setContents(wdiodeumtConfigs.get(0).getContents());
//        oldWdiodeumtConfig.setDataTime(wdiodeumtConfigs.get(0).getDataTime());
//        oldWdiodeumtConfig.setDescription(wdiodeumtConfigs.get(0).getDescription());
//        oldWdiodeumtConfig.setId(wdiodeumtConfigs.get(0).getId());
//        oldWdiodeumtConfig.setCol1(wdiodeumtConfigs.get(0).getCol1());
//        oldWdiodeumtConfig.setCol2(wdiodeumtConfigs.get(0).getCol2());
//        oldWdiodeumtConfig.setCol3(wdiodeumtConfigs.get(0).getCol3());
		WdiodeumtConfig oldWdiodeumtConfig = wdiodeumtConfigs.get(0);
		Date current_date = new Date();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ViewWdiodeumtConfig oldViewWdiodeumtConfig = new ViewWdiodeumtConfig();

		boolean isSuccess = false;
		for (ViewWdiodeumtConfig conf : configinfo) {
			if (conf.getW_key().equals(editViewWdiodeumtConfig.getW_key())) {
				/*
				 * oldViewWdiodeumtConfig.setW_time(conf.getW_time());
				 * oldViewWdiodeumtConfig.setDescription(conf.getDescription());
				 * oldViewWdiodeumtConfig.setW_value(conf.getW_value());
				 * oldViewWdiodeumtConfig.setW_key(conf.getW_key());
				 */

				conf.setW_value(editViewWdiodeumtConfig.getW_value());
				conf.setDescription(editViewWdiodeumtConfig.getDescription());
				conf.setW_time(current_date);
				isSuccess = true;

				break;
			}
		}

		if (!isSuccess) {
			return UserConstants.CHANGE_0_RECORD;
		}

		String contents = switchWdiodeumtConfigToConfString(configinfo, time.format(current_date));
		wdiodeumtConfigs.get(0).setContents(contents);

		int changeStatus = -1;
		if ("yes".equals(reversion)) {
			// 保存新版本

			wdiodeumtConfigs.get(0).setDataTime(current_date);
			wdiodeumtConfigs.get(0).setDescription(editViewWdiodeumtConfig.getComment());
			wdiodeumtConfigs.get(0).setId(null);

			changeStatus = wdiodeumtConfigMapper.insertWdiodeumtConfig(wdiodeumtConfigs.get(0));
		} else {
			// 更新当前版本
			changeStatus = wdiodeumtConfigMapper.updateWdiodeumtConfig(wdiodeumtConfigs.get(0));
		}

		if (changeStatus == UserConstants.CHANGE_1_RECORD) {

			// int persistStatus = persist_wdiodeumt_config("wdiodeumt_config",
			// editViewWdiodeumtConfig.getW_key(), editViewWdiodeumtConfig.getW_value(),
			// editViewWdiodeumtConfig.getComment(), "yes", "alter");
			int persistStatus = persistWdiodeumtConfig(wdiodeumtConfigs);

			if (persistStatus == UserConstants.CHANGE_0_RECORD) {
				LogUtils.ERROR_LOG.error("持久化修改到配置文件失败, 回滚数据库");
				/*
				 * for (ViewWdiodeumtConfig conf : configinfo) { if
				 * (conf.getW_key().equals(oldViewWdiodeumtConfig.getW_key())) {
				 * conf.setW_value(oldViewWdiodeumtConfig.getW_value());
				 * conf.setDescription(oldViewWdiodeumtConfig.getDescription());
				 * conf.setW_time(oldViewWdiodeumtConfig.getW_time());
				 * 
				 * break; } }
				 */
				// 原来的代码中进行数据库回滚操作时。并没有还原wdiodeumtConfigs的属性值
				changeStatus = wdiodeumtConfigMapper.updateWdiodeumtConfig(oldWdiodeumtConfig);
				// LogUtils.ERROR_LOG.error(oldViewWdiodeConfig.toString());
				if (changeStatus == UserConstants.CHANGE_0_RECORD) {
					LogUtils.ERROR_LOG.error("回滚数据库失败");
					return UserConstants.CHANGE_0_RECORD;
				}

				LogUtils.ERROR_LOG.error("回滚数据库成功");
				return UserConstants.CHANGE_0_RECORD;
			}

			return UserConstants.CHANGE_1_RECORD;
		} else {
			LogUtils.ERROR_LOG.error("持久化修改到数据库失败");
			return UserConstants.CHANGE_0_RECORD;
		}
	}

	private int persistWdiodeumtConfig(List<WdiodeumtConfig> wdiodeumtConfigs) {
		StringBuilder stringBuilder = new StringBuilder();
		File file = new File(wdiodeumtConfigPath);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
//			String updateTime = (wdiodeumtConfigs.get(0).getDataTime().toString()) == null ? ""
//					: wdiodeumtConfigs.get(0).getDataTime().toString();
//			String description = (wdiodeumtConfigs.get(0).getDescription()) == null ? ""
//					: wdiodeumtConfigs.get(0).getDescription();
//			stringBuilder.append("#").append("更新时间:").append(updateTime).append("\n").append("#").append(description)
//					.append("\n");
//			@SuppressWarnings("unchecked")
//			List<ViewWdiodeumtConfig> configInfos = (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE
//					.parseWdiodeumtConfig(wdiodeumtConfigs.get(0)).get("configinfo");
//			configInfos.forEach(configItem -> {
//
//				if (configItem.getDescription() != null && !configItem.getDescription().equals("")) {
//					stringBuilder.append("#").append(configItem.getDescription()).append("\n");
//				}
//				stringBuilder.append(configItem.getW_key()).append("=").append(configItem.getW_value()).append("\n");
//			});
			// fileOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
			fileOutputStream.write(wdiodeumtConfigs.get(0).getContents().getBytes(StandardCharsets.UTF_8));
			fileOutputStream.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private String switchWdiodeumtConfigToJsonString(List<ViewWdiodeumtConfig> wdiodeumtConfigs, String current_date) {
		StringBuilder sb = new StringBuilder("{");
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ViewWdiodeumtConfig viewWdiodeumtConfig : wdiodeumtConfigs) {
			sb.append("\"").append(viewWdiodeumtConfig.getW_key()).append("\":").append("{").append("\"description\"")
					.append(":\"").append(viewWdiodeumtConfig.getDescription()).append("\",").append("\"value\"")
					.append(":\"").append(viewWdiodeumtConfig.getW_value()).append("\",").append("\"data_time\"")
					.append(":\"").append(time.format(viewWdiodeumtConfig.getW_time())).append("\"").append("},");
		}

		return sb.append("}").toString();
	}

	private String switchWdiodeumtConfigToConfString(List<ViewWdiodeumtConfig> wdiodeumtConfigs, String current_date) {
		StringBuilder sb = new StringBuilder("");

		for (ViewWdiodeumtConfig viewWdiodeumtConfig : wdiodeumtConfigs) {
			// System.out.println(viewWdiodeumtConfig.toString());
			if (!(viewWdiodeumtConfig.getDescription() == null || viewWdiodeumtConfig.getDescription().equals(""))) {
				sb.append("#").append(viewWdiodeumtConfig.getDescription())
						.append(System.getProperty("line.separator"));
			}
			sb.append(viewWdiodeumtConfig.getW_key()).append("=").append(viewWdiodeumtConfig.getW_value())
					.append(System.getProperty("line.separator"));
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	private String switchWdiodeumtConfigToJsonStringForExport(List<ViewWdiodeumtConfig> wdiodeumtConfigs) {
		StringBuilder sb = new StringBuilder("{");
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ViewWdiodeumtConfig viewWdiodeumtConfig : wdiodeumtConfigs) {
			sb.append("    \"").append(viewWdiodeumtConfig.getW_key()).append("\":").append("{\n")
					.append("        \"description\"").append(":\"").append(viewWdiodeumtConfig.getDescription())
					.append("\",\n").append("        \"value\"").append(":\"").append(viewWdiodeumtConfig.getW_value())
					.append("\",\n").append("        \"data_time\"").append(":\"")
					.append(time.format(viewWdiodeumtConfig.getW_time())).append("\"\n").append("    },\n");
		}

		sb.append("    \"").append("version_description").append("\":").append("\"如果上传配置，请记得修改此处的版本信息哦\"\n");
		return sb.append("}").toString();
	}

	@Override
	public AjaxResult reversion(Integer id) {
		WdiodeumtConfig wdiodeumtConfig = wdiodeumtConfigMapper.selectWdiodeumtConfigById(id);
		wdiodeumtConfig.setId(null);
		wdiodeumtConfig.setDescription("版本回退，自动生成的注释");
		wdiodeumtConfig.setDataTime(new Date());

		// 查询最后一条记录
		List<WdiodeumtConfig> wdiodeumtConfigLast = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
		// List<ViewWdiodeumtConfig> configinfoLast = (List<ViewWdiodeumtConfig>)
		// ParseWdiodeumtConfig.INSTANCE.parseWdiodeumtConfig(wdiodeumtConfigLast.get(0)).get("configinfo");

		// 执行配置文件内容回滚
		// List<ViewWdiodeumtConfig> configinfo = (List<ViewWdiodeumtConfig>)
		// ParseWdiodeumtConfig.INSTANCE.parseWdiodeumtConfig(wdiodeumtConfig).get("configinfo");

		Date current_date = new Date();

		/*
		 * ArrayList<String> successKeys = new ArrayList<>(); boolean isSuccess = true;
		 * for (ViewWdiodeumtConfig conf : configinfo) { conf.setW_time(current_date);
		 * int persistStatus = persist_wdiodeumt_config("wdiodeumt_config",
		 * conf.getW_key(), conf.getW_value(), conf.getDescription(), "yes", "alter");
		 * if (persistStatus == UserConstants.CHANGE_0_RECORD) {
		 * LogUtils.ERROR_LOG.error("回滚配置文件失败， 配置项为 " + conf.getW_key()); isSuccess =
		 * false; break; } else { successKeys.add(conf.getW_key().trim());
		 * LogUtils.ERROR_LOG.error("回滚配置文件 - 配置项 " + conf.getW_key() + " 还原成功"); } }
		 */
		List<WdiodeumtConfig> wdiodeumtConfigs = new ArrayList<>();
		wdiodeumtConfigs.add(wdiodeumtConfig);
		// 执行成功返回1，执行失败返回0
		int persistStatus = persistWdiodeumtConfig(wdiodeumtConfigs);

		if (persistStatus == 1) {
			// 执行数据库记录持久化
			LogUtils.ERROR_LOG.error("配置文件回滚成功");
			int row = wdiodeumtConfigMapper.insertWdiodeumtConfig(wdiodeumtConfig);
			if (row == 0) {
				return AjaxResult.error("配置文件回滚成功，持久化至数据库失败");
			} else {
				return AjaxResult.success();
			}
		} else {
			LogUtils.ERROR_LOG.error("配置文件回滚存在异常，正在进行数据库回滚");

			/*
			 * for (ViewWdiodeumtConfig conf : configinfo) { if
			 * (successKeys.contains(conf.getW_key().trim())) { continue; } for
			 * (ViewWdiodeumtConfig oldConf : configinfoLast) { if
			 * (oldConf.getW_key().trim().equals(conf.getW_key().trim())) {
			 * conf.setW_value(oldConf.getW_value());
			 * conf.setDescription(oldConf.getDescription());
			 * conf.setW_time(oldConf.getW_time()); } } }
			 */
			// 配置文件更新失败，进行数据库回滚
			wdiodeumtConfigLast.get(0).setId(null);
			int row = wdiodeumtConfigMapper.insertWdiodeumtConfig(wdiodeumtConfigLast.get(0));
			if (row == 0) {
				return AjaxResult.error("配置文件回滚失败，数据库回滚失败，请联系管理员");
			} else {
				return AjaxResult.success();
			}

		}
	}

	@Override
	public int reinit() {
		int persistStatus = persist_wdiodeumt_config("wdiodeumt_config", "", "", "", "yes", "reinit");
		if (persistStatus == UserConstants.CHANGE_0_RECORD) {
			LogUtils.ERROR_LOG.error("还原配置文件失败, 请重试");
			return UserConstants.CHANGE_0_RECORD;
		}

		LogUtils.ERROR_LOG.error("重新初始化配置文件成功");
		return UserConstants.CHANGE_1_RECORD;
	}

	@Override
	public AjaxResult export(Integer id) {
		List<WdiodeumtConfig> wdiodeumtConfigs = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
		List<ViewWdiodeumtConfig> configinfo = (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE
				.parseWdiodeumtConfig(wdiodeumtConfigs.get(0)).get("configinfo");

		OutputStream out = null;

		String filename = encodingFilename("核心配置");
		try {
			out = new FileOutputStream(getAbsoluteFile(filename));
			String jsonStr = switchWdiodeumtConfigToConfString(configinfo, "");
			out.write(jsonStr.getBytes());
			out.flush();
			return AjaxResult.success(filename);
		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("获取导出核心配置的绝对路径时发生异常", e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("导出核心配置，写入临时配置文件时发生异常", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					LogUtils.ERROR_LOG.error("导出核心配置，关闭输出流时发生异常", e1);
				}
			}
		}

		return AjaxResult.error("导出核心配置失败，请联系管理员！");
	}

	/**
	 * 编码文件名
	 */
	private String encodingFilename(String filename) {
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd");

		filename = UUID.randomUUID().toString() + "_" + time.format(new Date()) + "-" + filename + ".json";
		return filename;
	}

	/**
	 * 获取下载路径
	 *
	 * @param filename 文件名称
	 */
	public String getAbsoluteFile(String filename) {
		String downloadPath = Cs2cConfig.getDownloadPath() + filename;
		File desc = new File(downloadPath);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}

	@Override
	public AjaxResult upload(MultipartFile file) {
		try {

			if (file.isEmpty()) {
				return AjaxResult.error("文件为不存在");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			StringBuilder config = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				config.append(line).append(System.getProperty("line.separator"));
			}

			try {
				reader.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("上传配置文件时， 关闭BufferedReader流失败", e);
				return AjaxResult.error();
			}

//			JSONObject jsonObject = JSONObject.parseObject(config.toString());
//			String description = jsonObject.getString("version_description");
//			jsonObject.remove("version_description");

			WdiodeumtConfig wdiodeumtConfig = new WdiodeumtConfig();
			wdiodeumtConfig.setContents(config.toString());
//
//			@SuppressWarnings("unchecked")
//			List<ViewWdiodeumtConfig> configinfo = (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE
//					.parseWdiodeumtConfig(wdiodeumtConfig).get("configinfo");
//

			Date current_date = new Date();
			//
//			// 上传配置时， 将所有配置项的更新时间 设置为当前时间点
//			for (ViewWdiodeumtConfig cc : configinfo) {
//				cc.setW_time(current_date);
//			}
//
//			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String contents = switchWdiodeumtConfigToJsonString(configinfo, time.format(current_date));
//			wdiodeumtConfig.setContents(contents);
			wdiodeumtConfig.setDataTime(current_date);
			wdiodeumtConfig.setDescription("");
			wdiodeumtConfig.setId(null);

//			List<WdiodeumtConfig> oldWdiodeumtConfig = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
//			List<ViewWdiodeumtConfig> oldConfiginfo = (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE
//					.parseWdiodeumtConfig(oldWdiodeumtConfig.get(0)).get("configinfo");

//			if (1 == 1)
//				return AjaxResult.error("上传配置文件生效失败");
			// 将配置文件内容存入数据库
			if (UserConstants.CHANGE_0_RECORD == wdiodeumtConfigMapper.insertWdiodeumtConfig(wdiodeumtConfig)) {
				return AjaxResult.error("上传配置文件生效失败");
			}

			/*
			 * HashMap<String, String[]> record = new HashMap<>(); int count = 0, flag = 1;
			 * for (ViewWdiodeumtConfig cc : configinfo) { if (flag == 0) { break; }
			 * 
			 * for (ViewWdiodeumtConfig ccc : oldConfiginfo) { if (cc.getW_key() != null &&
			 * cc.getW_key().equals(ccc.getW_key())) { if (cc.getW_value() != null &&
			 * cc.getW_value().equals(ccc.getW_value())) { //break; } else if
			 * (ccc.getW_value() != null && ccc.getW_value().equals(cc.getW_value())) {
			 * //break; } else { int persistStatus =
			 * persist_wdiodeumt_config("wdiodeumt_config", cc.getW_key(), cc.getW_value(),
			 * cc.getDescription(), "yes", "alter"); if (persistStatus ==
			 * UserConstants.CHANGE_1_RECORD) { count++; record.put(cc.getW_key(), new
			 * String[]{ccc.getW_value(), cc.getW_value()}); } else { flag = 0; } //break; }
			 * break; } } }
			 */
			// LogUtils.ERROR_LOG.error(record.toString());
			// LogUtils.ERROR_LOG.error(flag + "");

			// LogUtils.ERROR_LOG.error(jsonObject.toJSONString());
			List<WdiodeumtConfig> wdiodeumtConfigs = new ArrayList<>();
			wdiodeumtConfigs.add(wdiodeumtConfig);
			int flag = persistWdiodeumtConfig(wdiodeumtConfigs);
			if (flag == 1) {
				return AjaxResult.success();
			} else {
				List<WdiodeumtConfig> lastRecord = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
				if (lastRecord.size() > 0) {
					// LogUtils.ERROR_LOG.error(lastRecord.toString());
					int changeStatus = wdiodeumtConfigMapper.deleteWdiodeumtConfigById(lastRecord.get(0).getId());
					// LogUtils.ERROR_LOG.error(oldViewWdiodeConfig.toString());
					if (changeStatus == UserConstants.CHANGE_1_RECORD) {
						LogUtils.ERROR_LOG.error("回滚数据库成功");
						return AjaxResult.error("更新失败， 回滚数据库成功");
					}
				}

				LogUtils.ERROR_LOG.error("回滚数据库失败");
				return AjaxResult.error("更新失败， 回滚数据库失败");
			}
			/*
			 * if (flag == 0) { if (count == 0) {
			 * LogUtils.ERROR_LOG.error("更新配置文件失败，准备回滚数据库"); } else { StringBuilder sb = new
			 * StringBuilder(); for (String kk : record.keySet()) { sb.append(kk + "=[" +
			 * record.get(kk)[0] + "," + record.get(kk)[1] + "];"); }
			 * LogUtils.ERROR_LOG.error("更新配置文件失败，准备回滚数据库, 已经变更的配置项为 " + sb.toString() +
			 * "； 请联系管理员处理"); }
			 * 
			 * List<WdiodeumtConfig> lastRecord =
			 * wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
			 * 
			 * //LogUtils.ERROR_LOG.error(lastRecord.toString()); int changeStatus =
			 * wdiodeumtConfigMapper.deleteWdiodeumtConfigById(lastRecord.get(0).getId());
			 * //LogUtils.ERROR_LOG.error(oldViewWdiodeConfig.toString()); if (changeStatus
			 * == UserConstants.CHANGE_1_RECORD) { LogUtils.ERROR_LOG.error("回滚数据库成功");
			 * return AjaxResult.error("更新失败， 回滚数据库成功"); }
			 * 
			 * LogUtils.ERROR_LOG.error("回滚数据库失败"); return
			 * AjaxResult.error("更新失败， 回滚数据库失败"); }
			 */
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("上传配置文件时，读取配置文件内容失败", e);
			return AjaxResult.error("配置文件上传失败");
		}

//        return AjaxResult.success();
	}

	/**
	 * 执行配置文件修改的命令
	 *
	 * @param commandKey 命令在数据库中的键
	 * @param key        配置文件-键
	 * @param value      配置文件-值
	 * @param comment    配置文件-注释
	 * @param flag       yes=>修改单个配置， no=>批量修改配置
	 * @param oper       指明操作类型 alter => 修改单个配置 alterAll => 修改单个配置和注释 update =>
	 *                   从文件修改单个配置 updateAll => 从文件修改单个配置和注释
	 * @return
	 */

	private int persist_wdiodeumt_config(String commandKey, String key, String value, String comment, String flag,
			String oper) {
		CommandInfo commandInfo = commandInfoService.selectCommandInfoByKey(commandKey);
		if (commandInfo != null && !commandInfo.getValue().trim().equals("")) {
			boolean jobBackground = false;
			if (commandInfo.getBackground().trim().equals("yes")) {
				jobBackground = true;
			}

			long jobTimeout = 3000; // 默认3s超时
			if (!jobBackground) {
				if (commandInfo.getTimeout() != 0) {
					jobTimeout = commandInfo.getTimeout();
				}
			}

			/*
			 * ./DataSlot.sh wdiode_config alter yes s_ip 192.168.0.20 "注释的内容哦"
			 */
			StringBuilder command = new StringBuilder(commandInfo.getValue());
			command.append(UserConstants.SPACE_ONE).append(oper).append(UserConstants.SPACE_ONE).append(flag)
					.append(UserConstants.SPACE_ONE).append(key).append(UserConstants.SPACE_ONE)
					.append("\"" + value + "\"").append(UserConstants.SPACE_ONE).append("\"" + comment + "\"");

			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
					return UserConstants.CHANGE_1_RECORD;
				} else {
					LogUtils.ERROR_LOG.error("Wdiodeumt配置文件变更时 " + oper + " 操作出错， 执行命令为 " + command.toString()
							+ "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("Wdiodeumt配置文件变更时， " + oper + "操作发生异常， 命令为 " + command.toString(), e);
				return UserConstants.CHANGE_0_RECORD;
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在单向的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return UserConstants.CHANGE_0_RECORD;
	}

	@Override
	public EditViewWdiodeumtConfig selectWdiodeumtConfigByKey(String w_key) {
		List<WdiodeumtConfig> wdiodeumtConfigs = wdiodeumtConfigMapper.selectWdiodeumtConfigList(1); // 查询最后一条记录
		EditViewWdiodeumtConfig editViewWdiodeumtConfig = new EditViewWdiodeumtConfig();
		List<ViewWdiodeumtConfig> configinfo = (List<ViewWdiodeumtConfig>) ParseWdiodeumtConfig.INSTANCE
				.parseWdiodeumtConfig(wdiodeumtConfigs.get(0)).get("configinfo");

		for (ViewWdiodeumtConfig viewWdiodeumtConfig : configinfo) {
			if (viewWdiodeumtConfig.getW_key() != null && viewWdiodeumtConfig.getW_key().equals(w_key)) {
				editViewWdiodeumtConfig.setW_key(w_key);
				editViewWdiodeumtConfig.setW_value(viewWdiodeumtConfig.getW_value());
				editViewWdiodeumtConfig.setComment("默认信息");
				editViewWdiodeumtConfig.setDescription(viewWdiodeumtConfig.getDescription());
				editViewWdiodeumtConfig.setW_time(viewWdiodeumtConfig.getW_time());
				break;
			}
		}
		return editViewWdiodeumtConfig;
	}

	@Override
	public String checkComment(String comment, String reversion) {
		if (reversion.equals("yes")) {
			if (comment != null && !"".equals(comment.trim())) {
				return UserConstants.NORMAL;
			}
		} else if (reversion.equals("no")) {
			return UserConstants.NORMAL;
		}

		return UserConstants.EXCEPTION;
	}

	@Override
	public AjaxResult downloadF() {
		List<ViewWdiodeumtConfig> list = selectWdiodeumtConfigList(new ViewWdiodeumtConfig());
		String stdout = "";
		StringBuffer sb = new StringBuffer();
		for (ViewWdiodeumtConfig conf : list) {

			if (conf.getW_key().length() >= 8)
				sb.append(conf.getW_key() + "\t");
			else
				sb.append(conf.getW_key() + "\t\t");

			if (conf.getW_value().length() >= 8)
				sb.append(conf.getW_value() + "\t");
			else
				sb.append(conf.getW_value() + "\t\t");

			if (conf.getDescription().length() >= 15)
				sb.append(conf.getDescription() + "\t");
			else if (conf.getDescription().length() >= 10)
				sb.append(conf.getDescription() + "\t\t");
			else if (conf.getDescription().length() >= 5)
				sb.append(conf.getDescription() + "\t\t\t");
			else
				sb.append(conf.getDescription() + "\t\t\t\t");

			sb.append(conf.getW_time() + "\n");
		}
		// System.out.println("stdout :"+stdout);

		Calendar cal = Calendar.getInstance();
		// System.out.println("Calendar");
		String fname = "端口转发配置文件-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + ".zip";
		// System.out.println(fname);
		byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
		// System.out.println(data.length);
		return AjaxResult.success2(fname, data);
	}

	@Override
	public AjaxResult fileSync() {
		String fileName = wdiodeumtConfigPath.substring(wdiodeumtConfigPath.lastIndexOf("/"));
		File src = new File(wdiodeumtConfigPath);
		String fileDir = configService.selectConfigByKey("sys.file.directory");
		if (StringUtils.isEmpty(fileDir)) {
			return AjaxResult.error("未配置文件同步路径，请先配置该路径！");
		}
//       File dest = new File(ferryDirMapper.selectFerryDirList(null).get(0).getFileFerry() + fileName);
		File dest = new File(fileDir + fileName);
		if (!dest.exists()) {
			try {
				dest.createNewFile();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("创建文件失败");
				return AjaxResult.error("创建此配置文件失败");
			}
		}
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(src).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error("文件同步失败！");
		} finally {
			try {
				if (inputChannel != null) {
					inputChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (outputChannel != null) {
					outputChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return AjaxResult.success("文件同步成功");
	}

	@Override
	public AjaxResult uploadToDB() {
		if (searchFile() && updateFile()) {
			return AjaxResult.success("已将本地配置文件同步至数据库！");
		} else {
			return AjaxResult.error("本地文件不存在或读取相应的配置文件失败");
		}
	}

	public boolean searchFile() {
		File file = new File(wdiodeumtConfigPath);
		return file.exists();
	}

	public boolean updateFile() {
		File src = new File(wdiodeumtConfigPath);
		WdiodeumtConfig wdiodeumtConfig = new WdiodeumtConfig();
		List<ViewWdiodeumtConfig> viewWdiodeumtConfigs = new ArrayList<>();
		// 将配置文件内容持久化到数据库中
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(src));
			// 用来存储注释
			String description = "";
			int flag = 0;
			String line = null;
			wdiodeumtConfig.setDescription("来自本地配置文件");
			while ((line = bufferedReader.readLine()) != null) {
//				flag++;
//				if (flag == 1) {
//					// wdiodeumtConfig.setDataTime(new Date(line.split(":")[1]));
//					continue;
//				} else if (flag == 2) {
//					wdiodeumtConfig.setDescription("来自本地配置文件");
//					continue;
//				} else {
				if (line.trim().equals("")) {
					continue;
				} else if (line.startsWith("#")) {
					description = line.substring(1);
				} else {
					ViewWdiodeumtConfig viewWdiodeumtConfig = new ViewWdiodeumtConfig();
//						if (line.contains("tcnrswhost")) {
//							// 自动修改配置文件中的tcnrswhost配置项为本机名称
//							viewWdiodeumtConfig.setDescription(description);
//							viewWdiodeumtConfig.setW_key(line.substring(0, line.indexOf("=")));
//							viewWdiodeumtConfig.setW_value(InetAddress.getLocalHost().getHostName());
//							viewWdiodeumtConfig.setW_time(new Date(System.currentTimeMillis()));
//							viewWdiodeumtConfigs.add(viewWdiodeumtConfig);
//						} else {
					viewWdiodeumtConfig.setDescription(description);
					viewWdiodeumtConfig.setW_key(line.substring(0, line.indexOf("=")));
					viewWdiodeumtConfig.setW_value(line.substring(line.indexOf("=") + 1));
					viewWdiodeumtConfig.setW_time(new Date(System.currentTimeMillis()));
					viewWdiodeumtConfigs.add(viewWdiodeumtConfig);
//						}
					description = "";
				}
			}
//			}
			bufferedReader.close();
		} catch (Exception e) {
//            e.printStackTrace();
			LogUtils.ERROR_LOG.error("读取本地配置文件失败-->" + e.getMessage());
			return false;
		}
		wdiodeumtConfig.setDataTime(new Date(System.currentTimeMillis()));
		wdiodeumtConfig.setContents(switchWdiodeumtConfigToConfString(viewWdiodeumtConfigs, null));
		// 将数据插入数据库
		return UserConstants.CHANGE_0_RECORD != wdiodeumtConfigMapper.insertWdiodeumtConfig(wdiodeumtConfig);
	}

	/**
	 * 新增端口转发配置文件
	 *
	 * @param wdiodeumtConfig 端口转发配置文件信息
	 * @return 结果
	 */
	@Override
	public int insertWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig) {
		return wdiodeumtConfigMapper.insertWdiodeumtConfig(wdiodeumtConfig);
	}

	/**
	 * 修改端口转发配置文件
	 *
	 * @param wdiodeumtConfig 端口转发配置文件信息
	 * @return 结果
	 */
	@Override
	public int updateWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig) {
		return wdiodeumtConfigMapper.updateWdiodeumtConfig(wdiodeumtConfig);
	}

	/**
	 * 删除端口转发配置文件对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteWdiodeumtConfigByIds(String ids) {
		return wdiodeumtConfigMapper.deleteWdiodeumtConfigByIds(Convert.toStrArray(ids)) != 0
				? persistWdiodeumtConfig(wdiodeumtConfigMapper.selectWdiodeumtConfigList(1))
				: 0;
	}

}
