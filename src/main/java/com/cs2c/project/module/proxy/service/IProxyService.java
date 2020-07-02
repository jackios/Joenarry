package com.cs2c.project.module.proxy.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.proxy.domain.Proxy;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 代理配置 服务层
 * 
 * @author Joenas
 * @date 2018-10-24
 */
public interface IProxyService 
{
	/**
     * 查询代理配置信息
     * 
     * @param id 代理配置ID
     * @return 代理配置信息
     */
	public Proxy selectProxyById(Integer id);
	
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
     * 删除代理配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProxyByIds(String ids);

	public String generateServiceID();

	public AjaxResult upload(String serviceId, String gofor, MultipartFile file);

	public String checkServiceId(String serviceId, int id);
	
	public AjaxResult downloadF();

	public int AddConf(String[] IpList, String host);
	
	public int DropConf(String[] IpList, String host);

//	public AjaxResult checkupload(MultipartFile file);
}
