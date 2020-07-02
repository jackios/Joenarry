package com.cs2c.project.module.diskinfo.service;

import java.util.List;

import com.cs2c.project.module.webservice.domain.DiskInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.diskinfo.mapper.DiskinfoMapper;
import com.cs2c.project.module.diskinfo.domain.Diskinfo;
import com.cs2c.project.module.diskinfo.service.IDiskinfoService;
import com.cs2c.common.support.Convert;

/**
 * 磁盘 服务层实现
 * 
 * @author Joenas
 * @date 2018-11-01
 */
@Service
public class DiskinfoServiceImpl implements IDiskinfoService 
{
	@Autowired
	private DiskinfoMapper diskinfoMapper;

	/**
     * 查询磁盘信息
     * 
     * @param id 磁盘ID
     * @return 磁盘信息
     */
    @Override
	public Diskinfo selectDiskinfoById(Integer id)
	{
	    return diskinfoMapper.selectDiskinfoById(id);
	}
	
	/**
     * 查询磁盘列表
     * 
     * @param diskinfo 磁盘信息
     * @return 磁盘集合
     */
	@Override
	public List<Diskinfo> selectDiskinfoList(Diskinfo diskinfo)
	{
	    return diskinfoMapper.selectDiskinfoList(diskinfo);
	}
	/**
	 * 查询磁盘列表
	 *
	 * @param diskInformation 磁盘信息
	 * @return 磁盘集合
	 */
	@Override
	public List<DiskInformation> selectDiskInformationList(DiskInformation diskInformation)
	{
		return diskinfoMapper.selectDiskInformationList(diskInformation);
	}
	
    /**
     * 新增磁盘
     * 
     * @param diskinfo 磁盘信息
     * @return 结果
     */
	@Override
	public int insertDiskinfo(Diskinfo diskinfo)
	{
	    return diskinfoMapper.insertDiskinfo(diskinfo);
	}
	/**
	 * 新增磁盘
	 *
	 * @param diskInformation 磁盘信息
	 * @return 结果
	 */
	@Override
	public int insertDiskInformation(DiskInformation diskInformation)
	{
		return diskinfoMapper.insertDiskInformation(diskInformation);
	}
	
	/**
     * 修改磁盘
     * 
     * @param diskinfo 磁盘信息
     * @return 结果
     */
	@Override
	public int updateDiskinfo(Diskinfo diskinfo)
	{
	    return diskinfoMapper.updateDiskinfo(diskinfo);
	}

	/**
	 * 修改磁盘
	 *
	 * @param diskInformation 磁盘信息
	 * @return 结果
	 */
	@Override
	public int updateDiskInformation(DiskInformation diskInformation)
	{
		return diskinfoMapper.updateDiskInformation(diskInformation);
	}

	/**
     * 删除磁盘对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDiskinfoByIds(String ids)
	{
		return diskinfoMapper.deleteDiskinfoByIds(Convert.toStrArray(ids));
	}
	
}
