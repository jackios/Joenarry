package com.cs2c.project.module.license.mapper;

import com.cs2c.project.module.license.domain.License;
import java.util.List;	

/**
 * License 数据层
 * 
 * @author Joenas
 * @date 2018-10-11
 */
public interface LicenseMapper 
{
//	/**
//     * 查询License信息
//     *
//     * @param id 磁盘ID
//     * @return License信息
//     */
//	public License selectLicenseById(Integer id);
	
	/**
     * 查询License列表
     * 
     * @param license License信息
     * @return License集合
     */
	public List<License> selectLicenseList(License license);
//
//	/**
//     * 新增License
//     *
//     * @param license License信息
//     * @return 结果
//     */
//	public int insertLicense(License license);
	
	/**
     * 修改License
     * 
     * @param license License信息
     * @return 结果
     */
	public int updateLicense(License license);
	
//	/**
//     * 删除License
//     *
//     * @param id LicenseID
//     * @return 结果
//     */
//	public int deleteLicenseById(Integer id);
//
//	/**
//     * 批量删除License
//     *
//     * @param ids 需要的数据ID
//     * @return 结果
//     */
//	public int deleteLicenseByIds(String[] ids);
	
}