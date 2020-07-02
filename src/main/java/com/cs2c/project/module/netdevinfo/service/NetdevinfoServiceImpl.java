package com.cs2c.project.module.netdevinfo.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.IpCidrUtils;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.project.system.user.domain.User;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.netdevinfo.mapper.NetdevinfoMapper;
import com.cs2c.project.module.netdevinfo.domain.Netdevinfo;

/**
 * 网络设备 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-11
 */
@Service
public class NetdevinfoServiceImpl implements INetdevinfoService 
{
	@Autowired
	private ICommandInfoService commandInfoService;

	@Autowired
	private NetdevinfoMapper netdevinfoMapper;

	private codezip codeZip = new codezip();
	/**
     * 查询网络设备信息
     * 
     * @param id 网络设备ID
     * @return 网络设备信息
     */
    @Override
	public Netdevinfo selectNetdevinfoById(Integer id)
	{
	    return netdevinfoMapper.selectNetdevinfoById(id);
	}
	
	/**
     * 查询网络设备列表
     * 
     * @param netdevinfo 网络设备信息
     * @return 网络设备集合
     */
	@Override
	public List<Netdevinfo> selectNetdevinfoList(Netdevinfo netdevinfo)
	{
//		CommandInfo commandInfo = commandInfoService.selectCommandInfoByKey("netdevinfo_to_db");
//		boolean jobBackground = false;
//		if (commandInfo.getBackground().trim().equals("yes")) {
//			jobBackground = true;
//		}
//
//		long jobTimeout = 3000; // 默认3s超时
//		if (!jobBackground) {
//			if (commandInfo.getTimeout() != 0) {
//				jobTimeout = commandInfo.getTimeout();
//			}
//		}
//
//		Map<String, String> output = new HashMap<>();
//		try {
//			output = ShellUtils.runAndGetOutput(CommandLine.parse(commandInfo.getValue()), jobTimeout, jobBackground);
//		} catch (IOException e) {
//			LogUtils.ERROR_LOG.error("同步网络设备信息到数据库时发生异常， 执行命令为 " + commandInfo.getValue(), e);
//		}
//		String stdout = output.get("stdout");
//		String	stderr = output.get("stderr");
//
//		if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
//			//nothing todo...
//		} else {
//			LogUtils.ERROR_LOG.error("同步网络设备信息到数据库时失败， 执行命令为 " + commandInfo.getValue() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
//		}

	    return netdevinfoMapper.selectNetdevinfoList(netdevinfo);
	}
	

	/**
     * 修改网络设备
     * 
     * @param netdevinfo 网络设备信息
     * @return 结果
     */
	@Override
	public int updateNetdevinfo(Netdevinfo netdevinfo)
	{
		String netmask = netdevinfo.getNetmask().trim();

		try {
			if (null != netmask && netmask.trim().length() > 0 && netmask.trim().length() < 3) {
				Integer ii = Integer.parseInt(netmask);
				if (!(ii < 33 && netmask.equals(ii.toString()))) {
					LogUtils.ERROR_LOG.error("子网掩码 " + netmask + " 不符合掩码规范");
					return UserConstants.CHANGE_0_RECORD;
				}
			} else {
				try {
					netmask = IpCidrUtils.INSTANCE.cidrToInteger(netmask).toString();
				} catch (IllegalArgumentException e) {
					LogUtils.ERROR_LOG.error("转换掩码 " + netmask + " 为Prefix形式失败", e);
					return UserConstants.CHANGE_0_RECORD;
				}
			}

			// 设置IP地址
			CommandInfo commandInfo = commandInfoService.selectCommandInfoByKey("config_network");
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
				command.append(UserConstants.SPACE_ONE).append("config_netdev")
						.append(UserConstants.SPACE_ONE).append(netdevinfo.getDevName())
						.append(UserConstants.SPACE_ONE).append(netdevinfo.getIpaddr())
						.append(UserConstants.SPACE_ONE).append(netmask)
						.append(UserConstants.SPACE_ONE).append(netdevinfo.getOnboot())
						.append(UserConstants.SPACE_ONE).append(netdevinfo.getGateway());
				CommandLine commandLine = CommandLine.parse(command.toString());

				try {
					Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
					String stdout = output.get("stdout");
					String stderr = output.get("stderr");


					if (StringUtils.isEmpty(stderr) && !isStdoutContainsCustomError(stdout)) {
						//return netdevinfoMapper.updateNetdevinfo(netdevinfo); // 此行用于通过代码修改数据库
						commandInfo = commandInfoService.selectCommandInfoByKey("netdevinfo_to_db");
						jobBackground = false;
						if (commandInfo.getBackground().trim().equals("yes")) {
							jobBackground = true;
						}

						jobTimeout = 3000; // 默认3s超时
						if (!jobBackground) {
							if (commandInfo.getTimeout() != 0) {
								jobTimeout = commandInfo.getTimeout();
							}
						}
						output = ShellUtils.runAndGetOutput(CommandLine.parse(commandInfo.getValue()), jobTimeout, jobBackground);

						stdout = output.get("stdout");
						stderr = output.get("stderr");
						if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
							return UserConstants.CHANGE_1_RECORD;
						} else {
							LogUtils.ERROR_LOG.error("同步网络设备信息到数据库时失败， 执行命令为 " + commandInfo.getValue() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
						}
					} else {
						LogUtils.ERROR_LOG.error("配置网络设备时出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
					}
				} catch (IOException e) {
					LogUtils.ERROR_LOG.error("配置网络失败， 命令为 " + command.toString(), e);
					return UserConstants.CHANGE_0_RECORD;
				}
			} else {
				LogUtils.ERROR_LOG.error("数据库中不存在配置网络的command或command信息错误");
				return UserConstants.CHANGE_0_RECORD;
			}
		} catch (NumberFormatException e) {
			LogUtils.ERROR_LOG.error("子网掩码输入了非数字字符" , e);
			return UserConstants.CHANGE_0_RECORD;
		}

		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 启用网络设备
	 * @param netdevinfo
	 * @return
	 */

	@Override
	public int upNetdev(Netdevinfo netdevinfo) {
		return netdev_change_status("up_netdev", netdevinfo.getDevName(), "up");
	}

	/**
	 * 禁用网络设备
	 */
	@Override
	public int downNetdev(Netdevinfo netdevinfo) {
		return netdev_change_status("down_netdev", netdevinfo.getDevName(), "down");
	}

	/**
	 * 执行网络的启用禁用
	 * @param commandKey 命令键
	 * @param devName 设备名称
	 * @param oper 操作（up/down）
	 * @return
	 */
	private int netdev_change_status(String commandKey, String devName, String oper) {
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
			command.append(UserConstants.SPACE_ONE).append(devName)
					.append(UserConstants.SPACE_ONE).append(oper);
			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && !isStdoutContainsCustomError(stdout)) {
//					commandInfo = commandInfoService.selectCommandInfoByKey("netdevinfo_to_db");
//					jobBackground = false;
//					if (commandInfo.getBackground().trim().equals("yes")) {
//						jobBackground = true;
//					}
//
//					jobTimeout = 3000; // 默认3s超时
//					if (!jobBackground) {
//						if (commandInfo.getTimeout() != 0) {
//							jobTimeout = commandInfo.getTimeout();
//						}
//					}
//					output = ShellUtils.runAndGetOutput(CommandLine.parse(commandInfo.getValue()), jobTimeout, jobBackground);
//
//					stdout = output.get("stdout");
//					stderr = output.get("stderr");
//					if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
//						return UserConstants.CHANGE_1_RECORD;
//					} else {
//						LogUtils.ERROR_LOG.error("同步网络设备信息到数据库时失败， 执行命令为 " + commandInfo.getValue() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
//					}

					return UserConstants.CHANGE_1_RECORD;
				} else {
					LogUtils.ERROR_LOG.error("网络设备" + devName + "操作" + oper + "出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("网络设备" + devName + "操作" + oper + "失败， 命令为 " + command.toString(), e);
				return UserConstants.CHANGE_0_RECORD;
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在配置网络的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return UserConstants.CHANGE_0_RECORD;
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
	 * 校验网关
	 */
	public String checkGatewayAddress(String ip)
	{
		if (null != ip && ip.trim().length() == 0) {
			return UserConstants.NORMAL;
		}

		return checkIpAddress(ip);
	}

	/**
	 * 校验子网掩码
	 */
	public String checkNetmask(String ip)
	{
		if (null != ip && ip.trim().length() > 0 && ip.trim().length() < 3) {
			ip = ip.trim();
			try {
				Integer ii = Integer.parseInt(ip);
				if (ii < 33 && ip.equals(ii.toString())) {
					return UserConstants.NORMAL;
				}
			} catch (NumberFormatException e) {
				LogUtils.ERROR_LOG.error("子网掩码输入了非数字字符" , e);
			}

			return UserConstants.EXCEPTION;
		}

		return checkIpAddress(ip);
	}

    @Override
    public AjaxResult downloadF() {
        List<Netdevinfo> list = selectNetdevinfoList(new Netdevinfo());
        String stdout = "";
        StringBuffer sb = new StringBuffer();
        for(Netdevinfo netdev : list) {
            sb.append( netdev.getId()+"\t");
            if(netdev.getDevName().length() >= 8) sb.append(netdev.getDevName()+"\t");
            else sb.append(netdev.getDevName()+"\t\t");
            
            if(netdev.getIpaddr().length() >= 8) sb.append(netdev.getIpaddr()+"\t");
            else sb.append(netdev.getIpaddr()+"\t\t");
            
            if(netdev.getNetmask().length() >= 8) sb.append(netdev.getNetmask()+"\t");
            else sb.append(netdev.getNetmask()+"\t\t");
            
            if(netdev.getGateway().length() >= 8) sb.append(netdev.getGateway()+"\t");
            else sb.append(netdev.getGateway()+"\t\t");
            
            if(netdev.getMac().length() >= 16) sb.append(netdev.getMac()+"\t");
            else if(netdev.getMac().length() >= 8) sb.append(netdev.getMac()+"\t\t");
            else sb.append(netdev.getMac()+"\t\t\t");
            
           
            sb.append( netdev.getComment()+"\n");
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "网络配置-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
