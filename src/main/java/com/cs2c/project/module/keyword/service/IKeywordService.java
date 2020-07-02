package com.cs2c.project.module.keyword.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.keyword.domain.Keyword;
import java.util.List;

/**
 * 关键字 服务层
 * 
 * @author Joenas
 * @date 2018-10-13
 */
public interface IKeywordService 
{
	/**
     * 查询关键字信息
     * 
     * @param id 关键字ID
     * @return 关键字信息
     */
	public Keyword selectKeywordById(Integer id);
	
	/**
     * 查询关键字列表
     * 
     * @param keyword 关键字信息
     * @return 关键字集合
     */
	public List<Keyword> selectKeywordList(Keyword keyword);
	
	/**
     * 新增关键字
     * 
     * @param keyword 关键字信息
     * @return 结果
     */
	public int insertKeyword(Keyword keyword);
	
//	/**
//     * 修改关键字
//     *
//     * @param keyword 关键字信息
//     * @return 结果
//     */
//	public int updateKeyword(Keyword keyword);
		
	/**
     * 删除关键字信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteKeywordByIds(String ids);

	/**
	 * 校验关键字是否已存在
	 */
	public String checkKeyword(String keyword);
	
	public AjaxResult downloadF();
	
}
