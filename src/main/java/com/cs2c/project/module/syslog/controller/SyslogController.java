package com.cs2c.project.module.syslog.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.utils.LogUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.syslog.domain.Syslog;
import com.cs2c.project.module.syslog.service.ISyslogService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 日志管理 信息操作处理
 * 
 * @author Joenas
 * @date 2018-11-06
 */
@Controller
@RequestMapping("/module/syslog")
public class SyslogController extends BaseController
{
    private String prefix = "module/syslog";
	
	@Autowired
	private ISyslogService syslogService;
	
	@RequiresPermissions("module:syslog:view")
	@GetMapping()
	public String syslog()
	{
	    return prefix + "/syslog";
	}
	
	/**
	 * 查询日志管理列表
	 */
	@RequiresPermissions("module:syslog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Syslog syslog)
	{
		startPage();
        List<Syslog> list = syslogService.selectSyslogList(syslog);
		return getDataTable(list);
	}
	
	/**
	 * 新增日志管理
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存日志管理
	 */
	@RequiresPermissions("module:syslog:add")
	@Log(title = "日志管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Syslog syslog)
	{		
		return toAjax(syslogService.insertSyslog(syslog));
	}

	/**
	 * 修改日志管理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Syslog syslog = syslogService.selectSyslogById(id);
		mmap.put("syslog", syslog);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存日志管理
	 */
	@RequiresPermissions("module:syslog:edit")
	@Log(title = "日志管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Syslog syslog)
	{		
		return toAjax(syslogService.updateSyslog(syslog));
	}
	
	/**
	 * 删除日志管理
	 */
	@RequiresPermissions("module:syslog:remove")
	@Log(title = "日志管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(syslogService.deleteSyslogByIds(ids));
	}


	/**
	 * 修改日志状态
	 */
	@RequiresPermissions("module:syslog:remove")
	@Log(title = "日志管理", businessType = BusinessType.UPDATE)
	@PostMapping( "/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(@RequestParam(value = "ip") String ip, @RequestParam(value = "status") String valid, @RequestParam(value = "id") Integer id)
	{
		if ("127.0.0.1".equals(ip.trim())) {
			return AjaxResult.error("请修改远端IP地址");
		}
		Syslog syslog = new Syslog();
		syslog.setId(id);
		if ("yes".equals(valid))
			syslog.setValid("no");
		else
			syslog.setValid("yes");

		Syslog sg = syslogService.selectSyslogById(id);
		sg.setIp(ip);
		sg.setValid(syslog.getValid());

		if (syslog.getValid().trim().equals("yes")) {
			if (enableRsyslogConfig(sg) == UserConstants.CHANGE_0_RECORD) {
				return toAjax(UserConstants.CHANGE_0_RECORD);
			}
		} else {
			if (disableRsyslogConfig(sg) == UserConstants.CHANGE_0_RECORD) {
				return toAjax(UserConstants.CHANGE_0_RECORD);
			}
		}

		int count = syslogService.updateSyslog(sg);
		if ( count == 0) {
			if (syslog.getValid().trim().equals("yes")) {
				if (disableRsyslogConfig(sg) == UserConstants.CHANGE_1_RECORD) {
					LogUtils.ERROR_LOG.error("修改rsyslog配置失败，回滚成功");
				} else {
					LogUtils.ERROR_LOG.error("修改rsyslog配置失败，回滚失败");
				}
			} else {
				if (enableRsyslogConfig(sg) == UserConstants.CHANGE_1_RECORD) {
					LogUtils.ERROR_LOG.error("修改rsyslog配置 失败，回滚成功");
				} else {
					LogUtils.ERROR_LOG.error("修改rsyslog配置 失败，回滚失败");
				}
			}
		}

		return toAjax(count);
	}

	private String getContents(Syslog syslog) {
		StringBuilder sb = new StringBuilder();

		if ("001".equals(syslog.getType())) // 登陆日志
		{
			sb.append("joenarry-login @").append(syslog.getIp().trim()).append(":514");
		} else if ("002".equals(syslog.getType())) // 操作日志
		{
			sb.append("joenarry-oper @").append(syslog.getIp().trim()).append(":514");
		} else if ("003".equals(syslog.getType())) // 传输日志
		{
			sb.append("joenarry-wdiode @").append(syslog.getIp().trim()).append(":514");
		} else
		{
			return null;
		}

		return sb.toString();
	}

	private Integer enableRsyslogConfig(Syslog syslog) {
		String contents = getContents(syslog);
		if (null == contents) {
			return UserConstants.CHANGE_0_RECORD;
		}

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/etc/rsyslog.conf", true)));
			bw.write(contents);
			bw.newLine();
			bw.flush();
			bw.close();

			return UserConstants.CHANGE_1_RECORD;
		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("写入rsyslog配置文件时， 发生异常, 参数为 " + syslog, e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("写入rsyslog配置时， 发生异常, 参数为 " + syslog, e);
		}

		return UserConstants.CHANGE_0_RECORD;
	}

	private Integer disableRsyslogConfig(Syslog syslog) {
		String configContents = getContents(syslog);
		if (null == configContents) {
			return UserConstants.CHANGE_0_RECORD;
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/etc/rsyslog.conf")));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/etc/rsyslog.conf")));

			ArrayList<String> contents = new ArrayList<>();
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().equals(configContents)) {
					continue;
				}
				contents.add(line);
			}

			try {
				br.close();
			} catch (IOException e) {
				try {
					br.close();
				} catch (IOException e1) {
					LogUtils.ERROR_LOG.error("修改rsyslog配置文件时，发生异常 ", e1);
					return UserConstants.CHANGE_0_RECORD;
				}
			}


			for (String s : contents) {
				bw.write(s);
				bw.newLine();
			}

			bw.flush();
			bw.close();

			return UserConstants.CHANGE_1_RECORD;
		} catch (FileNotFoundException e) {
			LogUtils.ERROR_LOG.error("写入rsyslog配置文件时， 发生异常, 参数为 " + syslog, e);
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("写入rsyslog配置时， 发生异常, 参数为 " + syslog, e);
		}

		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 校验IP
	 */
	@PostMapping("/checkIpAddress")
	@ResponseBody
	public String checkIpAddress(@RequestParam(value = "ip") String ip)
	{
		return syslogService.checkIpAddress(ip);
	}

}
