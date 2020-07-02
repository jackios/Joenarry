package com.cs2c.project.module.filecount.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.filecount.mapper.FilecountMapper;
import com.cs2c.project.module.filecount.domain.Filecount;
import com.cs2c.project.module.filecount.service.IFilecountService;
import com.cs2c.common.support.Convert;

/**
 * 文件个数统计 服务层实现
 * 
 * @author Joenas
 * @date 2018-11-25
 */
@Service
public class FilecountServiceImpl implements IFilecountService 
{
	@Autowired
	private FilecountMapper filecountMapper;

	/**
     * 查询文件个数统计信息
     * 
     * @param id 文件个数统计ID
     * @return 文件个数统计信息
     */
    @Override
	public Filecount selectFilecountById(Long id)
	{
	    return filecountMapper.selectFilecountById(id);
	}
	
	/**
     * 查询文件个数统计列表
     * 
     * @param filecount 文件个数统计信息
     * @return 文件个数统计集合
     */
	@Override
	public List<Filecount> selectFilecountList(Filecount filecount)
	{
	    return filecountMapper.selectFilecountList(filecount);
	}
	
    /**
     * 新增文件个数统计
     * 
     * @param filecount 文件个数统计信息
     * @return 结果
     */
	@Override
	public int insertFilecount(Filecount filecount)
	{
	    return filecountMapper.insertFilecount(filecount);
	}
	
	/**
     * 修改文件个数统计
     * 
     * @param filecount 文件个数统计信息
     * @return 结果
     */
	@Override
	public int updateFilecount(Filecount filecount)
	{
	    return filecountMapper.updateFilecount(filecount);
	}

	/**
     * 删除文件个数统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFilecountByIds(String ids)
	{
		return filecountMapper.deleteFilecountByIds(Convert.toStrArray(ids));
	}
	
}
