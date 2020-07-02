package com.cs2c.project.module.dnsinfo.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.dnsinfo.domain.Dnsinfo;
import com.cs2c.project.module.dnsinfo.service.IDnsinfoService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * DNS数据 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Controller
@RequestMapping("/module/dnsinfo")
public class DnsinfoController extends BaseController
{
    private String prefix = "module/dnsinfo";
	
	@Autowired
	private IDnsinfoService dnsinfoService;
	
//	@RequiresPermissions("module:dnsinfo:view")
//	@GetMapping()
//	public String dnsinfo()
//	{
//	    return prefix + "/dnsinfo";
//	}
//
	/**
	 * 查询DNS数据列表
	 */
	@RequiresPermissions("module:dnsinfo:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Dnsinfo dnsinfo)
	{
		startPage();
        List<Dnsinfo> list = dnsinfoService.selectDnsinfoList(dnsinfo);
		return getDataTable(list);
	}
	
	/**
	 * 新增DNS数据
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存DNS数据
	 */
	@RequiresPermissions("module:dnsinfo:add")
	@Log(title = "DNS数据", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Dnsinfo dnsinfo)
	{		
		return toAjax(dnsinfoService.insertDnsinfo(dnsinfo));
	}

	/**
	 * 删除DNS数据
	 */
	@RequiresPermissions("module:dnsinfo:remove")
	@Log(title = "DNS数据", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(dnsinfoService.deleteDnsinfoByIds(ids));
	}

	/**
	 * 校验DNS地址
	 */
	@PostMapping("/checkIpAddress")
	@ResponseBody
	public String checkIpAddress(@RequestParam(value = "dns") String ip)
	{
		return dnsinfoService.checkIpAddress(ip);
	}

}
