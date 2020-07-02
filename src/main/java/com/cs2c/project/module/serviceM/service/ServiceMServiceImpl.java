package com.cs2c.project.module.serviceM.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.serviceM.mapper.ServiceMMapper;
import com.cs2c.project.module.serviceM.domain.ServiceM;
import com.cs2c.project.module.serviceM.service.IServiceMService;
import com.cs2c.common.support.Convert;

/**
 * 服务管理 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-23
 */
@Service
public class ServiceMServiceImpl implements IServiceMService 
{
	@Autowired
	private ServiceMMapper serviceMMapper;

	@Autowired
	private ICommandInfoService commandInfoService;

	/**
     * 查询服务管理信息
     * 
     * @param id 服务管理ID
     * @return 服务管理信息
     */
    @Override
	public ServiceM selectServiceMById(Integer id)
	{
	    return serviceMMapper.selectServiceMById(id);
	}
	
	/**
     * 查询服务管理列表
     * 
     * @param serviceM 服务管理信息
     * @return 服务管理集合
     */
	@Override
	public List<ServiceM> selectServiceMList(ServiceM serviceM)
	{
	    return serviceMMapper.selectServiceMList(serviceM);
	}
	
    /**
     * 新增服务管理
     * 
     * @param serviceM 服务管理信息
     * @return 结果
     */
	//@Override
	//public int insertServiceM(ServiceM serviceM)
//	{
//	    return serviceMMapper.insertServiceM(serviceM);
//	}
	
	/**
     * 修改服务管理
     * 
     * @param serviceM 服务管理信息
     * @return 结果
     */
	@Override
	public int updateServiceM(ServiceM serviceM)
	{
	    return serviceMMapper.updateServiceM(serviceM);
	}

	/**
     * 删除服务管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	//@Override
	//public int deleteServiceMByIds(String ids)
	//{
	//	return serviceMMapper.deleteServiceMByIds(Convert.toStrArray(ids));
	//}

	private ServiceM getCurrentInstance(String ids) {
		Integer[] firstIds = Convert.toIntArray(ids);
		if (firstIds.length == 0) {
			return null;
		}

		return selectServiceMById(firstIds[0]);
	}

	@Override
	public int start(String ids) {
		return runTask(ids, "start");
	}
	@Override
	public int stop(String ids) {
		return runTask(ids, "stop");
	}
	@Override
	public int reboot(String ids){
		if (1 == runTask(ids, "stop")) {
			return runTask(ids, "start");
		} else {
			LogUtils.ERROR_LOG.error("重启服务的时候出现错误");
			return runTask(ids, "start");
		}
		//return runTask(ids, "reboot");
	}
	@Override
	public int reload(String ids){
		return runTask(ids, "reload");
	}
	public int enable(String ids) {
		ServiceM serviceM = getCurrentInstance(ids);
		if (null == serviceM) {
			return UserConstants.CHANGE_0_RECORD;
		}

		serviceM.setIsValid("yes");
		return updateServiceM(serviceM);
	}
	@Override
	public int disable(String ids) {
		ServiceM serviceM = getCurrentInstance(ids);
		if (null == serviceM) {
			return UserConstants.CHANGE_0_RECORD;
		}

		serviceM.setIsValid("no");
		return updateServiceM(serviceM);
	}

	private int runTask(String ids, String oper) {
		ServiceM serviceM = getCurrentInstance(ids);
		if (null == serviceM) {
			return UserConstants.CHANGE_0_RECORD;
		}

		String status = operation(serviceM.getServiceKey(), oper);

		if(status.equals("0"))
			return UserConstants.CHANGE_1_RECORD;

		return UserConstants.CHANGE_0_RECORD;
	}

	@Override
	public String getServiceCurrentStatus(String cmd, String oper) {
		boolean jobBackground = false;
		long jobTimeout = 10000; // 默认10s超时

		StringBuilder command = new StringBuilder(cmd);
		command.append(UserConstants.SPACE_ONE).append(oper);
		CommandLine commandLine = CommandLine.parse(command.toString());

		try {
			Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
			String stdout = output.get("stdout");
			String stderr = output.get("stderr");
			if (StringUtils.isEmpty(stderr) && !isStdoutContainsCustomError(stdout)) {
				if (stdout.contains("running")) {
					return "运行中";
				} else if(stdout.contains("stoped")){
					return "未运行";
				} else {
					return "状态未知";
				}
			} else {
				LogUtils.ERROR_LOG.error("获取服务状态操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("获取服务状态操作失败， 命令为 " + command.toString(), e);
		}

		return "状态未知";
	}

	private String operation(String cmd, String oper) {
		boolean jobBackground = false;
		long jobTimeout = 10000; // 默认10s超时

		StringBuilder command = new StringBuilder(cmd);
		command.append(UserConstants.SPACE_ONE).append(oper);
		CommandLine commandLine = CommandLine.parse(command.toString());


		try {
			Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
			String stdout = output.get("stdout");
			String stderr = output.get("stderr");
			if (StringUtils.isEmpty(stderr) && !isStdoutContainsCustomError(stdout)) {
				return UserConstants.NORMAL;
			} else {
				LogUtils.ERROR_LOG.error("操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("操作失败， 命令为 " + command.toString(), e);
		}

		return UserConstants.EXCEPTION;
	}

	/**
	 * 检查标准输出是否包含Error自定义输出
	 */
	private boolean isStdoutContainsCustomError(String string) {
		String result[] = string.split("\n");
		for (String s : result) {
			if (s.trim().startsWith("Error"))
				return true;
		}
		return false;
	}
    @Override
    public int sysreboot(String ids) {
        return runTask(ids, "reboot");
        
    }

    @Override
    public int poweroff(String ids) {
        // TODO Auto-generated method stub
        return runTask(ids, "poweroff");
    }
    
}
