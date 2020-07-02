package com.cs2c.project.module.retain.mapper;

import com.cs2c.project.module.retain.domain.Retain;
import java.util.List;	

/**
 * 单向配置 数据层
 * 
 * @author Joenas
 * @date 2018-10-14
 */
public interface RetainMapper 
{
	/**
     * 查询单向配置信息
     * 
     * @param id 单向配置ID
     * @return 单向配置信息
     */
	public Retain selectRetainById(Integer id);
	
	/**
     * 查询单向配置列表
     * 
     * @param retain 单向配置信息
     * @return 单向配置集合
     */
	public List<Retain> selectRetainList(Retain retain);
	
	/**
     * 新增单向配置
     * 
     * @param retain 单向配置信息
     * @return 结果
     */
	public int insertRetain(Retain retain);
	
	/**
     * 修改单向配置
     * 
     * @param retain 单向配置信息
     * @return 结果
     */
	public int updateRetain(Retain retain);
	
	/**
     * 删除单向配置
     * 
     * @param id 单向配置ID
     * @return 结果
     */
	public int deleteRetainById(Integer id);
	
	/**
     * 批量删除单向配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRetainByIds(String[] ids);
	
}