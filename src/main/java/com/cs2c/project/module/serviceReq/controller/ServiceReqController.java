package com.cs2c.project.module.serviceReq.controller;

import java.util.ArrayList;
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
import com.cs2c.framework.web.page.PageDomain;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.page.TableSupport;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 文件传输查询 信息操作处理
 * 
 * @author Joenas
 * @date 2020-04-21
 */
@Controller
@RequestMapping("/module/serviceReq")
public class ServiceReqController extends BaseController {
	private String prefix = "module/serviceReq";

	@Autowired
	private IServiceReqService serviceReqService;

	@RequiresPermissions("module:serviceReq:list")
	@GetMapping()
	public String serviceReq() {
		return prefix + "/serviceReq";
	}

	/**
	 * 查询文件传输查询列表
	 */
	@RequiresPermissions("module:serviceReq:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ServiceReq serviceReq,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		startPage();
		List<ServiceReq> list = serviceReqService.selectServiceReqList(serviceReq, searchValue);
		return getDataTable(list);
	}

	/**
	 * 新增文件传输查询
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存文件传输查询
	 */
	@RequiresPermissions("module:serviceReq:add")
	@Log(title = "文件传输查询", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ServiceReq serviceReq) {
		return toAjax(serviceReqService.insertServiceReq(serviceReq));
	}

	/**
	 * 修改文件传输查询
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		ServiceReq serviceReq = serviceReqService.selectServiceReqById(id);
		mmap.put("serviceReq", serviceReq);
		return prefix + "/edit";
	}

	/**
	 * 修改保存文件传输查询
	 */
	@RequiresPermissions("module:serviceReq:edit")
	@Log(title = "文件传输查询", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ServiceReq serviceReq) {
		return toAjax(serviceReqService.updateServiceReq(serviceReq));
	}

	/**
	 * 删除文件传输查询
	 */
	@RequiresPermissions("module:serviceReq:remove")
	@Log(title = "文件传输查询", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(serviceReqService.deleteServiceReqByIds(ids));
	}

}
