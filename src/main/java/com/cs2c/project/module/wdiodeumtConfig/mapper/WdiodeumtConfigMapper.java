package com.cs2c.project.module.wdiodeumtConfig.mapper;

import com.cs2c.project.module.wdiodeumtConfig.domain.WdiodeumtConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 端口转发配置文件 数据层
 * 
 * @author Joenas
 * @date 2020-03-05
 */
public interface WdiodeumtConfigMapper 
{
	/**
     * 查询端口转发配置文件信息
     * 
     * @param id 端口转发配置文件ID
     * @return 端口转发配置文件信息
     */
	public WdiodeumtConfig selectWdiodeumtConfigById(Integer id);
	
	/**
     * 查询端口转发配置文件列表
     *
     * @return 端口转发配置文件集合
     */
	public List<WdiodeumtConfig> selectWdiodeumtConfigList(@Param("last_count") Integer count);
	
	/**
     * 新增端口转发配置文件
     * 
     * @param wdiodeumtConfig 端口转发配置文件信息
     * @return 结果
     */
	public int insertWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig);
	
	/**
     * 修改端口转发配置文件
     * 
     * @param wdiodeumtConfig 端口转发配置文件信息
     * @return 结果
     */
	public int updateWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig);
	
	/**
     * 删除端口转发配置文件
     * 
     * @param id 端口转发配置文件ID
     * @return 结果
     */
	public int deleteWdiodeumtConfigById(Integer id);
	
	/**
     * 批量删除端口转发配置文件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWdiodeumtConfigByIds(String[] ids);
	
}