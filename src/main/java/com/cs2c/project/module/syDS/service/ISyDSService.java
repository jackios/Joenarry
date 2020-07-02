package com.cs2c.project.module.syDS.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.syDS.domain.SyDS;
import java.util.List;

/**
 * DS设置 服务层
 * 
 * @author Joenas
 * @date 2019-01-03
 */
public interface ISyDSService 
{
	/**
     * 查询DS设置信息
     * 
     * @param id DS设置ID
     * @return DS设置信息
     */
	public SyDS selectSyDSById(Integer id);
	
	/**
     * 查询DS设置列表
     * 
     * @param syDS DS设置信息
     * @return DS设置集合
     */
	public List<SyDS> selectSyDSList(SyDS syDS);
	
	/**
     * 新增DS设置
     * 
     * @param syDS DS设置信息
     * @return 结果
     */
	public AjaxResult insertSyDS(SyDS syDS);
	
	/**
     * 修改DS设置
     * 
     * @param syDS DS设置信息
     * @return 结果
     */
	public AjaxResult updateSyDS(SyDS syDS);
		
	/**
     * 删除DS设置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSyDSByIds(String ids);
	
    public AjaxResult build_properties(SyDS syDS);
    public AjaxResult start_system(SyDS syDS);
    public AjaxResult stop_system(SyDS syDS) ;
    public int sys_init(SyDS syDS) ;
    public void upcol2() ;
	
}
