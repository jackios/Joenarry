package com.cs2c.project.module.license.service;

import com.cs2c.project.module.license.domain.License;
import java.util.List;

/**
 * License 服务层
 * 
 * @author Joenas
 * @date 2018-10-11
 */
public interface ILicenseService 
{
//	/**
//     * 查询License信息
//     *
//     * @param id LicenseID
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
//     * 删除License信息
//     *
//     * @param ids 需要删除的数据ID
//     * @return 结果
//     */
//	public int deleteLicenseByIds(String ids);
	
}
