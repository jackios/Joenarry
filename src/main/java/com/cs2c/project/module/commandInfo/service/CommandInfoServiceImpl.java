package com.cs2c.project.module.commandInfo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.commandInfo.mapper.CommandInfoMapper;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.common.support.Convert;

/**
 * CommandInfo 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-12
 */
@Service
public class CommandInfoServiceImpl implements ICommandInfoService 
{
	@Autowired
	private CommandInfoMapper commandInfoMapper;

	/**
     * 查询CommandInfo信息
     * 
     * @param id CommandInfoID
     * @return CommandInfo信息
     */
    @Override
	public CommandInfo selectCommandInfoById(Integer id)
	{
	    return commandInfoMapper.selectCommandInfoById(id);
	}

	/**
	 * 查询CommandInfo信息
	 *
	 * @param key CommandInfoKey
	 * @return CommandInfo信息
	 */
	@Override
	public CommandInfo selectCommandInfoByKey(String key)
	{
		return commandInfoMapper.selectCommandInfoByKey(key);
	}


	/**
     * 查询CommandInfo列表
     * 
     * @param commandInfo CommandInfo信息
     * @return CommandInfo集合
     */
	@Override
	public List<CommandInfo> selectCommandInfoList(CommandInfo commandInfo)
	{
	    return commandInfoMapper.selectCommandInfoList(commandInfo);
	}
	
    /**
     * 新增CommandInfo
     * 
     * @param commandInfo CommandInfo信息
     * @return 结果
     */
	@Override
	public int insertCommandInfo(CommandInfo commandInfo)
	{
	    return commandInfoMapper.insertCommandInfo(commandInfo);
	}
	
	/**
     * 修改CommandInfo
     * 
     * @param commandInfo CommandInfo信息
     * @return 结果
     */
	@Override
	public int updateCommandInfo(CommandInfo commandInfo)
	{
	    return commandInfoMapper.updateCommandInfo(commandInfo);
	}

	/**
     * 删除CommandInfo对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCommandInfoByIds(String ids)
	{
		return commandInfoMapper.deleteCommandInfoByIds(Convert.toStrArray(ids));
	}
	
}
