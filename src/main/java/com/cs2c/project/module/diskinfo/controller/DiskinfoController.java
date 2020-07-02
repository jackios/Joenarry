package com.cs2c.project.module.diskinfo.controller;

import java.io.IOException;
import java.util.List;


import com.cs2c.project.module.diskinfo.domain.Diskinfo;
import com.cs2c.project.module.webservice.WebServiceInterface;
import com.cs2c.project.module.webservice.WebServiceInterfaceImplService;
import com.cs2c.project.module.webservice.WebServiceResponse;
import com.cs2c.project.module.webservice.domain.DdosForWebService;
import com.cs2c.project.module.webservice.domain.DiskInformation;
import com.cs2c.project.tool.webservice.ByteToList;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;

import com.cs2c.project.module.diskinfo.service.IDiskinfoService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 磁盘 信息操作处理
 * 
 * @author Joenas
 * @date 2018-11-01
 */
@Controller
@RequestMapping("/module/diskinfo")
@Slf4j
public class DiskinfoController extends BaseController
{
    private String prefix = "module/diskinfo";
	
	@Autowired
	private IDiskinfoService diskinfoService;
	
	@RequiresPermissions("module:diskinfo:view")
	@GetMapping()
	public String diskinfo()
	{
	    return prefix + "/diskinfo";
	}
	
	/**
	 * 查询磁盘列表

	@RequiresPermissions("module:diskinfo:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Diskinfo diskinfo)
	{
		startPage();
        List<Diskinfo> list = diskinfoService.selectDiskinfoList(diskinfo);
		return getDataTable(list);
	}*/
	@RequiresPermissions("module:diskinfo:list")
	@PostMapping("/list")
	@ResponseBody
	public  TableDataInfo list(DiskInformation diskinfo)
	{   //创建对象
		WebServiceInterface webServiceInterface = new WebServiceInterfaceImplService().getWebServiceInterfacePort();
		//获得当前的用户信息
		String name=getUser().getUserName();
		String passwd=getUser().getPassword();
		//认证
		WebServiceResponse diskInfo = webServiceInterface.getDiskInfo(null, name, passwd);
		String hostname=diskInfo.getHostName();
		List<DiskInformation> list= null;
		try {
			list = ByteToList.uncompressList(diskInfo.getData());
			list.forEach(i->i.setCol1(hostname));
			//判断是否存在的
			DiskInformation diskInformationexist=new DiskInformation();
			diskInformationexist.setCol1(hostname);
			List<DiskInformation> listInfomationexist= diskinfoService.selectDiskInformationList(diskInformationexist);
			if(!(listInfomationexist.size()>0)){
				for(DiskInformation df:list){
					diskinfoService.insertDiskInformation(df);
				}
			}else{
				for(DiskInformation df:list){
					diskinfoService.updateDiskInformation(df);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info("error");
		}
		if(!(list.size()>0)){
			startPage();
			List<DiskInformation> listinfo = diskinfoService.selectDiskInformationList(diskinfo);
			return  getDataTable(listinfo);

		}
		return getDataTable(list);
	}
	
	/**
	 * 新增磁盘
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存磁盘
	 */
	@RequiresPermissions("module:diskinfo:add")
	@Log(title = "磁盘", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Diskinfo diskinfo)
	{		
		return toAjax(diskinfoService.insertDiskinfo(diskinfo));
	}
	/**
	 * 修改磁盘
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Diskinfo diskinfo = diskinfoService.selectDiskinfoById(id);
		mmap.put("diskinfo", diskinfo);
	    return prefix + "/edit";
	}
	/**
	 * 修改保存磁盘
	 */
	@RequiresPermissions("module:diskinfo:edit")
	@Log(title = "磁盘", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Diskinfo diskinfo)
	{		
		return toAjax(diskinfoService.updateDiskinfo(diskinfo));
	}
	/**
	 * 删除磁盘
	 */
	@RequiresPermissions("module:diskinfo:remove")
	@Log(title = "磁盘", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(diskinfoService.deleteDiskinfoByIds(ids));
	}
	
}
