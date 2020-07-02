package com.cs2c.project.module.transfer.controller;

import java.util.List;

import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.project.module.wdiodeConfig.service.IWdiodeConfigService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.transfer.domain.Transfer;
import com.cs2c.project.module.transfer.service.ITransferService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 传输控制 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/module/transfer")
public class TransferController extends BaseController
{
    private String prefix = "module/transfer";

    @Autowired
    private IWdiodeConfigService wdiodeConfigService;
	
	@Autowired
	private ITransferService transferService;
	
	@RequiresPermissions("module:transfer:view")
	@GetMapping()
	public String transfer()
	{
	    return prefix + "/transfer";
	}
	
	/**
	 * 查询传输控制列表
	 */
	@RequiresPermissions("module:transfer:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Transfer transfer)
	{
		startPage();
       // List<Transfer> list = transferService.selectTransferList(transfer);
        List<Transfer> list = transferService.getTransferList();
		return getDataTable(list);
	}

	/**
	 * 修改传输控制
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer port, ModelMap mmap)
	{
		//Transfer transfer = new Transfer();//transferService.selectTransferById(id);
		mmap.put("port", port + 10000);
		mmap.put("ip", wdiodeConfigService.selectWdiodeConfigByKey("local_ip").getW_value());
	    return prefix + "/edit";
	}
	
	/**
	 * 任务状态操作
	 */
	@RequiresPermissions("module:transfer:edit")
	@Log(title = "传输控制-更改状态", businessType = BusinessType.UPDATE)
	@PostMapping("/changeStatus/{operation}")
	@ResponseBody
	public AjaxResult changeStatus(@RequestParam(value = "ports") String ports, @RequestParam(value = "pids") String pids, @PathVariable(value = "operation") String operation)
	{
		return transferService.changeStatus(ports, pids, operation);
	}
	
	/**
	 * 删除传输控制
	 */
	@RequiresPermissions("module:transfer:remove")
	@Log(title = "传输控制", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(transferService.deleteTransferByIds(ids));
	}


	@Log(title = "传输控制-下载文件", businessType = BusinessType.EXPORT)
	@RequiresPermissions("module:transfer:view")
	@PostMapping("/download")
	@ResponseBody
	public AjaxResult download(@RequestParam(value = "filename") String filename, @RequestParam(value = "path") String path)
	{
		return transferService.download(path, filename);
	}

	@Log(title = "传输控制-下载文件", businessType = BusinessType.EXPORT)
	@RequiresPermissions("module:transfer:view")
	@PostMapping("/downloadPreview")
	@ResponseBody
	public AjaxResult downloadPreview(@RequestParam(value = "filename") String filename, @RequestParam(value = "path") String path)
	{
		return transferService.downloadPreview(path, filename);
	}

	/**
	 * 生成预览操作
	 */
	@RequiresPermissions("module:transfer:edit")
	@Log(title = "传输控制-生成预览", businessType = BusinessType.UPDATE)
	@PostMapping("/genPreview")
	@ResponseBody
	public AjaxResult genPreview(@RequestParam(value = "dir") String dir, @RequestParam(value = "filename") String filename)
	{
		return transferService.genPreview(dir, filename);
	}
    @PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return transferService.downloadF();
    }
}
