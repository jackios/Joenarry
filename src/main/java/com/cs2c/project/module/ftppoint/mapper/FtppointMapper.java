package com.cs2c.project.module.ftppoint.mapper;

import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.module.ftppoint.domain.Ftppoint;
import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 强制访问管理 数据层
 * 
 * @author Joenas
 * @date 2019-04-30
 */
public interface FtppointMapper 
{
	/**
     * 查询强制访问管理信息
     * 
     * @param id 强制访问管理ID
     * @return 强制访问管理信息
     */
	public Ftppoint selectFtppointById(Integer id);
	
	/**
     * 查询强制访问管理列表
     * 
     * @param ftppoint 强制访问管理信息
     * @return 强制访问管理集合
     */
	public List<Ftppoint> selectFtppointList(Ftppoint ftppoint);
	
	/**
     * 新增强制访问管理
     * 
     * @param ftppoint 强制访问管理信息
     * @return 结果
     */
	public int insertFtppoint(Ftppoint ftppoint);
	
	/**
     * 修改强制访问管理
     * 
     * @param ftppoint 强制访问管理信息
     * @return 结果
     */
	public int updateFtppoint(Ftppoint ftppoint);
	
	/**
     * 删除强制访问管理
     * 
     * @param id 强制访问管理ID
     * @return 结果
     */
	public int deleteFtppointById(Integer id);
	
	/**
     * 批量删除强制访问管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFtppointByIds(String[] ids);

    public int selectCountFtppointByParentId(Integer parentId);

    public Ftppoint checkFtppointNameUnique(@Param("name") String name, @Param("parentId") Integer parentId);
    
    
}




