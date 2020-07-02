package com.cs2c.project.module.retain.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.retain.domain.Retain;
import com.cs2c.project.module.retain.service.IRetainService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 单向配置 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-14
 */
@Controller
@RequestMapping("/module/retain")
public class RetainController extends BaseController
{
    private String prefix = "module/retain";
	
	@Autowired
	private IRetainService retainService;
	
	@RequiresPermissions("module:retain:view")
	@GetMapping()
	public String retain()
	{
	    return prefix + "/retain";
	}
	
	/**
	 * 查询单向配置列表
	 */
	@RequiresPermissions("module:retain:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Retain retain)
	{
		startPage();
        List<Retain> list = retainService.selectRetainList(retain);
		return getDataTable(list);
	}
	
	/**
	 * 新增单向配置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存单向配置
	 */
	@RequiresPermissions("module:retain:add")
	@Log(title = "单向配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Retain retain)
	{		
		return toAjax(retainService.insertRetain(retain));
	}

	/**
	 * 修改单向配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Retain retain = retainService.selectRetainById(id);
		mmap.put("retain", retain);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存单向配置
	 */
	@RequiresPermissions("module:retain:edit")
	@Log(title = "单向配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Retain retain)
	{		
		return toAjax(retainService.updateRetain(retain));
	}
	
	/**
	 * 删除单向配置
	 */
	@RequiresPermissions("module:retain:remove")
	@Log(title = "单向配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(retainService.deleteRetainByIds(ids));
	}

	/**
	 * 校验IPADDR
	 */
	@PostMapping("/checkIpAddress")
	@ResponseBody
	public String checkIpAddress(@RequestParam(value = "ip") String ip)
	{
		return retainService.checkIpAddress(ip);
	}

	/**
	 * 校验MAC
	 */
	@PostMapping("/checkMac")
	@ResponseBody
	public String checkMac(@RequestParam(value = "mac") String mac)
	{
		return retainService.checkMac(mac);
	}
	@PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return retainService.downloadF();
    }
	
}
