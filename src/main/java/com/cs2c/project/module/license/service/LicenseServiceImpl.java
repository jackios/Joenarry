package com.cs2c.project.module.license.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.license.mapper.LicenseMapper;
import com.cs2c.project.module.license.domain.License;
import com.cs2c.common.support.Convert;

/**
 * License 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-11
 */
@Service
public class LicenseServiceImpl implements ILicenseService 
{
	@Autowired
	private LicenseMapper licenseMapper;

//	/**
//     * 查询License信息
//     *
//     * @param id LicenseID
//     * @return License信息
//     */
//    @Override
//	public License selectLicenseById(Integer id)
//	{
//	    return licenseMapper.selectLicenseById(id);
//	}
//
	/**
     * 查询License列表
     * 
     * @param license License信息
     * @return License集合
     */
	@Override
	public List<License> selectLicenseList(License license)
	{
	    return licenseMapper.selectLicenseList(license);
	}
	
//    /**
//     * 新增License
//     *
//     * @param license License信息
//     * @return 结果
//     */
//	@Override
//	public int insertLicense(License license)
//	{
//	    return licenseMapper.insertLicense(license);
//	}
	
	/**
     * 修改License
     * 
     * @param license License信息
     * @return 结果
     */
	@Override
	public int updateLicense(License license)
	{
	    return licenseMapper.updateLicense(license);
	}

//	/**
//     * 删除License对象
//     *
//     * @param ids 需要删除的数据ID
//     * @return 结果
//     */
//	@Override
//	public int deleteLicenseByIds(String ids)
//	{
//		return licenseMapper.deleteLicenseByIds(Convert.toStrArray(ids));
//	}
	
}
