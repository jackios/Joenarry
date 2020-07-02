package com.cs2c.project.module.keyword.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.keyword.domain.Keyword;
import com.cs2c.project.module.keyword.service.IKeywordService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 关键字 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Controller
@RequestMapping("/module/keyword")
public class KeywordController extends BaseController
{
    private String prefix = "module/keyword";
	
	@Autowired
	private IKeywordService keywordService;
	
	@RequiresPermissions("module:keyword:view")
	@GetMapping()
	public String keyword()
	{
	    return prefix + "/keyword";
	}
	
	/**
	 * 查询关键字列表
	 */
	@RequiresPermissions("module:keyword:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Keyword keyword)
	{
		startPage();
        List<Keyword> list = keywordService.selectKeywordList(keyword);
		return getDataTable(list);
	}
	
	/**
	 * 新增关键字
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存关键字
	 */
	@RequiresPermissions("module:keyword:add")
	@Log(title = "关键字", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Keyword keyword)
	{		
		return toAjax(keywordService.insertKeyword(keyword));
	}

//	/**
//	 * 修改关键字
//	 */
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
//	{
//		Keyword keyword = keywordService.selectKeywordById(id);
//		mmap.put("keyword", keyword);
//	    return prefix + "/edit";
//	}
	
//	/**
//	 * 修改保存关键字
//	 */
//	@RequiresPermissions("module:keyword:edit")
//	@Log(title = "关键字", businessType = BusinessType.UPDATE)
//	@PostMapping("/edit")
//	@ResponseBody
//	public AjaxResult editSave(Keyword keyword)
//	{
//		return toAjax(keywordService.updateKeyword(keyword));
//	}
	
	/**
	 * 删除关键字
	 */
	@RequiresPermissions("module:keyword:remove")
	@Log(title = "关键字", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(keywordService.deleteKeywordByIds(ids));
	}

	/**
	 * 校验关键字是否已存在
	 */
	@PostMapping("/checkKeyword")
	@ResponseBody
	public String checkIpAddress(@RequestParam(value = "keyword") String keyword)
	{
		return keywordService.checkKeyword(keyword);
	}
	
	@PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return keywordService.downloadF();
    }
	
}
