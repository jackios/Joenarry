package com.cs2c.project.module.endpoint.mapper;

import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.system.menu.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 端点 数据层
 * 
 * @author Joenas
 * @date 2018-10-28
 */
public interface EndpointMapper 
{
	/**
     * 查询端点信息
     * 
     * @param id 端点ID
     * @return 端点信息
     */
	public Endpoint selectEndpointById(Integer id);
	
	/**
     * 查询端点列表
     * 
     * @param endpoint 端点信息
     * @return 端点集合
     */
	public List<Endpoint> selectEndpointList(Endpoint endpoint);
	
	/**
     * 新增端点
     * 
     * @param endpoint 端点信息
     * @return 结果
     */
	public int insertEndpoint(Endpoint endpoint);
	
	/**
     * 修改端点
     * 
     * @param endpoint 端点信息
     * @return 结果
     */
	public int updateEndpoint(Endpoint endpoint);
	
	/**
     * 删除端点
     * 
     * @param id 端点ID
     * @return 结果
     */
	public int deleteEndpointById(Integer id);
	
	/**
     * 批量删除端点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEndpointByIds(String[] ids);

	public int selectCountEndpointByParentId(Integer parentId);

	public Endpoint checkEndpointNameUnique(@Param("name") String name, @Param("parentId") Integer parentId);
	
}