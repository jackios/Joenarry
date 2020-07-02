package com.cs2c.project.module.serviceReq.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.serviceReq.domain.ServiceReq;
import java.util.List;

/**
 * 文件传输查询 服务层
 * 
 * @author Joenas
 * @date 2020-04-21
 */
public interface IServiceReqService 
{
	/**
     * 查询文件传输查询信息
     * 
     * @param id 文件传输查询ID
     * @return 文件传输查询信息
     */
	public ServiceReq selectServiceReqById(Long id);
	
	/**
     * 查询文件传输查询列表
     * 
     * @param serviceReq 文件传输查询信息
     * @return 文件传输查询集合
     */
	public List<ServiceReq> selectServiceReqList(ServiceReq serviceReq, String searchValue);
	
	/**
     * 新增文件传输查询
     * 
     * @param serviceReq 文件传输查询信息
     * @return 结果
     */
	public int insertServiceReq(ServiceReq serviceReq);
	
	/**
     * 修改文件传输查询
     * 
     * @param serviceReq 文件传输查询信息
     * @return 结果
     */
	public int updateServiceReq(ServiceReq serviceReq);
		
	/**
     * 删除文件传输查询信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteServiceReqByIds(String ids);

	/**
	 * 按年搜索
	 * 
	 * @param serviceReq 年份数据
	 * @return 搜索结果列表
	 */
	public String selectServiceReqbyDate(String dir, String Year, String Month, String Date);
	public String selectServiceReqbyMonth(String dir, String Year, String Month);
	public String selectServiceReqbyYear(String dir, String Year);
	
	public AjaxResult test();
	
}
