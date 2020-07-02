package com.cs2c.project.system.user.service;

import java.util.List;

import com.cs2c.project.system.user.domain.Cpuinfo;
import com.cs2c.project.system.user.mapper.CpuinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.common.support.Convert;

/**
 * CPU负载使用率 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-10
 */
@Service
public class CpuinfoServiceImpl implements ICpuinfoService 
{
	@Autowired
	private CpuinfoMapper cpuinfoMapper;

	/**
     * 查询CPU负载使用率信息
     * 
     * @param id CPU负载使用率ID
     * @return CPU负载使用率信息
     */
    @Override
	public Cpuinfo selectCpuinfoById(Integer id)
	{
	    return cpuinfoMapper.selectCpuinfoById(id);
	}
	
	/**
     * 查询CPU负载使用率列表
     * 
     * @param cpuinfo CPU负载使用率信息
     * @return CPU负载使用率集合
     */
	@Override
	public List<Cpuinfo> selectCpuinfoList(Cpuinfo cpuinfo)
	{
	    return cpuinfoMapper.selectCpuinfoList(cpuinfo);
	}
	
    /**
     * 新增CPU负载使用率
     * 
     * @param cpuinfo CPU负载使用率信息
     * @return 结果
     */
	@Override
	public int insertCpuinfo(Cpuinfo cpuinfo)
	{
	    return cpuinfoMapper.insertCpuinfo(cpuinfo);
	}
	
	/**
     * 修改CPU负载使用率
     * 
     * @param cpuinfo CPU负载使用率信息
     * @return 结果
     */
	@Override
	public int updateCpuinfo(Cpuinfo cpuinfo)
	{
	    return cpuinfoMapper.updateCpuinfo(cpuinfo);
	}

	/**
     * 删除CPU负载使用率对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCpuinfoByIds(String ids)
	{
		return cpuinfoMapper.deleteCpuinfoByIds(Convert.toStrArray(ids));
	}
	
}
