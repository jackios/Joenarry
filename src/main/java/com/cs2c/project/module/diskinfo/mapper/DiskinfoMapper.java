package com.cs2c.project.module.diskinfo.mapper;

import com.cs2c.project.module.diskinfo.domain.Diskinfo;
import com.cs2c.project.module.webservice.domain.DiskInformation;

import java.util.List;

/**
 * 磁盘 数据层
 * 
 * @author Joenas
 * @date 2018-11-01
 */
public interface DiskinfoMapper 
{
	/**
     * 查询磁盘信息
     * 
     * @param id 磁盘ID
     * @return 磁盘信息
     */
	public Diskinfo selectDiskinfoById(Integer id);
	
	/**
     * 查询磁盘列表
     * 
     * @param diskinfo 磁盘信息
     * @return 磁盘集合
     */
	public List<Diskinfo> selectDiskinfoList(Diskinfo diskinfo);
	/**
	 * 查询磁盘列表
	 *
	 * @param diskInformation 磁盘信息
	 * @return 磁盘集合
	 */
	public List<DiskInformation> selectDiskInformationList(DiskInformation diskInformation);


	/**
     * 新增磁盘
     * 
     * @param diskinfo 磁盘信息
     * @return 结果
     */
	public int insertDiskinfo(Diskinfo diskinfo);

	/**
	 * 新增磁盘
	 *
	 * @param  diskInformation 磁盘信息
	 * @return 结果
	 */
	public int insertDiskInformation(DiskInformation diskInformation);

	/**
     * 修改磁盘
     * 
     * @param diskinfo 磁盘信息
     * @return 结果
     */
	public int updateDiskinfo(Diskinfo diskinfo);
	/**
	 * 修改磁盘
	 *
	 * @param diskInformation 磁盘信息
	 * @return 结果
	 */
	public int updateDiskInformation(DiskInformation diskInformation);
	
	/**
     * 删除磁盘
     * 
     * @param id 磁盘ID
     * @return 结果
     */
	public int deleteDiskinfoById(Integer id);
	
	/**
     * 批量删除磁盘
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDiskinfoByIds(String[] ids);
	
}