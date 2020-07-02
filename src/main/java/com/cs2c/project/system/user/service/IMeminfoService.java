package com.cs2c.project.system.user.service;


import com.cs2c.project.system.user.domain.Meminfo;

import java.util.List;

/**
 * 内存 服务层
 * 
 * @author Joenas
 * @date 2018-10-10
 */
public interface IMeminfoService 
{
	/**
     * 查询内存信息
     * 
     * @param id 内存ID
     * @return 内存信息
     */
	public Meminfo selectMeminfoById(Integer id);
	
	/**
     * 查询内存列表
     * 
     * @param meminfo 内存信息
     * @return 内存集合
     */
	public List<Meminfo> selectMeminfoList(Meminfo meminfo);
	
	/**
     * 新增内存
     * 
     * @param meminfo 内存信息
     * @return 结果
     */
	public int insertMeminfo(Meminfo meminfo);
	
	/**
     * 修改内存
     * 
     * @param meminfo 内存信息
     * @return 结果
     */
	public int updateMeminfo(Meminfo meminfo);
		
	/**
     * 删除内存信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteMeminfoByIds(String ids);
	
}
