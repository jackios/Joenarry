package com.cs2c.project.module.serviceM.controller;

import java.util.ArrayList;
import java.util.List;

import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.project.module.serviceM.domain.ViewServiceM;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.serviceM.domain.ServiceM;
import com.cs2c.project.module.serviceM.service.IServiceMService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 服务管理 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-23
 */
@Controller
@RequestMapping("/module/serviceM")
public class ServiceMController extends BaseController
{
    private String prefix = "module/serviceM";
	
	@Autowired
	private IServiceMService serviceMService;
	
	@RequiresPermissions("module:serviceM:view")
	@GetMapping()
	public String serviceM()
	{
	    return prefix + "/serviceM";
	}
	
	/**
	 * 查询服务管理列表
	 */
	@RequiresPermissions("module:serviceM:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ServiceM serviceM)
	{
		startPage();
        List<ServiceM> list = serviceMService.selectServiceMList(serviceM);

        List<ViewServiceM> viewList = new ArrayList<>();
        for (ServiceM l : list) {

        	if (!"admin".equals(ShiroUtils.getUser().getLoginName()) && "no".equals(l.getIsValid())) {
				continue;
			}
			viewList.add(new ViewServiceM(l.getId(), l.getServiceName(),
					serviceMService.getServiceCurrentStatus(l.getServiceKey(), "status"),
					l.getServiceComment(), l.getServiceType(), l.getIsValid()));
		}

		return getDataTable(viewList);
	}
	
	/**
	 * 新增服务管理
	 */
//	@GetMapping("/add")
//	public String add()
//	{
//	    return prefix + "/add";
//	}
	
	/**
	 * 新增保存服务管理
	 */
//	@RequiresPermissions("module:serviceM:add")
//	@Log(title = "服务管理", businessType = BusinessType.INSERT)
//	@PostMapping("/add")
//	@ResponseBody
//	public AjaxResult addSave(ServiceM serviceM)
//	{
//		return toAjax(serviceMService.insertServiceM(serviceM));
//	}

	/**
	 * 修改服务管理
	 */
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
//	{
//		ServiceM serviceM = serviceMService.selectServiceMById(id);
//		mmap.put("serviceM", serviceM);
//	    return prefix + "/edit";
//	}
	
	/**
	 * 修改保存服务管理
	 */
//	@RequiresPermissions("module:serviceM:edit")
//	@Log(title = "服务管理", businessType = BusinessType.UPDATE)
//	@PostMapping("/edit")
//	@ResponseBody
//	public AjaxResult editSave(ServiceM serviceM)
//	{
//		return toAjax(serviceMService.updateServiceM(serviceM));
//	}
//
	/**
	 * 删除服务管理
	 */
//	@RequiresPermissions("module:serviceM:remove")
//	@Log(title = "服务管理", businessType = BusinessType.DELETE)
//	@PostMapping( "/remove")
//	@ResponseBody
//	public AjaxResult remove(String ids)
//	{
//		return toAjax(serviceMService.deleteServiceMByIds(ids));
//	}

	/**
	 * 启动服务管理
	 */
	@RequiresPermissions("module:serviceM:reboot")
	@Log(title = "启停管理-启动", businessType = BusinessType.UPDATE)
	@PostMapping( "/start")
	@ResponseBody
	public AjaxResult start(@RequestParam(value = "id") String ids)
	{
		return toAjax(serviceMService.start(ids));
		//return toAjax(serviceMService.deleteServiceMByIds(ids));
	}

	/**
	 * 停止服务管理
	 */
	@RequiresPermissions("module:serviceM:reboot")
	@Log(title = "启停管理-停止", businessType = BusinessType.UPDATE)
	@PostMapping( "/stop")
	@ResponseBody
	public AjaxResult stop(@RequestParam(value = "id") String ids)
	{
		return toAjax(serviceMService.stop(ids));
		//return toAjax(serviceMService.deleteServiceMByIds(ids));
	}

	/**
	 * 重启服务管理
	 */
	@RequiresPermissions("module:serviceM:reboot")
	@Log(title = "启停管理-重启", businessType = BusinessType.UPDATE)
	@PostMapping( "/reboot")
	@ResponseBody
	public AjaxResult reboot(@RequestParam(value = "id") String ids)
	{
		return toAjax(serviceMService.reboot(ids));
		//return toAjax(serviceMService.deleteServiceMByIds(ids));
	}

	
	
    /**
     * 重启服务管理
     */
    @RequiresPermissions("module:serviceM:reboot")
    @Log(title = "启停管理-重启", businessType = BusinessType.UPDATE)
    @PostMapping( "/sysreboot")
    @ResponseBody
    public AjaxResult sysreboot(@RequestParam(value = "id") String ids)
    {
        return toAjax(serviceMService.sysreboot(ids));
        //return toAjax(serviceMService.deleteServiceMByIds(ids));
    }

    /**
     * 重启服务管理
     */
    @RequiresPermissions("module:serviceM:reboot")
    @Log(title = "启停管理-重启", businessType = BusinessType.UPDATE)
    @PostMapping( "/syspoweroff")
    @ResponseBody
    public AjaxResult poweroff(@RequestParam(value = "id") String ids)
    {
        return toAjax(serviceMService.poweroff(ids));
        //return toAjax(serviceMService.deleteServiceMByIds(ids));
    }
    
    
	/**
	 * 重载服务管理
	 */
	@RequiresPermissions("module:serviceM:reload")
	@Log(title = "启停管理-重载", businessType = BusinessType.UPDATE)
	@PostMapping( "/reload")
	@ResponseBody
	public AjaxResult reload(@RequestParam(value = "id") String ids)
	{
		return toAjax(serviceMService.reload(ids));
		//return toAjax(serviceMService.deleteServiceMByIds(ids));
	}

	/**
	 * 禁用服务管理
	 */
	@RequiresPermissions("module:serviceM:control")
	@Log(title = "启停管理-禁用", businessType = BusinessType.UPDATE)
	@PostMapping( "/disable")
	@ResponseBody
	public AjaxResult disable(@RequestParam(value = "id") String ids)
	{
		return toAjax(serviceMService.disable(ids));
		//return toAjax(serviceMService.deleteServiceMByIds(ids));
	}

	/**
	 * 启用服务管理
	 */
	@RequiresPermissions("module:serviceM:control")
	@Log(title = "启停管理-启用", businessType = BusinessType.UPDATE)
	@PostMapping( "/enable")
	@ResponseBody
	public AjaxResult enable(@RequestParam(value = "id") String ids)
	{
		return toAjax(serviceMService.enable(ids));
		//return toAjax(serviceMService.deleteServiceMByIds(ids));
	}
}
