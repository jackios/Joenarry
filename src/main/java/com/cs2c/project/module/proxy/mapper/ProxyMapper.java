package com.cs2c.project.module.proxy.mapper;

import com.cs2c.project.module.proxy.domain.Proxy;
import java.util.List;	

/**
 * 代理配置 数据层
 * 
 * @author Joenas
 * @date 2018-10-24
 */
public interface ProxyMapper 
{
	/**
     * 查询代理配置信息
     * 
     * @param id 代理配置ID
     * @return 代理配置信息
     */
	public Proxy selectProxyById(Integer id);

	/**
	 * 查询代理配置信息
	 *
	 * @param service_id 代理配置ID
	 * @return 代理配置信息
	 */
	public Proxy selectProxyByServiceId(String  service_id);

	/**
     * 查询代理配置列表
     * 
     * @param proxy 代理配置信息
     * @return 代理配置集合
     */
	public List<Proxy> selectProxyList(Proxy proxy);
	
	/**
     * 新增代理配置
     * 
     * @param proxy 代理配置信息
     * @return 结果
     */
	public int insertProxy(Proxy proxy);
	
	/**
     * 修改代理配置
     * 
     * @param proxy 代理配置信息
     * @return 结果
     */
	public int updateProxy(Proxy proxy);
	
	/**
     * 删除代理配置
     * 
     * @param id 代理配置ID
     * @return 结果
     */
	public int deleteProxyById(Integer id);
	
	/**
     * 批量删除代理配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProxyByIds(String[] ids);
	
}