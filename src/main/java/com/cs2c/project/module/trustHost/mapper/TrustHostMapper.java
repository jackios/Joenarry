package com.cs2c.project.module.trustHost.mapper;

import com.cs2c.project.module.trustHost.domain.TrustHost;
import java.util.List;	

/**
 * 可信主机 数据层
 * 
 * @author Joenas
 * @date 2018-10-13
 */
public interface TrustHostMapper 
{
	/**
     * 查询可信主机信息
     * 
     * @param id 可信主机ID
     * @return 可信主机信息
     */
	public TrustHost selectTrustHostById(Integer id);
	
	/**
     * 查询可信主机列表
     * 
     * @param trustHost 可信主机信息
     * @return 可信主机集合
     */
	public List<TrustHost> selectTrustHostList(TrustHost trustHost);
	
	/**
     * 新增可信主机
     * 
     * @param trustHost 可信主机信息
     * @return 结果
     */
	public int insertTrustHost(TrustHost trustHost);
	
	/**
     * 修改可信主机
     * 
     * @param trustHost 可信主机信息
     * @return 结果
     */
	public int updateTrustHost(TrustHost trustHost);
	
	/**
     * 删除可信主机
     * 
     * @param id 可信主机ID
     * @return 结果
     */
	public int deleteTrustHostById(Integer id);
	
	/**
     * 批量删除可信主机
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTrustHostByIds(String[] ids);
	
}