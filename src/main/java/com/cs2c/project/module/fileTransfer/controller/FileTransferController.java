package com.cs2c.project.module.fileTransfer.controller;

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
import com.cs2c.project.module.fileTransfer.domain.FileTransfer;
import com.cs2c.project.module.fileTransfer.service.IFileTransferService;
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
@RequestMapping("/module/fileTransfer")
public class FileTransferController extends BaseController {
	private String prefix = "module/fileTransfer";

	@Autowired
	private IFileTransferService fileTransferService;

	@RequiresPermissions("module:fileTransfer:list")
	@GetMapping()
	public String fileTransfer() {
		return prefix + "/fileTransfer";
	}

	/**
	 * 查询文件传输查询列表
	 */
	@RequiresPermissions("module:fileTransfer:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(FileTransfer fileTransfer,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		startPage();
		List<FileTransfer> list = fileTransferService.selectFileTransferList(fileTransfer, searchValue);
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
	@RequiresPermissions("module:fileTransfer:add")
	@Log(title = "文件传输查询", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FileTransfer fileTransfer) {
		return toAjax(fileTransferService.insertFileTransfer(fileTransfer));
	}

	/**
	 * 修改文件传输查询
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		FileTransfer fileTransfer = fileTransferService.selectFileTransferById(id);
		mmap.put("fileTransfer", fileTransfer);
		return prefix + "/edit";
	}

	/**
	 * 修改保存文件传输查询
	 */
	@RequiresPermissions("module:fileTransfer:edit")
	@Log(title = "文件传输查询", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FileTransfer fileTransfer) {
		return toAjax(fileTransferService.updateFileTransfer(fileTransfer));
	}

	/**
	 * 删除文件传输查询
	 */
	@RequiresPermissions("module:fileTransfer:remove")
	@Log(title = "文件传输查询", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(fileTransferService.deleteFileTransferByIds(ids));
	}

}
