package com.cs2c.project.module.retain.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.retain.mapper.RetainMapper;
import com.cs2c.project.module.retain.domain.Retain;
import com.cs2c.project.module.retain.service.IRetainService;
import com.cs2c.project.system.user.domain.User;
import com.cs2c.common.support.Convert;

/**
 * 单向配置 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-14
 */
@Service
public class RetainServiceImpl implements IRetainService 
{
	@Autowired
	private RetainMapper retainMapper;


	@Autowired
	private ICommandInfoService commandInfoService;
	
	private codezip codeZip = new codezip();

	/**
     * 查询单向配置信息
     * 
     * @param id 单向配置ID
     * @return 单向配置信息
     */
    @Override
	public Retain selectRetainById(Integer id)
	{
	    return retainMapper.selectRetainById(id);
	}
	
	/**
     * 查询单向配置列表
     * 
     * @param retain 单向配置信息
     * @return 单向配置集合
     */
	@Override
	public List<Retain> selectRetainList(Retain retain)
	{
	    return retainMapper.selectRetainList(retain);
	}
	
    /**
     * 新增单向配置
     * 
     * @param retain 单向配置信息
     * @return 结果
     */
	@Override
	public int insertRetain(Retain retain)
	{
		// 限定只能有一条记录
		if (selectRetainList(new Retain()).size() > 0) {
			return UserConstants.CHANGE_0_RECORD;
		}

		if (config_retain("config_retain", retain.getIp(), retain.getMac(), "add") == UserConstants.CHANGE_1_RECORD)
			return retainMapper.insertRetain(retain);

		return UserConstants.CHANGE_0_RECORD;

	}
	
	/**
     * 修改单向配置
     * 
     * @param retain 单向配置信息
     * @return 结果
     */
	@Override
	public int updateRetain(Retain retain)
	{
		if (config_retain("config_retain", retain.getIp(), retain.getMac(), "edit") == UserConstants.CHANGE_1_RECORD)
			return retainMapper.updateRetain(retain);

		return UserConstants.CHANGE_0_RECORD;
	}

	/**
     * 删除单向配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRetainByIds(String ids)
	{
		Retain retain = null;
		for (Integer id : Convert.toIntArray(ids)) {
			retain = selectRetainById(id);
			if (config_retain("config_retain", retain.getIp(), retain.getMac(), "remove") == UserConstants.CHANGE_1_RECORD)
				continue;

			return UserConstants.CHANGE_0_RECORD;
		}

		return retainMapper.deleteRetainByIds(Convert.toStrArray(ids));
	}

	/**
	 * 校验IPADDR
	 */
	@Override
	public String checkIpAddress(String ip)
	{
		if (ip.matches(UserConstants.IPADDRESS_PATTERN)) {
			return UserConstants.NORMAL;
		}
		return UserConstants.EXCEPTION;
	}

	/**
	 * 校验MAC
	 */
	@Override
	public String checkMac(String mac)
	{
		if (mac.matches(UserConstants.MAC_PATTERN)) {
			return UserConstants.NORMAL;
		}
		return UserConstants.EXCEPTION;
	}

	private int config_retain(String commandKey, String ip, String mac, String oper) {
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

			StringBuilder command = new StringBuilder(commandInfo.getValue());
			command.append(UserConstants.SPACE_ONE).append(oper)
					.append(UserConstants.SPACE_ONE).append(ip)
					.append(UserConstants.SPACE_ONE).append(mac);

			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
					return UserConstants.CHANGE_1_RECORD;
				} else {
					LogUtils.ERROR_LOG.error("单向配置时 " + oper + " 操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("单向配置时， " + oper + "操作发生异常， 命令为 " + command.toString(), e);
				return UserConstants.CHANGE_0_RECORD;
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在单向的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return UserConstants.CHANGE_0_RECORD;
	}

    @Override
    public AjaxResult downloadF() {
        List<Retain> list = selectRetainList(new Retain());
        String stdout = "";
        for(Retain retain : list) {
            
            if(retain.getIp().length() >= 8) stdout +=retain.getIp()+"\t";
            else stdout +=retain.getIp()+"\t\t";
            
            
            if(retain.getMac().length() >= 16) stdout +=retain.getMac()+"\t";
            else if(retain.getMac().length() >= 8) stdout +=retain.getMac()+"\t\t";
            else stdout +=retain.getMac()+"\t\t\t";

            stdout += retain.getRemark()+"\n";
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "单向配置-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
