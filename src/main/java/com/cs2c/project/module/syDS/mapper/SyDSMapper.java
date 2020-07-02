package com.cs2c.project.module.syDS.mapper;

import com.cs2c.project.module.syDS.domain.SyDS;
import java.util.List;	

/**
 * DS设置 数据层
 * 
 * @author Joenas
 * @date 2019-01-03
 */
public interface SyDSMapper 
{
	/**
     * 查询DS设置信息
     * 
     * @param id DS设置ID
     * @return DS设置信息
     */
	public SyDS selectSyDSById(Integer id);
	
	/**
     * 查询DS设置信息
     * 
     * @param group_id DS设置ID
     * @return DS设置信息
     */
	public SyDS selectSyDSByGroupId(String groupId);
	
	/**
     * 查询DS设置列表
     * 
     * @param syDS DS设置信息
     * @return DS设置集合
     */
	public List<SyDS> selectSyDSList(SyDS syDS);
	
	/**
     * 查询DS设置列表
     * 
     * @param syDS DS设置信息
     * @return DS设置集合
     */
	public List<SyDS> selectSyDSing(String col1);

    /**
     * 查询同步数
     * 
     * @param date 开始同步时间
     * @return 结果
     */
    public List<SyDS> upcol2tcn(String date);
    
    public List<SyDS> upcol2rsw(String date);
	
	/**
     * 新增DS设置
     * 
     * @param syDS DS设置信息
     * @return 结果
     */
	public int insertSyDS(SyDS syDS);
	
	/**
     * 修改DS设置
     * 
     * @param syDS DS设置信息
     * @return 结果
     */
	public int updateSyDS(SyDS syDS);
	
	/**
     * 删除DS设置
     * 
     * @param id DS设置ID
     * @return 结果
     */
	public int deleteSyDSById(Integer id);
	
	
	/**生成一个节点的配置文件时，其他节点失去配置文件
	    */
	    public int ResetSyDS(int id);
	    
	/**
     * 删除DS设置
     * 
     * @param id DS设置ID
     * @return 结果
     */
    public int deleteSyDSByGroupId(String groupId);
    
	/**
     * 批量删除DS设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSyDSByIds(String[] ids);

	/**
     * 初始化col1设置
     * 
     * @param null
     * @return null
     */
	public int init(int id);
}