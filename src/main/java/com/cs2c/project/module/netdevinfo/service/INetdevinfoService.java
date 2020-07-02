package com.cs2c.project.module.netdevinfo.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.netdevinfo.domain.Netdevinfo;
import java.util.List;

/**
 * 网络设备 服务层
 * 
 * @author Joenas
 * @date 2018-10-11
 */
public interface INetdevinfoService 
{
	/**
     * 查询网络设备信息
     * 
     * @param id 网络设备ID
     * @return 网络设备信息
     */
	public Netdevinfo selectNetdevinfoById(Integer id);
	
	/**
     * 查询网络设备列表
     * 
     * @param netdevinfo 网络设备信息
     * @return 网络设备集合
     */
	public List<Netdevinfo> selectNetdevinfoList(Netdevinfo netdevinfo);



	/**
	 * 修改网络设备
	 *
	 * @param netdevinfo 网络设备信息
	 * @return 结果
	 */
	public int updateNetdevinfo(Netdevinfo netdevinfo);

	/**
	 * 启用网络设备
	 *
	 * @param netdevinfo 网络设备信息
	 * @return 结果
	 */
	public int upNetdev(Netdevinfo netdevinfo);

	/**
	 * 禁用网络设备
	 *
	 * @param netdevinfo 网络设备信息
	 * @return 结果
	 */
	public int downNetdev(Netdevinfo netdevinfo);


	/**
	 * 校验IPADDR
	 */
	public String checkIpAddress(String ip);

	/**
	 * 校验Netmask
	 */
	public String checkNetmask(String ip);

	/**
	 * 校验网关
	 */
	public String checkGatewayAddress( String ip);
	
	public AjaxResult downloadF();
}
