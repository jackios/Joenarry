package com.cs2c.project.module.wdiodeConfig.mapper;

import com.cs2c.project.module.wdiodeConfig.domain.WdiodeConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * wdiode配置 数据层
 * 
 * @author Joenas
 * @date 2018-10-14
 */
public interface WdiodeConfigMapper 
{
	/**
     * 查询wdiode配置信息
     * 
     * @param id wdiode配置ID
     * @return wdiode配置信息
     */
	public WdiodeConfig selectWdiodeConfigById(Integer id);
	
	/**
     * 查询wdiode配置列表
     *
	 * @param count 查询最后几条记录
     * @return wdiode配置集合
     */
	public List<WdiodeConfig> selectWdiodeConfigList(@Param("last_count") Integer count);
	
	/**
     * 新增wdiode配置
     * 
     * @param wdiodeConfig wdiode配置信息
     * @return 结果
     */
	public int insertWdiodeConfig(WdiodeConfig wdiodeConfig);

	/**
	 * 修改wdiode配置
	 *
	 * @param wdiodeConfig wdiode配置信息
	 * @return 结果
	 */
	public int updateWdiodeConfig(WdiodeConfig wdiodeConfig);

	/**
	 * 删除wdiode配置
	 *
	 * @param id wdiode配置ID
	 * @return 结果
	 */
	public int deleteWdiodeConfigById(Integer id);

	/**
	 * 批量删除wdiode配置
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteWdiodeConfigByIds(String[] ids);

}