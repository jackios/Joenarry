package com.cs2c.project.module.netflow.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.netflow.mapper.NetflowMapper;
import com.cs2c.project.module.netflow.domain.Netflow;
import com.cs2c.project.module.netflow.service.INetflowService;
import com.cs2c.common.support.Convert;

/**
 * 网卡流量统计 服务层实现
 * 
 * @author Joenas
 * @date 2018-11-25
 */
@Service
public class NetflowServiceImpl implements INetflowService 
{
	@Autowired
	private NetflowMapper netflowMapper;

	/**
     * 查询网卡流量统计信息
     * 
     * @param id 网卡流量统计ID
     * @return 网卡流量统计信息
     */
    @Override
	public Netflow selectNetflowById(Long id)
	{
	    return netflowMapper.selectNetflowById(id);
	}
	
	/**
     * 查询网卡流量统计列表
     * 
     * @param netflow 网卡流量统计信息
     * @return 网卡流量统计集合
     */
	@Override
	public List<Netflow> selectNetflowList(Netflow netflow)
	{
	    return netflowMapper.selectNetflowList(netflow);
	}

	@Override
	public List<Netflow> selectNetflowListLastN(Netflow netflow)
	{
		return netflowMapper.selectNetflowListLastN(netflow);
	}


	/**
     * 新增网卡流量统计
     * 
     * @param netflow 网卡流量统计信息
     * @return 结果
     */
	@Override
	public int insertNetflow(Netflow netflow)
	{
	    return netflowMapper.insertNetflow(netflow);
	}
	
	/**
     * 修改网卡流量统计
     * 
     * @param netflow 网卡流量统计信息
     * @return 结果
     */
	@Override
	public int updateNetflow(Netflow netflow)
	{
	    return netflowMapper.updateNetflow(netflow);
	}

	/**
     * 删除网卡流量统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteNetflowByIds(String ids)
	{
		return netflowMapper.deleteNetflowByIds(Convert.toStrArray(ids));
	}
	
}
