package com.cs2c.project.module.serviceReq.mapper;

import com.cs2c.project.module.serviceReq.domain.ServiceReq;
import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 文件传输查询 数据层
 * 
 * @author Joenas
 * @date 2020-04-21
 */
public interface ServiceReqMapper 
{
	/**
     * 查询文件传输查询信息
     * 
     * @param id 文件传输查询ID
     * @return 文件传输查询信息
     */
	public ServiceReq selectServiceReqById(Long id);
	
	
	/**
     * 查询文件传输查询信息
     * 
     * @param id 文件传输查询ID
     * @return 文件传输查询信息
     */
	public int selectServiceReqByYear(@Param("year")String year, @Param("month")String month, 
			@Param("day")String day, @Param("hour")String hour, @Param("dir")String dir);
	
	/**
     * 查询文件传输查询列表
     * 
     * @param serviceReq 文件传输查询信息
     * @return 文件传输查询集合
     */
	public List<ServiceReq> selectServiceReqList(ServiceReq serviceReq);
	
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
     * 删除文件传输查询
     * 
     * @param id 文件传输查询ID
     * @return 结果
     */
	public int deleteServiceReqById(Long id);
	
	/**
     * 批量删除文件传输查询
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteServiceReqByIds(String[] ids);
	
}