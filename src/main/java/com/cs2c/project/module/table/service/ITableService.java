package com.cs2c.project.module.table.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.table.domain.Table;
import java.util.List;

/**
 * ip禁用 服务层
 * 
 * @author Joenas
 * @date 2019-04-24
 */
public interface ITableService 
{
	/**
     * 查询ip禁用信息
     * 
     * @param id ip禁用ID
     * @return ip禁用信息
     */
	public Table selectTableById(Integer id);
	
	/**
     * 查询ip禁用列表
     * 
     * @param table ip禁用信息
     * @return ip禁用集合
     */
	public List<Table> selectTableList(Table table);
	
	/**
     * 新增ip禁用
     * 
     * @param table ip禁用信息
     * @return 结果
     */
	public int insertTable(Table table);
	
	/**
     * 修改ip禁用
     * 
     * @param table ip禁用信息
     * @return 结果
     */
	public int updateTable(Table table);
		
	/**
     * 删除ip禁用信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTableByIds(String ids);
	
	public AjaxResult downloadF();
	
}
