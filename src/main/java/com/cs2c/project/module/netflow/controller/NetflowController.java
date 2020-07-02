package com.cs2c.project.module.netflow.controller;

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
import com.cs2c.project.module.netflow.domain.Netflow;
import com.cs2c.project.module.netflow.service.INetflowService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 网卡流量统计 信息操作处理
 * 
 * @author Joenas
 * @date 2018-11-25
 */
@Controller
@RequestMapping("/module/netflow")
public class NetflowController extends BaseController
{
    private String prefix = "module/netflow";
	
	@Autowired
	private INetflowService netflowService;
	
	@RequiresPermissions("module:netflow:view")
	@GetMapping()
	public String netflow()
	{
	    return prefix + "/netflow";
	}
	
	/**
	 * 查询网卡流量统计列表
	 */
	@RequiresPermissions("module:netflow:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Netflow netflow)
	{
		startPage();
        List<Netflow> list = netflowService.selectNetflowList(netflow);
		return getDataTable(list);
	}
	
	/**
	 * 新增网卡流量统计
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存网卡流量统计
	 */
	@RequiresPermissions("module:netflow:add")
	@Log(title = "网卡流量统计", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Netflow netflow)
	{		
		return toAjax(netflowService.insertNetflow(netflow));
	}

	/**
	 * 修改网卡流量统计
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Netflow netflow = netflowService.selectNetflowById(id);
		mmap.put("netflow", netflow);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存网卡流量统计
	 */
	@RequiresPermissions("module:netflow:edit")
	@Log(title = "网卡流量统计", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Netflow netflow)
	{		
		return toAjax(netflowService.updateNetflow(netflow));
	}
	
	/**
	 * 删除网卡流量统计
	 */
	@RequiresPermissions("module:netflow:remove")
	@Log(title = "网卡流量统计", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(netflowService.deleteNetflowByIds(ids));
	}
	
}
