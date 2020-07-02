package com.cs2c.project.module.dnsinfo.mapper;

import com.cs2c.project.module.dnsinfo.domain.Dnsinfo;
import java.util.List;	

/**
 * DNS数据 数据层
 * 
 * @author Joenas
 * @date 2018-10-13
 */
public interface DnsinfoMapper 
{
	/**
     * 查询DNS数据信息
     * 
     * @param id DNS数据ID
     * @return DNS数据信息
     */
	public Dnsinfo selectDnsinfoById(Integer id);
	
	/**
     * 查询DNS数据列表
     * 
     * @param dnsinfo DNS数据信息
     * @return DNS数据集合
     */
	public List<Dnsinfo> selectDnsinfoList(Dnsinfo dnsinfo);
	
	/**
     * 新增DNS数据
     * 
     * @param dnsinfo DNS数据信息
     * @return 结果
     */
	public int insertDnsinfo(Dnsinfo dnsinfo);

	/**
     * 删除DNS数据
     * 
     * @param id DNS数据ID
     * @return 结果
     */
	public int deleteDnsinfoById(Integer id);
	
	/**
     * 批量删除DNS数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDnsinfoByIds(String[] ids);
	
}