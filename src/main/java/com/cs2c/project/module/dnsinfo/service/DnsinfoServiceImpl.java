package com.cs2c.project.module.dnsinfo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.dnsinfo.mapper.DnsinfoMapper;
import com.cs2c.project.module.dnsinfo.domain.Dnsinfo;
import com.cs2c.project.module.dnsinfo.service.IDnsinfoService;
import com.cs2c.common.support.Convert;

/**
 * DNS数据 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Service
public class DnsinfoServiceImpl implements IDnsinfoService 
{
	@Autowired
	private ICommandInfoService commandInfoService;

	@Autowired
	private DnsinfoMapper dnsinfoMapper;

	/**
     * 查询DNS数据信息
     * 
     * @param id DNS数据ID
     * @return DNS数据信息
     */
    @Override
	public Dnsinfo selectDnsinfoById(Integer id)
	{
	    return dnsinfoMapper.selectDnsinfoById(id);
	}
	
	/**
     * 查询DNS数据列表
     * 
     * @param dnsinfo DNS数据信息
     * @return DNS数据集合
     */
	@Override
	public List<Dnsinfo> selectDnsinfoList(Dnsinfo dnsinfo)
	{
	    return dnsinfoMapper.selectDnsinfoList(dnsinfo);
	}
	
    /**
     * 新增DNS数据
     * 
     * @param dnsinfo DNS数据信息
     * @return 结果
     */
	@Override
	public int insertDnsinfo(Dnsinfo dnsinfo)
	{
        if (config_dns("config_dns", dnsinfo.getDns(), "add") == UserConstants.CHANGE_1_RECORD) {
            try {
                return dnsinfoMapper.insertDnsinfo(dnsinfo);
            }catch(Exception e) {
                return 0;
            }}
		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 执行网络的启用禁用
	 * @param commandKey 命令键
	 * @param dnsAddress DNS地址
	 * @param oper 操作（up/down）
	 * @return
	 */
	private int config_dns(String commandKey, String dnsAddress, String oper) {
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
					.append(UserConstants.SPACE_ONE).append(dnsAddress);
			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
					return UserConstants.CHANGE_1_RECORD;
				} else {
					LogUtils.ERROR_LOG.error("配置DNS时 " + oper + " 操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("配置DNS时， " + oper + "操作发生异常， 命令为 " + command.toString(), e);
				return UserConstants.CHANGE_0_RECORD;
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在配置DNS的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return UserConstants.CHANGE_0_RECORD;
	}


	/**
     * 删除DNS数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDnsinfoByIds(String ids)
	{
		Dnsinfo dnsinfo = null;
		try {
			Integer id = Integer.parseInt(ids);
			dnsinfo = selectDnsinfoById(id);

			if (config_dns("config_dns", dnsinfo.getDns(), "remove") == UserConstants.CHANGE_1_RECORD)
				return dnsinfoMapper.deleteDnsinfoByIds(Convert.toStrArray(ids));
		} catch (IllegalArgumentException e) {
			if (dnsinfo != null)
				LogUtils.ERROR_LOG.error("删除DNS信息时产生异常, 参数为 " + dnsinfo.getDns(), e);
		}

		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 校验DNS
	 */
	@Override
	public String checkIpAddress(String ip)
	{
		if (ip.matches(UserConstants.IPADDRESS_PATTERN)) {
			return UserConstants.NORMAL;
		}
		return UserConstants.EXCEPTION;
	}
}
