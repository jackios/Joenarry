package com.cs2c.project.system.user.service;

import java.util.List;

import com.cs2c.project.system.user.domain.Meminfo;
import com.cs2c.project.system.user.mapper.MeminfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.common.support.Convert;

/**
 * 内存 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-10
 */
@Service
public class MeminfoServiceImpl implements IMeminfoService 
{
	@Autowired
	private MeminfoMapper meminfoMapper;

	/**
     * 查询内存信息
     * 
     * @param id 内存ID
     * @return 内存信息
     */
    @Override
	public Meminfo selectMeminfoById(Integer id)
	{
	    return meminfoMapper.selectMeminfoById(id);
	}
	
	/**
     * 查询内存列表
     * 
     * @param meminfo 内存信息
     * @return 内存集合
     */
	@Override
	public List<Meminfo> selectMeminfoList(Meminfo meminfo)
	{
	    return meminfoMapper.selectMeminfoList(meminfo);
	}
	
    /**
     * 新增内存
     * 
     * @param meminfo 内存信息
     * @return 结果
     */
	@Override
	public int insertMeminfo(Meminfo meminfo)
	{
	    return meminfoMapper.insertMeminfo(meminfo);
	}
	
	/**
     * 修改内存
     * 
     * @param meminfo 内存信息
     * @return 结果
     */
	@Override
	public int updateMeminfo(Meminfo meminfo)
	{
	    return meminfoMapper.updateMeminfo(meminfo);
	}

	/**
     * 删除内存对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteMeminfoByIds(String ids)
	{
		return meminfoMapper.deleteMeminfoByIds(Convert.toStrArray(ids));
	}
	
}
