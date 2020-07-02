package com.cs2c.project.module.netdevinfo.mapper;

import com.cs2c.project.module.netdevinfo.domain.Netdevinfo;
import java.util.List;	

/**
 * 网络设备 数据层
 * 
 * @author Joenas
 * @date 2018-10-11
 */
public interface NetdevinfoMapper 
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
	
}