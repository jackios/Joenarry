package com.cs2c.project.module.serviceM.mapper;

import com.cs2c.project.module.serviceM.domain.ServiceM;
import java.util.List;	

/**
 * 服务管理 数据层
 * 
 * @author Joenas
 * @date 2018-10-23
 */
public interface ServiceMMapper 
{
	/**
     * 查询服务管理信息
     * 
     * @param id 服务管理ID
     * @return 服务管理信息
     */
	public ServiceM selectServiceMById(Integer id);
	
	/**
     * 查询服务管理列表
     * 
     * @param serviceM 服务管理信息
     * @return 服务管理集合
     */
	public List<ServiceM> selectServiceMList(ServiceM serviceM);
	
	/**
     * 新增服务管理
     * 
     * @param serviceM 服务管理信息
     * @return 结果
     */
	//public int insertServiceM(ServiceM serviceM);
	
	/**
     * 修改服务管理
     * 
     * @param serviceM 服务管理信息
     * @return 结果
     */
	public int updateServiceM(ServiceM serviceM);
	
	/**
     * 删除服务管理
     * 
     * @param id 服务管理ID
     * @return 结果
     */
	//public int deleteServiceMById(Integer id);
	
	/**
     * 批量删除服务管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	//public int deleteServiceMByIds(String[] ids);
	
}