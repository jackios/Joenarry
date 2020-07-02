package com.cs2c.project.module.netdevinfo.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cs2c.common.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.netdevinfo.domain.Netdevinfo;
import com.cs2c.project.module.netdevinfo.service.INetdevinfoService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 网络设备 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-11
 */
@Controller
@RequestMapping("/module/netdevinfo")
public class NetdevinfoController extends BaseController
{
    private String prefix = "module/netdevinfo";
	
	@Autowired
	private INetdevinfoService netdevinfoService;
	
	@RequiresPermissions("module:netdevinfo:view")
	@GetMapping()
	public String netdevinfo()
	{
	    return prefix + "/netdevinfo";
	}
	
	/**
	 * 查询网络设备列表
	 */
	@RequiresPermissions("module:netdevinfo:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Netdevinfo netdevinfo)
	{
		startPage();
        List<Netdevinfo> list = netdevinfoService.selectNetdevinfoList(netdevinfo);
		return getDataTable(list);
	}

	/**
	 * 修改网络设备
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Netdevinfo netdevinfo = netdevinfoService.selectNetdevinfoById(id);
		mmap.put("netdevinfo", netdevinfo);
		//LogUtils.ERROR_LOG.error(netdevinfo.toString());
	    return prefix + "/edit";
	}


	/**
	 * 修改保存网络设备
	 */
	@RequiresPermissions("module:netdevinfo:edit")
	@Log(title = "网络设备", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Netdevinfo netdevinfo)
	{		
		return toAjax(netdevinfoService.updateNetdevinfo(netdevinfo));
	}


	/**
	 * 启用网络设备
	 */
	@RequiresPermissions("module:netdevinfo:up")
	@Log(title = "网络设备", businessType = BusinessType.UPDATE)
	@PostMapping("/up")
	@ResponseBody
	public AjaxResult up(Netdevinfo netdevinfo)
	{
		return toAjax(netdevinfoService.upNetdev(netdevinfo));
	}

	/**
	 * 禁用网络设备
	 */
	@RequiresPermissions("module:netdevinfo:down")
	@Log(title = "网络设备", businessType = BusinessType.UPDATE)
	@PostMapping("/down")
	@ResponseBody
	public AjaxResult down(Netdevinfo netdevinfo)
	{
		return toAjax(netdevinfoService.downNetdev(netdevinfo));
	}


	/**
	 * 校验IPADDR
	 */
	@PostMapping("/checkIpAddress")
	@ResponseBody
	public String checkIpAddress(@RequestParam(value = "ip") String ip)
	{
		return netdevinfoService.checkIpAddress(ip);
	}

	/**
	 * 校验Netmask
	 */
	@PostMapping("/checkNetmask")
	@ResponseBody
	public String checkNetmask(@RequestParam(value = "ip") String ip)
	{
		return netdevinfoService.checkNetmask(ip);
	}

	/**
	 * 校验网关
	 */
	@PostMapping("/checkGatewayAddress")
	@ResponseBody
	public String checkGatewayAddress(@RequestParam(value = "ip") String ip)
	{
		return netdevinfoService.checkGatewayAddress(ip);
	}
	
    @PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return netdevinfoService.downloadF();
    }
}
