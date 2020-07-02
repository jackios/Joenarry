package com.cs2c.project.module.fileTransferpik.controller;

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
import com.cs2c.project.module.fileTransfer.domain.FileTransfer;
import com.cs2c.project.module.fileTransfer.service.IFileTransferService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 文件传输 信息操作处理
 * 
 * @author Joenas
 * @date 2019-03-19
 */
@Controller
@RequestMapping("/module/fileTransferpik")
public class FileTransferpikController extends BaseController {
	private String prefix = "module/fileTransfer";

	@Autowired
	private IFileTransferService fileTransferService;

	@RequiresPermissions("module:fileTransfer:view")
	@GetMapping()
	public String odit() {
		return prefix + "/fileTransferpik";
	}

	/**
	 * 查询文件传输列表
	 */
	@SuppressWarnings("deprecation")
	@RequiresPermissions("module:fileTransfer:view")
	@PostMapping("/list")
	@ResponseBody
	public AjaxResult list(FileTransfer fileTransfer, @RequestParam(value = "Year") String Year
	,@RequestParam(value = "Month") String Month,@RequestParam(value = "Date") String Date) {
		// startPage();
		// System.out.println(fileTransfer);
		String str = null, 
				dir = fileTransfer.getDir();
		System.out.println("Year : " + Year + ", Month : " + Month + ", Date : " + Date + ". ");

		if ( Month.equals("-")) {
			str = fileTransferService.selectFileTransferbyYear(dir, Year);
		}else if ( Date.equals("-")) {
			str = fileTransferService.selectFileTransferbyMonth(dir, Year, Month);
		}else {
			str = fileTransferService.selectFileTransferbyDate(dir, Year, Month, Date);
		}
		
		// System.out.println(str);
		return AjaxResult.success(str);
	}

	@PostMapping("/listnum")
	@ResponseBody
	public AjaxResult listnum() {
		startPage();
		List<FileTransfer> list = fileTransferService.selectFileTransferList(new FileTransfer(), null);
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
	@RequiresPermissions("module:fileTransfer:add")
	@Log(title = "文件传输", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FileTransfer fileTransfer) {
		return toAjax(fileTransferService.insertFileTransfer(fileTransfer));
	}

	/**
	 * 修改文件传输
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		FileTransfer fileTransfer = fileTransferService.selectFileTransferById(id);
		mmap.put("odit", fileTransfer);
		return prefix + "/edit";
	}

	/**
	 * 修改保存文件传输
	 */
	@RequiresPermissions("module:fileTransfer:edit")
	@Log(title = "文件传输", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FileTransfer fileTransfer) {
		return toAjax(fileTransferService.updateFileTransfer(fileTransfer));
	}

//	/**
//	 * 修改保存文件传输
//	 */
//	@RequiresPermissions("module:fileTransfer:edit")
//	@Log(title = "文件传输", businessType = BusinessType.UPDATE)
//	@PostMapping("/DF")
//	@ResponseBody
//	public AjaxResult downloadF(FileTransfer fileTransfer) {
//		return fileTransferService.downloadF(fileTransfer);
//	}

	/**
	 * 删除文件传输
	 */
	@RequiresPermissions("module:fileTransfer:remove")
	@Log(title = "文件传输", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(fileTransferService.deleteFileTransferByIds(ids));
	}

	/**
	 * testWdiode
	 */

	@RequiresPermissions("module:fileTransfer:view")
	@Log(title = "文件传输", businessType = BusinessType.DELETE)
	@PostMapping("/test")
	@ResponseBody
	public AjaxResult test() {
		return fileTransferService.test();
	}

}
