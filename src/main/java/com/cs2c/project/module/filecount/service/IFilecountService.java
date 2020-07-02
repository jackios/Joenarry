package com.cs2c.project.module.filecount.service;

import com.cs2c.project.module.filecount.domain.Filecount;
import java.util.List;

/**
 * 文件个数统计 服务层
 * 
 * @author Joenas
 * @date 2018-11-25
 */
public interface IFilecountService 
{
	/**
     * 查询文件个数统计信息
     * 
     * @param id 文件个数统计ID
     * @return 文件个数统计信息
     */
	public Filecount selectFilecountById(Long id);
	
	/**
     * 查询文件个数统计列表
     * 
     * @param filecount 文件个数统计信息
     * @return 文件个数统计集合
     */
	public List<Filecount> selectFilecountList(Filecount filecount);
	
	/**
     * 新增文件个数统计
     * 
     * @param filecount 文件个数统计信息
     * @return 结果
     */
	public int insertFilecount(Filecount filecount);
	
	/**
     * 修改文件个数统计
     * 
     * @param filecount 文件个数统计信息
     * @return 结果
     */
	public int updateFilecount(Filecount filecount);
		
	/**
     * 删除文件个数统计信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFilecountByIds(String ids);
	
}
