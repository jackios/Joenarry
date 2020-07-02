package com.cs2c.project.module.keywordFilter.mapper;

import com.cs2c.project.module.keywordFilter.domain.KeywordFilter;
import java.util.List;	

/**
 * 关键字过滤记录 数据层
 * 
 * @author Joenas
 * @date 2018-11-15
 */
public interface KeywordFilterMapper 
{
	/**
     * 查询关键字过滤记录信息
     * 
     * @param id 关键字过滤记录ID
     * @return 关键字过滤记录信息
     */
	public KeywordFilter selectKeywordFilterById(Integer id);
	
	/**
     * 查询关键字过滤记录列表
     * 
     * @param keywordFilter 关键字过滤记录信息
     * @return 关键字过滤记录集合
     */
	public List<KeywordFilter> selectKeywordFilterList(KeywordFilter keywordFilter);
	
	/**
     * 新增关键字过滤记录
     * 
     * @param keywordFilter 关键字过滤记录信息
     * @return 结果
     */
	public int insertKeywordFilter(KeywordFilter keywordFilter);
	
	/**
     * 修改关键字过滤记录
     * 
     * @param keywordFilter 关键字过滤记录信息
     * @return 结果
     */
	public int updateKeywordFilter(KeywordFilter keywordFilter);
	
	/**
     * 删除关键字过滤记录
     * 
     * @param id 关键字过滤记录ID
     * @return 结果
     */
	public int deleteKeywordFilterById(Integer id);
	
	/**
     * 批量删除关键字过滤记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteKeywordFilterByIds(String[] ids);
	
}