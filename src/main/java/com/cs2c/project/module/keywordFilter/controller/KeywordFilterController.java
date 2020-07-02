package com.cs2c.project.module.keywordFilter.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.keywordFilter.domain.KeywordFilter;
import com.cs2c.project.module.keywordFilter.service.IKeywordFilterService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 关键字过滤记录 信息操作处理
 * 
 * @author Joenas
 * @date 2018-11-15
 */
@Controller
@RequestMapping("/module/keywordFilter")
public class KeywordFilterController extends BaseController
{
    private String prefix = "module/keywordFilter";
	
	@Autowired
	private IKeywordFilterService keywordFilterService;
	
	@RequiresPermissions("module:keywordFilter:view")
	@GetMapping()
	public String keywordFilter()
	{
	    return prefix + "/keywordFilter";
	}
	
	/**
	 * 查询关键字过滤记录列表
	 */
	@RequiresPermissions("module:keywordFilter:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(KeywordFilter keywordFilter, @RequestParam(value = "searchValue", required = false) String searchValue)
	{
		startPage();
        List<KeywordFilter> list = keywordFilterService.selectKeywordFilterList(keywordFilter);
        if ( searchValue == null || "".equals(searchValue.trim())) {
        	return getDataTable(list);
		}

        List<KeywordFilter> result = new ArrayList<>();
        for (KeywordFilter kf : list) {
        	if (kf.getFilename().toLowerCase().contains(searchValue.toLowerCase())) {
        		result.add(kf);
			}
		}
		return getDataTable(result);
	}
	
	/**
	 * 新增关键字过滤记录
	 */
	/*@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}*/
	
	/**
	 * 新增保存关键字过滤记录
	 */
	/*@RequiresPermissions("module:keywordFilter:add")
	@Log(title = "关键字过滤记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(KeywordFilter keywordFilter)
	{		
		return toAjax(keywordFilterService.insertKeywordFilter(keywordFilter));
	}*/

	/**
	 * 修改关键字过滤记录
	 */
	/*@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		KeywordFilter keywordFilter = keywordFilterService.selectKeywordFilterById(id);
		mmap.put("keywordFilter", keywordFilter);
	    return prefix + "/edit";
	}*/
	
	/**
	 * 修改保存关键字过滤记录
	 */
	/*
	@RequiresPermissions("module:keywordFilter:edit")
	@Log(title = "关键字过滤记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(KeywordFilter keywordFilter)
	{		
		return toAjax(keywordFilterService.updateKeywordFilter(keywordFilter));
	}
	*/
	
	/**
	 * 删除关键字过滤记录
	 */
	/*
	@RequiresPermissions("module:keywordFilter:remove")
	@Log(title = "关键字过滤记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(keywordFilterService.deleteKeywordFilterByIds(ids));
	}
	*/
    @PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return keywordFilterService.downloadF();
    }
}
