package com.cs2c.project.module.syslog.mapper;

import com.cs2c.project.module.syslog.domain.Syslog;
import java.util.List;	

/**
 * 日志管理 数据层
 * 
 * @author Joenas
 * @date 2018-11-06
 */
public interface SyslogMapper 
{
	/**
     * 查询日志管理信息
     * 
     * @param id 日志管理ID
     * @return 日志管理信息
     */
	public Syslog selectSyslogById(Integer id);
	
	/**
     * 查询日志管理列表
     * 
     * @param syslog 日志管理信息
     * @return 日志管理集合
     */
	public List<Syslog> selectSyslogList(Syslog syslog);
	
	/**
     * 新增日志管理
     * 
     * @param syslog 日志管理信息
     * @return 结果
     */
	public int insertSyslog(Syslog syslog);
	
	/**
     * 修改日志管理
     * 
     * @param syslog 日志管理信息
     * @return 结果
     */
	public int updateSyslog(Syslog syslog);
	
	/**
     * 删除日志管理
     * 
     * @param id 日志管理ID
     * @return 结果
     */
	public int deleteSyslogById(Integer id);
	
	/**
     * 批量删除日志管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSyslogByIds(String[] ids);
	
}