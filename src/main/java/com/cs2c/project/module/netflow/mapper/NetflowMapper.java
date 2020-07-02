package com.cs2c.project.module.netflow.mapper;

import com.cs2c.project.module.netflow.domain.Netflow;
import java.util.List;	

/**
 * 网卡流量统计 数据层
 * 
 * @author Joenas
 * @date 2018-11-25
 */
public interface NetflowMapper 
{
	/**
     * 查询网卡流量统计信息
     * 
     * @param id 网卡流量统计ID
     * @return 网卡流量统计信息
     */
	public Netflow selectNetflowById(Long id);
	
	/**
     * 查询网卡流量统计列表
     * 
     * @param netflow 网卡流量统计信息
     * @return 网卡流量统计集合
     */
	public List<Netflow> selectNetflowList(Netflow netflow);

	public List<Netflow> selectNetflowListLastN(Netflow netflow);
	
	/**
     * 新增网卡流量统计
     * 
     * @param netflow 网卡流量统计信息
     * @return 结果
     */
	public int insertNetflow(Netflow netflow);
	
	/**
     * 修改网卡流量统计
     * 
     * @param netflow 网卡流量统计信息
     * @return 结果
     */
	public int updateNetflow(Netflow netflow);
	
	/**
     * 删除网卡流量统计
     * 
     * @param id 网卡流量统计ID
     * @return 结果
     */
	public int deleteNetflowById(Long id);
	
	/**
     * 批量删除网卡流量统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteNetflowByIds(String[] ids);
	
}