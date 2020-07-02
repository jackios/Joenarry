package com.cs2c.project.module.wdiodeConfig.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.config.JoenasConfig;
import com.cs2c.framework.config.Cs2cConfig;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.project.module.wdiodeConfig.domain.EditViewWdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.domain.ParseWdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.domain.ViewWdiodeConfig;
import com.cs2c.project.system.user.domain.User;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.wdiodeConfig.mapper.WdiodeConfigMapper;
import com.cs2c.project.module.wdiodeConfig.domain.WdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.service.IWdiodeConfigService;
import com.cs2c.common.support.Convert;
import org.springframework.web.multipart.MultipartFile;

/**
 * wdiode配置 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-14
 */
@Service
public class WdiodeConfigServiceImpl implements IWdiodeConfigService 
{
	@Autowired
	private WdiodeConfigMapper wdiodeConfigMapper;

	@Autowired
	private ICommandInfoService commandInfoService;

	@Autowired
	private JoenasConfig joenasConfig;
	
	private codezip codeZip = new codezip();
	/**
     * 查询wdiode配置信息
     * 
     * @param id wdiode配置ID
     * @return wdiode配置信息
     */
    @Override
	public WdiodeConfig selectWdiodeConfigById(Integer id)
	{
	    return wdiodeConfigMapper.selectWdiodeConfigById(id);
	}
	
	/**
     * 查询wdiode配置列表
     *
     * @param viewWdiodeConfig wdiode配置信息
     * @return wdiode配置集合
     */
	@Override
	public List<ViewWdiodeConfig> selectWdiodeConfigList(ViewWdiodeConfig viewWdiodeConfig)
	{
	    List<WdiodeConfig> wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录

		if (wdiodeConfig.size() == 0) {
			return new ArrayList<>();
		}

	    return (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig.get(0)).get("configinfo");
	}

	/**
	 * 查询wdiode配置列表
	 *
	 * @param viewWdiodeConfig wdiode配置信息
	 * @return wdiode配置集合
	 */
	@Override
	public List<ViewWdiodeConfig> selectWdiodeConfigList(ViewWdiodeConfig viewWdiodeConfig, Integer id)
	{
		WdiodeConfig wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigById(id); // 查询最后一条记录

		return (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig).get("configinfo");
	}

	/**
	 * 查询wdiode配置列表
	 *
	 * @param number
	 * @return wdiode配置集合
	 */
	@Override
	public List<WdiodeConfig> selectMulWdiodeConfigList(Integer number)
	{
		return wdiodeConfigMapper.selectWdiodeConfigList(number); // 查询最后一条记录
	}

	public EditViewWdiodeConfig selectWdiodeConfigByKey(String w_key) {
		List<WdiodeConfig> wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录
		EditViewWdiodeConfig editViewWdiodeConfig = new EditViewWdiodeConfig();
		List<ViewWdiodeConfig> configinfo = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig.get(0)).get("configinfo");

		for (ViewWdiodeConfig viewWdiodeConfig : configinfo) {
			if (viewWdiodeConfig.getW_key() != null && viewWdiodeConfig.getW_key().equals(w_key)) {
				editViewWdiodeConfig.setW_key(w_key);
				editViewWdiodeConfig.setW_value(viewWdiodeConfig.getW_value());
				editViewWdiodeConfig.setComment("默认信息");
				editViewWdiodeConfig.setDescription(viewWdiodeConfig.getDescription());
				editViewWdiodeConfig.setW_time(viewWdiodeConfig.getW_time());

				break;
			}
		}

		return editViewWdiodeConfig;
	}
	
	/**
     * 修改wdiode配置
     * 
     * @param editViewWdiodeConfig wdiode配置信息
     * @return 结果
     */
	@Override
	public int updateWdiodeConfig(EditViewWdiodeConfig editViewWdiodeConfig, String reversion)
	{
		List<WdiodeConfig> wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录
		List<ViewWdiodeConfig> configinfo = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig.get(0)).get("configinfo");

		Date current_date = new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ViewWdiodeConfig oldViewWdiodeConfig = new ViewWdiodeConfig();

		boolean isSuccess = false;
		for (ViewWdiodeConfig conf : configinfo) {
			if (conf.getW_key().equals(editViewWdiodeConfig.getW_key())) {
				oldViewWdiodeConfig.setW_time(conf.getW_time());
				oldViewWdiodeConfig.setDescription(conf.getDescription());
				oldViewWdiodeConfig.setW_value(conf.getW_value());
				oldViewWdiodeConfig.setW_key(conf.getW_key());

				conf.setW_value(editViewWdiodeConfig.getW_value());
				conf.setDescription(editViewWdiodeConfig.getDescription());
				conf.setW_time(current_date);
				isSuccess = true;

				break;
			}
		}


		if (!isSuccess) {
			return UserConstants.CHANGE_0_RECORD;
		}

		String contents = switchWdiodeConfigToJsonString(configinfo, time.format(current_date));
		wdiodeConfig.get(0).setContents(contents);

		int changeStatus = -1;
		if ("yes".equals(reversion)) {
			// 保存新版本

			wdiodeConfig.get(0).setDataTime(current_date);
			wdiodeConfig.get(0).setDescription(editViewWdiodeConfig.getComment());
			wdiodeConfig.get(0).setId(null);

			changeStatus = wdiodeConfigMapper.insertWdiodeConfig(wdiodeConfig.get(0));
		} else {
			// 更新当前版本
			changeStatus = wdiodeConfigMapper.updateWdiodeConfig(wdiodeConfig.get(0));
		}

		if (changeStatus == UserConstants.CHANGE_1_RECORD) {
			int persistStatus = persist_wdiode_config("wdiode_config", editViewWdiodeConfig.getW_key(), editViewWdiodeConfig.getW_value(), editViewWdiodeConfig.getComment(), "yes", "alter");
			if (persistStatus == UserConstants.CHANGE_0_RECORD) {
				LogUtils.ERROR_LOG.error("持久化修改到配置文件失败, 回滚数据库");
				for (ViewWdiodeConfig conf : configinfo) {
					if (conf.getW_key().equals(oldViewWdiodeConfig.getW_key())) {
						conf.setW_value(oldViewWdiodeConfig.getW_value());
						conf.setDescription(oldViewWdiodeConfig.getDescription());
						conf.setW_time(oldViewWdiodeConfig.getW_time());

						break;
					}
				}

				changeStatus = wdiodeConfigMapper.updateWdiodeConfig(wdiodeConfig.get(0));
				//LogUtils.ERROR_LOG.error(oldViewWdiodeConfig.toString());
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

	private String switchWdiodeConfigToJsonString(List<ViewWdiodeConfig> wdiodeConfigs, String current_date) {
		StringBuilder sb = new StringBuilder("{");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ViewWdiodeConfig viewWdiodeConfig : wdiodeConfigs) {
			sb.append("\"").append(viewWdiodeConfig.getW_key()).append("\":").append("{")
					.append("\"description\"").append(":\"").append(viewWdiodeConfig.getDescription()).append("\",")
					.append("\"value\"").append(":\"").append(viewWdiodeConfig.getW_value()).append("\",")
					.append("\"data_time\"").append(":\"").append(time.format(viewWdiodeConfig.getW_time())).append("\"")
					.append("},");
		}

		return sb.append("}").toString();
	}

	private String switchWdiodeConfigToJsonStringForExport(List<ViewWdiodeConfig> wdiodeConfigs) {
		StringBuilder sb = new StringBuilder("{");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ViewWdiodeConfig viewWdiodeConfig : wdiodeConfigs) {
			sb.append("    \"").append(viewWdiodeConfig.getW_key()).append("\":").append("{\n")
					.append("        \"description\"").append(":\"").append(viewWdiodeConfig.getDescription()).append("\",\n")
					.append("        \"value\"").append(":\"").append(viewWdiodeConfig.getW_value()).append("\",\n")
					.append("        \"data_time\"").append(":\"").append(time.format(viewWdiodeConfig.getW_time())).append("\"\n")
					.append("    },\n");
		}

		sb.append("    \"").append("version_description").append("\":").append("\"如果上传配置，请记得修改此处的版本信息哦\"\n");
		return sb.append("}").toString();
	}

	/**
	 *
	 * @param reversion
	 * @return
	 */
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

	/**
	 * 删除wdiode配置对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteWdiodeConfigByIds(String ids)
	{
		return wdiodeConfigMapper.deleteWdiodeConfigByIds(Convert.toStrArray(ids));
	}

	/**
	 * 回滚配置
	 *
	 * @param id
	 * @return 结果
	 */
	@Override
	public int reversion(Integer id) {
		WdiodeConfig wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigById(id);
		wdiodeConfig.setId(null);
		wdiodeConfig.setDescription("版本回退，自动生成的注释");
		wdiodeConfig.setDataTime(new Date());


		// 查询最后一条记录
		List<WdiodeConfig> wdiodeConfigLast = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录
		List<ViewWdiodeConfig> configinfoLast = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfigLast.get(0)).get("configinfo");

		// 执行配置文件内容回滚
		List<ViewWdiodeConfig> configinfo = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig).get("configinfo");

		Date current_date = new Date();

		ArrayList<String> successKeys = new ArrayList<>();
		boolean isSuccess = true;
		for (ViewWdiodeConfig conf : configinfo) {
			conf.setW_time(current_date);
			int persistStatus = persist_wdiode_config("wdiode_config", conf.getW_key(), conf.getW_value(), conf.getDescription(), "yes", "alter");
			if (persistStatus == UserConstants.CHANGE_0_RECORD) {
				LogUtils.ERROR_LOG.error("回滚配置文件失败， 配置项为 " + conf.getW_key());
				isSuccess = false;
				break;
			} else {
				successKeys.add(conf.getW_key().trim());
				LogUtils.ERROR_LOG.error("回滚配置文件 - 配置项 " + conf.getW_key() + " 还原成功");
			}
		}

		if (isSuccess) {
			// 执行数据库记录持久化
			LogUtils.ERROR_LOG.error("配置文件回滚成功");
			return wdiodeConfigMapper.insertWdiodeConfig(wdiodeConfig);
		} else {
			LogUtils.ERROR_LOG.error("配置文件回滚存在异常，未完全回滚成功");

			for (ViewWdiodeConfig conf : configinfo) {
				if (successKeys.contains(conf.getW_key().trim())) {
					continue;
				}

				for (ViewWdiodeConfig oldConf: configinfoLast) {
					if (oldConf.getW_key().trim().equals(conf.getW_key().trim())) {
						conf.setW_value(oldConf.getW_value());
						conf.setDescription(oldConf.getDescription());
						conf.setW_time(oldConf.getW_time());
					}
				}
			}

			return wdiodeConfigMapper.insertWdiodeConfig(wdiodeConfig);
		}
	}

	/**
	 * 还原配置
	 *
	 * @return 结果
	 */
	@Override
	public int reinit() {
//		int id = 1;
//		WdiodeConfig wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigById(id);
//		wdiodeConfig.setId(null);
//		wdiodeConfig.setDescription("版本回退，自动生成的注释");
//		wdiodeConfig.setDataTime(new Date());
//
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(joenasConfig.getWdiode_config_path() + ".default")));
//			String line = null;
//			ArrayList<String> contents = new ArrayList<>();
//			try {
//				while ((line = br.readLine()) != null) {
//					contents.add(line);
//				}
//			} catch (IOException e) {
//				LogUtils.ERROR_LOG.error("还原配置时，读取配置文件内存发生异常", e);
//				return UserConstants.CHANGE_0_RECORD;
//			}
//
//			for (String line : )
//
//		} catch (FileNotFoundException e) {
//			LogUtils.ERROR_LOG.error("还原配置时，默认配置文件不存在，无法还原", e);
//			return UserConstants.CHANGE_0_RECORD;
//		}
//
//		return wdiodeConfigMapper.insertWdiodeConfig(wdiodeConfig);
		int persistStatus = persist_wdiode_config("wdiode_config", "", "", "", "yes", "reinit");
		if (persistStatus == UserConstants.CHANGE_0_RECORD) {
			LogUtils.ERROR_LOG.error("还原配置文件失败, 请重试");
			return UserConstants.CHANGE_0_RECORD;
		}

		LogUtils.ERROR_LOG.error("重新初始化配置文件成功");
		return UserConstants.CHANGE_1_RECORD;
	}

	/**
	 * 导出配置文件
	 *
	 * @param id
	 * @return 结果
	 */
	public AjaxResult export(Integer id) {
		List<WdiodeConfig> wdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录
		List<ViewWdiodeConfig> configinfo = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig.get(0)).get("configinfo");

		OutputStream out = null;

		String filename = encodingFilename("核心配置");
		try {
			out = new FileOutputStream(getAbsoluteFile(filename));
			String jsonStr = switchWdiodeConfigToJsonStringForExport(configinfo);
			out.write(jsonStr.getBytes());
			out.flush();
			return AjaxResult.success(filename);
		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("获取导出核心配置的绝对路径时发生异常", e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("导出核心配置，写入临时配置文件时发生异常", e);
		} finally {
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
					LogUtils.ERROR_LOG.error("导出核心配置，关闭输出流时发生异常", e1);
				}
			}
		}

		return AjaxResult.error("导出核心配置失败，请联系管理员！");
	}

	/**
	 * 编码文件名
	 */
	private String encodingFilename(String filename)
	{
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");

		filename = UUID.randomUUID().toString() + "_" + time.format(new Date()) + "-" + filename  + ".json";
		return filename;
	}

	/**
	 * 获取下载路径
	 *
	 * @param filename 文件名称
	 */
	public String getAbsoluteFile(String filename)
	{
		String downloadPath = Cs2cConfig.getDownloadPath() + filename;
		File desc = new File(downloadPath);
		if (!desc.getParentFile().exists())
		{
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}


	/**
	 * 上传配置文件
	 *
	 * @param file
	 * @return 结果
	 */
	public AjaxResult upload(MultipartFile file) {
		try {

			if (file.isEmpty()) {
				return AjaxResult.error("文件为不存在");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			StringBuilder config = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				config.append(line);
			}

			try {
				reader.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("上传配置文件时， 关闭BufferedReader流失败", e);
				return AjaxResult.error();
			}



			JSONObject jsonObject = JSONObject.parseObject(config.toString());
			String description = jsonObject.getString("version_description");
			jsonObject.remove("version_description");

			WdiodeConfig wdiodeConfig = new WdiodeConfig();
			wdiodeConfig.setContents(jsonObject.toJSONString());

			List<ViewWdiodeConfig> configinfo = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(wdiodeConfig).get("configinfo");


			Date current_date = new Date();

			// 上传配置时， 将所有配置项的更新时间 设置为当前时间点
			for (ViewWdiodeConfig cc : configinfo) {
				cc.setW_time(current_date);
			}

			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contents = switchWdiodeConfigToJsonString(configinfo, time.format(current_date));
			wdiodeConfig.setContents(contents);
			wdiodeConfig.setDataTime(current_date);
			wdiodeConfig.setDescription(description);
			wdiodeConfig.setId(null);


			List<WdiodeConfig> oldWdiodeConfig = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录
			List<ViewWdiodeConfig> oldConfiginfo = (List<ViewWdiodeConfig>) ParseWdiodeConfig.INSTANCE.parseWdiodeConfig(oldWdiodeConfig.get(0)).get("configinfo");

//			if (1 == 1)
//				return AjaxResult.error("上传配置文件生效失败");
			if (UserConstants.CHANGE_0_RECORD == wdiodeConfigMapper.insertWdiodeConfig(wdiodeConfig)) {
				return AjaxResult.error("上传配置文件生效失败");
			}

			HashMap<String, String[]> record = new HashMap<>();
			int count = 0, flag = 1;
			for (ViewWdiodeConfig cc : configinfo) {
				if (flag == 0) {
					break;
				}

				for (ViewWdiodeConfig ccc : oldConfiginfo) {
					if (cc.getW_key() != null && cc.getW_key().equals(ccc.getW_key())) {
						if (cc.getW_value() != null && cc.getW_value().equals(ccc.getW_value())) {
							//break;
						} else if (ccc.getW_value() != null && ccc.getW_value().equals(cc.getW_value())) {
							//break;
						} else {
							int persistStatus = persist_wdiode_config("wdiode_config", cc.getW_key(), cc.getW_value(), cc.getDescription(), "yes", "alter");
							if (persistStatus == UserConstants.CHANGE_1_RECORD) {
								count++;
								record.put(cc.getW_key(), new String[]{ccc.getW_value(), cc.getW_value()});
							} else {
								flag = 0;
							}
							//break;
						}
						break;
					}
				}
			}
			//LogUtils.ERROR_LOG.error(record.toString());
			//LogUtils.ERROR_LOG.error(flag + "");

			//LogUtils.ERROR_LOG.error(jsonObject.toJSONString());
			if (flag == 0) {
				if (count == 0)
					LogUtils.ERROR_LOG.error("更新配置文件失败，准备回滚数据库");
				else {
					StringBuilder sb = new StringBuilder();
					for (String kk : record.keySet()) {
						sb.append(kk + "=[" + record.get(kk)[0] + "," + record.get(kk)[1] + "];");
					}
					LogUtils.ERROR_LOG.error("更新配置文件失败，准备回滚数据库, 已经变更的配置项为 " + sb.toString() + "； 请联系管理员处理");
				}

				List<WdiodeConfig> lastRecord = wdiodeConfigMapper.selectWdiodeConfigList(1); // 查询最后一条记录

				//LogUtils.ERROR_LOG.error(lastRecord.toString());
				int changeStatus = wdiodeConfigMapper.deleteWdiodeConfigById(lastRecord.get(0).getId());
				//LogUtils.ERROR_LOG.error(oldViewWdiodeConfig.toString());
				if (changeStatus == UserConstants.CHANGE_1_RECORD) {
					LogUtils.ERROR_LOG.error("回滚数据库成功");
					return AjaxResult.error("更新失败， 回滚数据库成功");
				}

				LogUtils.ERROR_LOG.error("回滚数据库失败");
				return AjaxResult.error("更新失败， 回滚数据库失败");
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("上传配置文件时，读取配置文件内容失败", e);
			return AjaxResult.error("配置文件上传失败");
		}

		return AjaxResult.success();
	}


	/**
	 * 执行配置文件修改的命令
	 *
	 * @param commandKey 命令在数据库中的键
	 * @param key    配置文件-键
	 * @param value 配置文件-值
	 * @param comment  配置文件-注释
	 * @param flag yes=>修改单个配置， no=>批量修改配置
	 * @param oper 指明操作类型
	 *             alter => 修改单个配置
	 *             alterAll => 修改单个配置和注释
	 *             update => 从文件修改单个配置
	 *             updateAll => 从文件修改单个配置和注释
	 * @return
	 */
	private int persist_wdiode_config(String commandKey, String key, String value, String comment, String flag, String oper) {
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
			./DataSlot.sh wdiode_config alter yes s_ip 192.168.0.20 "注释的内容哦"
			 */
			StringBuilder command = new StringBuilder(commandInfo.getValue());
			command.append(UserConstants.SPACE_ONE).append(oper)
					.append(UserConstants.SPACE_ONE).append(flag)
					.append(UserConstants.SPACE_ONE).append(key)
					.append(UserConstants.SPACE_ONE).append("\"" + value + "\"")
					.append(UserConstants.SPACE_ONE).append("\"" + comment + "\"");

			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
					return UserConstants.CHANGE_1_RECORD;
				} else {
					LogUtils.ERROR_LOG.error("Wdiode配置文件变更时 " + oper + " 操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("Wdiode配置文件变更时， " + oper + "操作发生异常， 命令为 " + command.toString(), e);
				return UserConstants.CHANGE_0_RECORD;
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在单向的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return UserConstants.CHANGE_0_RECORD;
	}

    @Override
    public AjaxResult downloadF() {
        List<ViewWdiodeConfig> list = selectWdiodeConfigList(new ViewWdiodeConfig());
        String stdout = "";
        StringBuffer sb = new StringBuffer();
        for(ViewWdiodeConfig conf : list) {

            if(conf.getW_key().length() >= 8) sb.append(conf.getW_key()+"\t");
            else sb.append(conf.getW_key()+"\t\t");
            
            if(conf.getW_value().length() >= 8) sb.append(conf.getW_value()+"\t");
            else sb.append(conf.getW_value()+"\t\t");
            
            
            if(conf.getDescription().length() >= 15) sb.append(conf.getDescription()+"\t");
            else if(conf.getDescription().length() >= 10) sb.append(conf.getDescription()+"\t\t");
            else if(conf.getDescription().length() >= 5) sb.append(conf.getDescription()+"\t\t\t");
            else sb.append(conf.getDescription()+"\t\t\t\t");
            

            sb.append( conf.getW_time()+"\n");
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "核心配置-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
