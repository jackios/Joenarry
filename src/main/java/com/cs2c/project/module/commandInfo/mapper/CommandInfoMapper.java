package com.cs2c.project.module.commandInfo.mapper;

import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CommandInfo 数据层
 * 
 * @author Joenas
 * @date 2018-10-12
 */
public interface CommandInfoMapper 
{
	/**
     * 查询CommandInfo信息
     * 
     * @param id CommandInfoID
     * @return CommandInfo信息
     */
	public CommandInfo selectCommandInfoById(Integer id);

	/**
	 * 查询CommandInfo信息
	 *
	 * @param key CommandInfoKey
	 * @return CommandInfo信息
	 */
	public CommandInfo selectCommandInfoByKey(@Param("command_key") String key);


	/**
     * 查询CommandInfo列表
     * 
     * @param commandInfo CommandInfo信息
     * @return CommandInfo集合
     */
	public List<CommandInfo> selectCommandInfoList(CommandInfo commandInfo);
	
	/**
     * 新增CommandInfo
     * 
     * @param commandInfo CommandInfo信息
     * @return 结果
     */
	public int insertCommandInfo(CommandInfo commandInfo);
	
	/**
     * 修改CommandInfo
     * 
     * @param commandInfo CommandInfo信息
     * @return 结果
     */
	public int updateCommandInfo(CommandInfo commandInfo);
	
	/**
     * 删除CommandInfo
     * 
     * @param id CommandInfoID
     * @return 结果
     */
	public int deleteCommandInfoById(Integer id);
	
	/**
     * 批量删除CommandInfo
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommandInfoByIds(String[] ids);
	
}