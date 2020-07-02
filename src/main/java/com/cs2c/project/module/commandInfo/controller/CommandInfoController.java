package com.cs2c.project.module.commandInfo.controller;

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
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * License 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-12
 */
@Controller
@RequestMapping("/module/commandInfo")
public class CommandInfoController extends BaseController
{
    private String prefix = "module/commandInfo";
	
	@Autowired
	private ICommandInfoService commandInfoService;
	
	@RequiresPermissions("module:commandInfo:view")
	@GetMapping()
	public String commandInfo()
	{
	    return prefix + "/commandInfo";
	}
	
	/**
	 * 查询License列表
	 */
	@RequiresPermissions("module:commandInfo:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(CommandInfo commandInfo)
	{
		startPage();
        List<CommandInfo> list = commandInfoService.selectCommandInfoList(commandInfo);
		return getDataTable(list);
	}
	
	/**
	 * 新增License
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存License
	 */
	@RequiresPermissions("module:commandInfo:add")
	@Log(title = "License", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CommandInfo commandInfo)
	{		
		return toAjax(commandInfoService.insertCommandInfo(commandInfo));
	}

	/**
	 * 修改License
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		CommandInfo commandInfo = commandInfoService.selectCommandInfoById(id);
		mmap.put("commandInfo", commandInfo);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存License
	 */
	@RequiresPermissions("module:commandInfo:edit")
	@Log(title = "License", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CommandInfo commandInfo)
	{		
		return toAjax(commandInfoService.updateCommandInfo(commandInfo));
	}
	
	/**
	 * 删除License
	 */
	@RequiresPermissions("module:commandInfo:remove")
	@Log(title = "License", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(commandInfoService.deleteCommandInfoByIds(ids));
	}
	
}
