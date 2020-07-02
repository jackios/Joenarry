package com.cs2c.project.module.serviceReqpik.controller;

import java.util.Calendar;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.serviceReq.domain.ServiceReq;
import com.cs2c.project.module.serviceReq.service.IServiceReqService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 文件传输 信息操作处理
 * 
 * @author Joenas
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/module/serviceReqpik")
public class ServiceReqpikController extends BaseController {
	private String prefix = "module/serviceReq";

	@Autowired
	private IServiceReqService serviceReqService;

	@RequiresPermissions("module:serviceReq:view")
	@GetMapping()
	public String odit() {
		return prefix + "/serviceReqpik";
	}

	/**
	 * 查询文件传输列表
	 */
	@SuppressWarnings("deprecation")
	@RequiresPermissions("module:serviceReq:view")
	@PostMapping("/list")
	@ResponseBody
	public AjaxResult list(ServiceReq serviceReq, @RequestParam(value = "Year") String Year
	,@RequestParam(value = "Month") String Month,@RequestParam(value = "Date") String Date) {
		// startPage();
		// System.out.println(serviceReq);
		String str = null, 
				dir = serviceReq.getHost();
		System.out.println("Year : " + Year + ", Month : " + Month + ", Date : " + Date + ". ");

		if ( Month.equals("-")) {
			str = serviceReqService.selectServiceReqbyYear(dir, Year);
		}else if ( Date.equals("-")) {
			str = serviceReqService.selectServiceReqbyMonth(dir, Year, Month);
		}else {
			str = serviceReqService.selectServiceReqbyDate(dir, Year, Month, Date);
		}
		
		// System.out.println(str);
		return AjaxResult.success(str);
	}

	@PostMapping("/listnum")
	@ResponseBody
	public AjaxResult listnum() {
		startPage();
		List<ServiceReq> list = serviceReqService.selectServiceReqList(new ServiceReq(), null);
		int num = list.size();
		return AjaxResult.success(num + "");
	}

	/**
	 * 新增文件传输
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存文件传输
	 */
	@RequiresPermissions("module:serviceReq:add")
	@Log(title = "文件传输", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ServiceReq serviceReq) {
		return toAjax(serviceReqService.insertServiceReq(serviceReq));
	}

	/**
	 * 修改文件传输
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		ServiceReq serviceReq = serviceReqService.selectServiceReqById(id);
		mmap.put("odit", serviceReq);
		return prefix + "/edit";
	}

	/**
	 * 修改保存文件传输
	 */
	@RequiresPermissions("module:serviceReq:edit")
	@Log(title = "文件传输", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ServiceReq serviceReq) {
		return toAjax(serviceReqService.updateServiceReq(serviceReq));
	}

//	/**
//	 * 修改保存文件传输
//	 */
//	@RequiresPermissions("module:serviceReq:edit")
//	@Log(title = "文件传输", businessType = BusinessType.UPDATE)
//	@PostMapping("/DF")
//	@ResponseBody
//	public AjaxResult downloadF(ServiceReq serviceReq) {
//		return serviceReqService.downloadF(serviceReq);
//	}

	/**
	 * 删除文件传输
	 */
	@RequiresPermissions("module:serviceReq:remove")
	@Log(title = "文件传输", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(serviceReqService.deleteServiceReqByIds(ids));
	}

	/**
	 * testWdiode
	 */

	@RequiresPermissions("module:serviceReq:view")
	@Log(title = "文件传输", businessType = BusinessType.DELETE)
	@PostMapping("/test")
	@ResponseBody
	public AjaxResult test() {
		return serviceReqService.test();
	}

}
