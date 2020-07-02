package com.cs2c.project.module.syslog.service;

import java.util.List;

import com.cs2c.common.constant.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.syslog.mapper.SyslogMapper;
import com.cs2c.project.module.syslog.domain.Syslog;
import com.cs2c.project.module.syslog.service.ISyslogService;
import com.cs2c.common.support.Convert;

/**
 * 日志管理 服务层实现
 * 
 * @author Joenas
 * @date 2018-11-06
 */
@Service
public class SyslogServiceImpl implements ISyslogService 
{
	@Autowired
	private SyslogMapper syslogMapper;

	/**
     * 查询日志管理信息
     * 
     * @param id 日志管理ID
     * @return 日志管理信息
     */
    @Override
	public Syslog selectSyslogById(Integer id)
	{
	    return syslogMapper.selectSyslogById(id);
	}
	
	/**
     * 查询日志管理列表
     * 
     * @param syslog 日志管理信息
     * @return 日志管理集合
     */
	@Override
	public List<Syslog> selectSyslogList(Syslog syslog)
	{
	    return syslogMapper.selectSyslogList(syslog);
	}
	
    /**
     * 新增日志管理
     * 
     * @param syslog 日志管理信息
     * @return 结果
     */
	@Override
	public int insertSyslog(Syslog syslog)
	{
	    return syslogMapper.insertSyslog(syslog);
	}
	
	/**
     * 修改日志管理
     * 
     * @param syslog 日志管理信息
     * @return 结果
     */
	@Override
	public int updateSyslog(Syslog syslog)
	{
	    return syslogMapper.updateSyslog(syslog);
	}

	/**
     * 删除日志管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSyslogByIds(String ids)
	{
		return syslogMapper.deleteSyslogByIds(Convert.toStrArray(ids));
	}


	/**
	 * 校验IPADDR
	 */
	@Override
	public String checkIpAddress(String ip)
	{
		if (ip.matches(UserConstants.IPADDRESS_PATTERN)) {
			return UserConstants.NORMAL;
		}
		return UserConstants.EXCEPTION;
	}
}
