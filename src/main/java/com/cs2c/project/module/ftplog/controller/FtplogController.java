package com.cs2c.project.module.ftplog.controller;

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
import com.cs2c.project.module.ftplog.service.IFtplogService;
import com.cs2c.project.module.ftppoint.domain.Ftppoint;
import com.cs2c.project.module.ftppoint.service.IFtppointService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 强制访问管理 信息操作处理
 * 
 * @author Joenas
 * @date 2019-04-30
 */
@Controller
@RequestMapping("/module/ftplog")
public class FtplogController extends BaseController
{
    private String prefix = "module/ftplog";
	
	@Autowired
	private IFtplogService ftplogService;
	
	@RequiresPermissions("module:ftppoint:view")
	@GetMapping()
	public String ftppoint()
	{
	    return prefix + "/ftplog";
	}
	
	/**
	 * 查询强制访问管理列表
	 */ /*
	@RequiresPermissions("module:ftppoint:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Ftppoint ftppoint)
	{
		startPage();
        List<Ftppoint> list = ftppointService.selectFtppointList(ftppoint);
		return getDataTable(list);
	}
	*/
	/**
	 * 新增强制访问管理
	 */   /*
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	*/
	/**
	 * 新增保存强制访问管理
	 */ /*
	@RequiresPermissions("module:ftppoint:add")
	@Log(title = "强制访问管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Ftppoint ftppoint)
	{		
		return toAjax(ftppointService.insertFtppoint(ftppoint));
	}
    */
	/**
	 * 修改强制访问管理
	 */   /*
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Ftppoint ftppoint = ftppointService.selectFtppointById(id);
		mmap.put("ftppoint", ftppoint);
	    return prefix + "/edit";
	}*/
	
	/**
	 * 修改保存强制访问管理
	 */   /*
	@RequiresPermissions("module:ftppoint:edit")
	@Log(title = "强制访问管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Ftppoint ftppoint)
	{		
		return toAjax(ftppointService.updateFtppoint(ftppoint));
	}
	*/
	/**
	 * 删除强制访问管理
	 *//*
	@RequiresPermissions("module:ftppoint:remove")
	@Log(title = "强制访问管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ftppointService.deleteFtppointByIds(ids));
	}
	*/
    
    @RequiresPermissions("module:ftplog:list")
    @Log(title = "ftp日志", businessType = BusinessType.DELETE)
    @PostMapping( "/show")
    @ResponseBody
    public AjaxResult showlog()
    {       
        return ftplogService.showlog();
    }
    
    
    
}
