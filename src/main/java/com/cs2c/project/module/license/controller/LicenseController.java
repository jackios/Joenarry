package com.cs2c.project.module.license.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.os.ShellUtils;
import org.apache.commons.exec.CommandLine;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.license.domain.License;
import com.cs2c.project.module.license.service.ILicenseService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * License 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-11
 */
@Controller
@RequestMapping("/module/license")
public class LicenseController extends BaseController
{
    private String prefix = "module/license";
	private static int LIC_LENGTH = 20;
	
	@Autowired
	private ILicenseService licenseService;
	
//	@RequiresPermissions("module:license:view")
//	@GetMapping()
//	public String license(ModelMap mmap)
//	{
//		License license = new License();
//		license.setType("wdiode");
//	    List<License> licenses = licenseService.selectLicenseList(license);
//	    if (licenses.size() != 1) {
//	    	mmap.put("errorMsg", "获取授权信息错误");
//		} else {
//			Map<String, String> result = null;
//			CommandLine commandLine = CommandLine.parse(licenses.get(0).getHostidCmd().trim());
//			try {
//				final long jobTimeout = 15000;
//				final boolean jobInBackground = false;
//				result = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobInBackground);
//				if (result.get("stdout").trim().length() != 0) {
//					mmap.put("hostid", result.get("stdout").trim());
//				} else {
//					mmap.put("errorMsg", "获取授权识别码信息时发生错误");
//					mmap.put("readonly", true);
//					return prefix + "/license";
//				}
//			} catch (IOException e) {
//				LogUtils.ERROR_LOG.error("获取License信息时， 执行命令失败(" + commandLine.toString() + "), " + e);
//				mmap.put("errorMsg", "获取授权识别码信息时发生错误");
//				mmap.put("readonly", true);
//				return prefix + "/license";
//			}
//
//			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			mmap.put("content", licenses.get(0).getContent());
//			mmap.put("dataTime", time.format(licenses.get(0).getDataTime()));
//
//			if (licenses.get(0).getContent().length() == 0) {
//				mmap.put("trial", "");
//				mmap.put("dataTime","");
//			} else {
//				mmap.put("registed", "");
//				mmap.put("readonly", true);
//			}
//		}
//
//		return prefix + "/license";
//	}

	private String getLicenseContent(String lic) {
		File file = new File(lic);
		String licContent = "";
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				while ((licContent = br.readLine()) != null) {
					if (licContent.trim().length() > 0) {
						break;
					}
				}

				br.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("获取注册码信息失败，请联系管理员解决");
				return null;
			}

			// 解决license文件存在, 但是内容为空的情况
			if (licContent == null) {
				licContent = "";
			}
		}

		return licContent;
	}

	private String getHostId(String cmd) {
		// 获取hostid
		Map<String, String> result = null;
		String hostId = "";
		CommandLine commandLine = CommandLine.parse(cmd);
		try {
			final long jobTimeout = 15000;
			final boolean jobInBackground = false;
			result = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobInBackground);
			if (result.get("stdout").trim().length() != 0) {
				hostId = result.get("stdout").trim();
			}
		} catch (IOException e) {
			return null;
		}

		return hostId;
	}

	private Boolean registLicense(String cmd) {
		Map<String, String> result = null;
		CommandLine commandLine = CommandLine.parse(cmd);
		try {
			final long jobTimeout = 15000;
			final boolean jobInBackground = false;
			result = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobInBackground);
			if (result.size() != 0 && result.get("stdout").toLowerCase().indexOf("true") > 0) {
				return true;
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("注册License时， 执行命令失败(" + commandLine.toString() + "), " + e);
		}

		return false;
	}

	private Boolean verifyLicense(String cmd) {
		Map<String, String> result = null;
		CommandLine commandLine = CommandLine.parse(cmd);
		try {
			final long jobTimeout = 15000;
			final boolean jobInBackground = false;
			result = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobInBackground);
			if (result.get("stdout").trim().length() != 0 && result.get("stdout").trim().toLowerCase().indexOf("true") > 0) {
				return true;
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("确认License信息时， 执行命令失败(" + commandLine.toString() + "), " + e);
		}

		return false;
	}



	@RequiresPermissions("module:license:view")
	@GetMapping()
	public String license(ModelMap mmap)
	{
		License license = new License();
		license.setType("wdiode");
	    List<License> licenses = licenseService.selectLicenseList(license);
	    if (licenses.size() != 1) {
	    	mmap.put("errorMsg", "获取授权信息错误");
	    	return prefix + "/license";
		} else {
			Map<String, String> result = null;
			// 获取hostid
			String hostId = getHostId(licenses.get(0).getHostidCmd().trim());
			if (hostId == null) {
				mmap.put("errorMsg", "获取授权识别码信息时发生错误");
				mmap.put("readonly", true);
				return prefix + "/license";
			}

			mmap.put("hostid", hostId);

			// 判断/etc/.wdiode_key 是否存在
			String lic = "/etc/.wdiode_key";

			File file = new File(lic);
			if (file.exists()) {
				String licContent = getLicenseContent(lic);
				if (licContent.trim().length() == LicenseController.LIC_LENGTH) {
					String statusCommand = licenses.get(0).getVerifyCmd().trim() + "  " + licContent.trim() + "  " + hostId;

					LogUtils.ERROR_LOG.error("============= " + statusCommand);
					boolean isRegisted = verifyLicense(statusCommand);
					if (isRegisted) {
						mmap.put("registed", "");
						mmap.put("readonly", true);
						SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						mmap.put("content", licContent.trim());
						mmap.put("dataTime", time.format(licenses.get(0).getDataTime()));
					} else {
						mmap.put("trial", "");
						mmap.put("dataTime","");
					}
				} else if (licContent.trim().length() == 0){
					mmap.put("trial", "");
					mmap.put("dataTime","");
				} else {
					mmap.put("errorMsg", "未知版本，请联系管理员进行处理");
					mmap.put("readonly", true);
				}
			} else {
				mmap.put("trial", "");
				mmap.put("dataTime", "");
			}
		}

		return prefix + "/license";
	}


	@RequiresPermissions("module:license:save")
	@Log(title = "License", businessType = BusinessType.UPDATE)
	@PostMapping("/save")
	@ResponseBody
	public AjaxResult registerSave(License license)
	{
		License dbLicense = new License();
		dbLicense.setType("wdiode");
		List<License> licenses = licenseService.selectLicenseList(dbLicense);

		// 1. 数据库只存在一条记录
		// 2. 数据库中content字段为空
		// 3. 传入的参数中content不为空
		if (licenses.size() == 1) {
			if (registLicense(licenses.get(0).getRegisterCmd().trim() + " " + license.getContent() + " " + license.getHostid())) {
				dbLicense = licenses.get(0);
				license.setDataTime(new Date());
				license.setType("wdiode");
				license.setId(dbLicense.getId());
				license.setRegisterCmd(dbLicense.getRegisterCmd());
				license.setVerifyCmd(dbLicense.getVerifyCmd());
				license.setHostidCmd(dbLicense.getHostidCmd());
				license.setCol1(dbLicense.getCol1());
				license.setCol2(dbLicense.getCol2());
				license.setCol3(dbLicense.getCol3());

				return toAjax(licenseService.updateLicense(license));
			}

			return toAjax(0);
		} else {
			LogUtils.ERROR_LOG.error("License数据库异常，请联系厂商排查原因");
		}

		// 更新失败
		return toAjax(0);
	}

//	/**
//	 * 查询license列表
//	 */
//	@RequiresPermissions("module:license:list")
//	@PostMapping("/list")
//	@ResponseBody
//	public TableDataInfo list(License license)
//	{
//		startPage();
//        List<License> list = licenseService.selectLicenseList(license);
//		return getDataTable(list);
//	}
	
//	/**
//	 * 新增license
//	 */
//	@GetMapping("/add")
//	public String add()
//	{
//	    return prefix + "/add";
//	}
//
//	/**
//	 * 新增保存license
//	 */
//	@RequiresPermissions("module:license:add")
//	@Log(title = "License", businessType = BusinessType.INSERT)
//	@PostMapping("/add")
//	@ResponseBody
//	public AjaxResult addSave(License license)
//	{
//		return toAjax(licenseService.insertLicense(license));
//	}
//
//	/**
//	 * 修改license
//	 */
//	@GetMapping("/edit/{id}")
//	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
//	{
//		License license = licenseService.selectLicenseById(id);
//		mmap.put("license", license);
//	    return prefix + "/edit";
//	}






	/**
	 * 修改保存license
	 */
//	@RequiresPermissions("module:license:save")
//	@Log(title = "License", businessType = BusinessType.UPDATE)
//	@PostMapping("/save")
//	@ResponseBody
//	public AjaxResult registerSave(License license)
//	{
//		License dbLicense = new License();
//		dbLicense.setType("wdiode");
//		List<License> licenses = licenseService.selectLicenseList(dbLicense);
//
//		// 1. 数据库只存在一条记录
//		// 2. 数据库中content字段为空
//		// 3. 传入的参数中content不为空
//		if (licenses.size() == 1 && "".equals(licenses.get(0).getContent().trim()) && !"".equals(license.getContent().trim())) {
//			final long jobTimeout = 15000;
//			final boolean jobInBackground = false;
//			Map<String, String> result = null;
//			CommandLine commandLine = CommandLine.parse(dbLicense.getRegisterCmd() + " " + license.getContent() + " " + license.getHostid());
//			try {
//				result = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobInBackground);
//
//				if (result.size() != 0 && result.get("stdout").toLowerCase().indexOf("true") > 0) {
//					dbLicense = licenses.get(0);
//					license.setDataTime(new Date());
//					license.setType("wdiode");
//					license.setId(dbLicense.getId());
//					license.setRegisterCmd(dbLicense.getRegisterCmd());
//					license.setVerifyCmd(dbLicense.getVerifyCmd());
//					license.setHostidCmd(dbLicense.getHostidCmd());
//					license.setCol1(dbLicense.getCol1());
//					license.setCol2(dbLicense.getCol2());
//					license.setCol3(dbLicense.getCol3());
//
//					return toAjax(licenseService.updateLicense(license));
//				}
//
//				return toAjax(0);
//			} catch (IOException e) {
//				LogUtils.ERROR_LOG.error("注册License时， 执行命令失败(" + commandLine.toString() + "), " + e);
//			}
//		}
//
//		// 更新失败
//		return toAjax(0);
//	}
	
//	/**
//	 * 删除license
//	 */
//	@RequiresPermissions("module:license:remove")
//	@Log(title = "License", businessType = BusinessType.DELETE)
//	@PostMapping( "/remove")
//	@ResponseBody
//	public AjaxResult remove(String ids)
//	{
//		return toAjax(licenseService.deleteLicenseByIds(ids));
//	}
	
}
