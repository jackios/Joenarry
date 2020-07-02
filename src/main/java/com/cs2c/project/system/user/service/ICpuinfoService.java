package com.cs2c.project.system.user.service;

import com.cs2c.project.system.user.domain.Cpuinfo;

import java.util.List;

/**
 * CPU负载使用率 服务层
 * 
 * @author Joenas
 * @date 2018-10-10
 */
public interface ICpuinfoService 
{
	/**
     * 查询CPU负载使用率信息
     * 
     * @param id CPU负载使用率ID
     * @return CPU负载使用率信息
     */
	public Cpuinfo selectCpuinfoById(Integer id);
	
	/**
     * 查询CPU负载使用率列表
     * 
     * @param cpuinfo CPU负载使用率信息
     * @return CPU负载使用率集合
     */
	public List<Cpuinfo> selectCpuinfoList(Cpuinfo cpuinfo);
	
	/**
     * 新增CPU负载使用率
     * 
     * @param cpuinfo CPU负载使用率信息
     * @return 结果
     */
	public int insertCpuinfo(Cpuinfo cpuinfo);
	
	/**
     * 修改CPU负载使用率
     * 
     * @param cpuinfo CPU负载使用率信息
     * @return 结果
     */
	public int updateCpuinfo(Cpuinfo cpuinfo);
		
	/**
     * 删除CPU负载使用率信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCpuinfoByIds(String ids);
	
}
