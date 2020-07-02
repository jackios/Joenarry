package com.cs2c.project.module.syDS.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.syDS.domain.SyDS;
import com.cs2c.project.module.syDS.service.ISyDSService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * DS设置 信息操作处理
 * 
 * @author Joenas
 * @date 2019-01-03
 */
@Controller
@RequestMapping("/module/syDS")
public class SyDSController extends BaseController
{
    private String prefix = "module/syDS";
	
	@Autowired
	private ISyDSService syDSService;
	
	@RequiresPermissions("module:syDS:view")
	@GetMapping()
	public String syDS()
	{
	    return prefix + "/syDS";
	}
	
	/**
	 * 查询DS设置列表
	 */
	@RequiresPermissions("module:syDS:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SyDS syDS)
	{
		startPage();
        List<SyDS> list = syDSService.selectSyDSList(syDS);
		return getDataTable(list);
	}
	
	/**
	 * 新增DS设置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存DS设置
	 */
	@RequiresPermissions("module:syDS:add")
	@Log(title = "DS设置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SyDS syDS)
	{		
		return syDSService.insertSyDS(syDS);
	}

	/**
	 * 修改DS设置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		SyDS syDS = syDSService.selectSyDSById(id);
		mmap.put("syDS", syDS);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存DS设置
	 */
	@RequiresPermissions("module:syDS:edit")
	@Log(title = "DS设置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SyDS syDS)
	{		
		return syDSService.updateSyDS(syDS);
	}
	
	 /**
     * 删除DS设置
     */
    @RequiresPermissions("module:syDS:remove")
    @Log(title = "DS设置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {       
        System.out.println(ids);
        return toAjax(syDSService.deleteSyDSByIds(ids));
    }
    
	/**
	 * 删除DS设置
	 */
	//@RequiresPermissions("module:syDS:build")
	@Log(title = "DS设置", businessType = BusinessType.EXPORT)
	@PostMapping( "/build")
	@ResponseBody
	public AjaxResult build(SyDS syDS)
	{		
	    System.out.println(syDS);
		return syDSService.build_properties(syDS);
	}
	    
	
    @RequiresPermissions("module:syDS:start")
    @Log(title = "DS设置", businessType = BusinessType.EXPORT)
    @PostMapping( "/start")
    @ResponseBody
    public AjaxResult start(SyDS syDS)
    {       
        System.out.println("开始同步");
        return syDSService.start_system(syDS);
    }
	
	//@RequiresPermissions("module:syDS:stop")
    @Log(title = "DS设置", businessType = BusinessType.EXPORT)
    @PostMapping( "/stop")
    @ResponseBody
    public AjaxResult stop(SyDS syDS)
    {       
        System.out.println("初始化");
        return syDSService.stop_system(syDS);
    }
	
	//@RequiresPermissions("module:syDS:get")
    @Log(title = "DS设置", businessType = BusinessType.OTHER)
    @PostMapping( "/init")
    @ResponseBody
    public int init(SyDS syDS)
    {       
        return syDSService.sys_init(syDS);
    }
    
    @Log(title = "asdasd", businessType = BusinessType.UPDATE)
    @PostMapping( "/upcol2")
    @ResponseBody
    public int upcol2(SyDS syDS)
    {       
        syDSService.upcol2();
        return UserConstants.CHANGE_1_RECORD;
    }
    
}
