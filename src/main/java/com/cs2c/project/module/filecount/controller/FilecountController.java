package com.cs2c.project.module.filecount.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.filecount.domain.Filecount;
import com.cs2c.project.module.filecount.service.IFilecountService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 文件个数统计 信息操作处理
 * 
 * @author Joenas
 * @date 2018-11-25
 */
@Controller
@RequestMapping("/module/filecount")
public class FilecountController extends BaseController
{
    private String prefix = "module/filecount";
	
	@Autowired
	private IFilecountService filecountService;
	
	@RequiresPermissions("module:filecount:view")
	@GetMapping()
	public String filecount()
	{
	    return prefix + "/filecount";
	}
	
	/**
	 * 查询文件个数统计列表
	 */
	@RequiresPermissions("module:filecount:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Filecount filecount)
	{
		startPage();
        List<Filecount> list = filecountService.selectFilecountList(filecount);
		return getDataTable(list);
	}
	
	/**
	 * 新增文件个数统计
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存文件个数统计
	 */
	@RequiresPermissions("module:filecount:add")
	@Log(title = "文件个数统计", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Filecount filecount)
	{		
		return toAjax(filecountService.insertFilecount(filecount));
	}

	/**
	 * 修改文件个数统计
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Filecount filecount = filecountService.selectFilecountById(id);
		mmap.put("filecount", filecount);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存文件个数统计
	 */
	@RequiresPermissions("module:filecount:edit")
	@Log(title = "文件个数统计", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Filecount filecount)
	{		
		return toAjax(filecountService.updateFilecount(filecount));
	}
	
	/**
	 * 删除文件个数统计
	 */
	@RequiresPermissions("module:filecount:remove")
	@Log(title = "文件个数统计", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(filecountService.deleteFilecountByIds(ids));
	}
	
}
