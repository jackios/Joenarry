package com.cs2c.project.module.trustHost.controller;

import java.util.List;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.support.Convert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.trustHost.domain.TrustHost;
import com.cs2c.project.module.trustHost.service.ITrustHostService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 可信主机 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Controller
@RequestMapping("/module/trustHost")
public class TrustHostController extends BaseController
{
    private String prefix = "module/trustHost";
	
	@Autowired
	private ITrustHostService trustHostService;
	
	@RequiresPermissions("module:trustHost:view")
	@GetMapping()
	public String trustHost()
	{
	    return prefix + "/trustHost";
	}
	
	/**
	 * 查询可信主机列表
	 */
	@RequiresPermissions("module:trustHost:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TrustHost trustHost)
	{
		startPage();
        List<TrustHost> list = trustHostService.selectTrustHostList(trustHost);
		return getDataTable(list);
	}
	
	/**
	 * 新增可信主机
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存可信主机
	 */
	@RequiresPermissions("module:trustHost:add")
	@Log(title = "可信主机", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TrustHost trustHost)
	{
		if (trustHost == null)
			trustHost.setIsValid("no");
		else
			trustHost.setIsValid("yes");
		try {
		    return toAjax(trustHostService.insertTrustHost(trustHost));
		}catch(Exception e) {
		    return error();
		}
	}

	/**
	 * 修改可信主机
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		TrustHost trustHost = trustHostService.selectTrustHostById(id);
		mmap.put("trustHost", trustHost);

		if ("127.0.0.1".equals(trustHost.getIp().trim()))
			return "error/unauth";

	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存可信主机
	 */
	@RequiresPermissions("module:trustHost:edit")
	@Log(title = "可信主机", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TrustHost trustHost)
	{		
	    if(trustHost.getIp().trim().equals("127.0.0.1")) 
	        return error();
	    try {
            return toAjax(trustHostService.updateTrustHost(trustHost));
        }catch(Exception e) {
            return error();
        }
		
	}
	
	/**
	 * 删除可信主机
	 */
	@RequiresPermissions("module:trustHost:remove")
	@Log(title = "可信主机", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		TrustHost trustHost = null;
		for (Integer id : Convert.toIntArray(",", ids)) {
			trustHost = trustHostService.selectTrustHostById(id);
			if ("127.0.0.1".equals(trustHost.getIp().trim())) {
				return toAjax(UserConstants.CHANGE_0_RECORD);
			}
		}

		return toAjax(trustHostService.deleteTrustHostByIds(ids));
	}

	/**
	 * 校验IPADDR
	 */
	@PostMapping("/checkIpAddress")
	@ResponseBody
	public String checkIpAddress(@RequestParam(value = "ip") String ip)
	{
		return trustHostService.checkIpAddress(ip);
	}
	
	@PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return trustHostService.downloadF();
    }

}
