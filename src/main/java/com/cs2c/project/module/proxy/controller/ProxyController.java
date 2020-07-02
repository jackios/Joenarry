package com.cs2c.project.module.proxy.controller;

import java.util.List;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.project.module.proxy.domain.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.proxy.service.IProxyService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 代理配置 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-24
 */
@Controller
@RequestMapping("/module/proxy")
public class ProxyController extends BaseController
{
    private String prefix = "module/proxy";
	
	@Autowired
	private IProxyService proxyService;
	
	@RequiresPermissions("module:proxy:view")
	@GetMapping()
	public String proxy()
	{
	    return prefix + "/proxy";
	}
	
	/**
	 * 查询代理配置列表
	 */
	@RequiresPermissions("module:proxy:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Proxy proxy)
	{
		startPage();
        List<Proxy> list = proxyService.selectProxyList(proxy);
		return getDataTable(list);
	}
	
	/**
	 * 新增代理配置
	 */
	@GetMapping("/add")
	public String add( ModelMap mmap)
	{
		for (Method method : Method.INSTANCES) {
			if (method.getValue().equals("POST")) {
				method.setChecked(true);
			} else {
				method.setChecked(false);
			}
		}

		for (Protocol protocol : Protocol.INSTANCES) {
			if (protocol.getValue().equalsIgnoreCase("HTTP/1.0")) {
				protocol.setChecked(true);
			} else {
				protocol.setChecked(false);
			}
		}

		for (Element_Type element_type : Element_Type.INSTANCES) {
			if (element_type.getValue().equalsIgnoreCase("xml")) {
				element_type.setChecked(true);
			}
			else {
				element_type.setChecked(false);
			}
		}

//		LogUtils.ERROR_LOG.error("====================== " + Element_Type.INSTANCES);

		mmap.put("protocols", Protocol.INSTANCES);
		mmap.put("methods", Method.INSTANCES);
		mmap.put("attachment", "no");
		mmap.put("element_type", Element_Type.INSTANCES);
		mmap.put("element_type_check_in", "no");
		mmap.put("element_type_check_out", "no");
		mmap.put("reqBackgate", "no");
		mmap.put("reqUrllock", "no");

		return prefix + "/add";
	}
	
	/**
	 * 新增保存代理配置
	 */
	@RequiresPermissions("module:proxy:add")
	@Log(title = "代理配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestParam(value = "method") String method, @RequestParam(value = "protocol") String protocol, 
			@RequestParam(value = "element_type") String element_type1, ViewProxy viewProxy)
	{
		Proxy proxy = new Proxy(viewProxy , method, protocol, element_type1);

//		//proxy.setServiceId(proxyService.generateServiceID());
//		proxy.setServiceId(viewProxy.getServiceId());
//		proxy.setServiceName(viewProxy.getServiceName());
//		proxy.setMethod(method);
//		proxy.setProtocol(protocol);
//		proxy.setHost(viewProxy.getHost());
//		proxy.setAllows(viewProxy.getAllows());
//		proxy.setPath(viewProxy.getPath());
//		proxy.setPort(viewProxy.getPort());
//		proxy.setElementType(element_type1);
//		proxy.setAttachmentFn(viewProxy.getAttachment_fn());
//		proxy.setAttachmentCon(viewProxy.getAttachment_con());
//		proxy.setReqHeader(viewProxy.getReqHeader());
//		//proxy.setElementTypeXsd(proxy.getServiceId() + "xsdpath");
//		if ("on".equalsIgnoreCase(viewProxy.getAttachment())) {
//			proxy.setAttachment("yes");
//		} else {
//			proxy.setAttachment("no");
//		}
//
//		if ("on".equalsIgnoreCase(viewProxy.getElement_type_check_in())) {
//			proxy.setElementTypeCheckIn("yes");
//		} else {
//			proxy.setElementTypeCheckIn("no");
//		}
//		if ("on".equalsIgnoreCase(viewProxy.getElement_type_check_out())) {
//            proxy.setElementTypeCheckOut("yes");
//        } else {
//            proxy.setElementTypeCheckOut("no");
//        }
		System.out.println(proxy);
		//proxy.setElements(viewProxy.getElement());

		return toAjax(proxyService.insertProxy(proxy));
	}

	/**
	 * 修改代理配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Proxy proxy = proxyService.selectProxyById(id);
		//System.out.println(proxy);
		for (Method method : Method.INSTANCES) {
//			if (method.getValue().equals(proxy.getMethod())) {
	       if ( proxy.getMethod().indexOf(method.getValue()) >= 0 ) {
				method.setChecked(true);
			} else {
				method.setChecked(false);
			}
		}

		for (Protocol protocol : Protocol.INSTANCES) {
			if (protocol.getValue().equalsIgnoreCase(proxy.getProtocol())) {
				protocol.setChecked(true);
			} else {
				protocol.setChecked(false);
			}
		}

		for (Element_Type element_type : Element_Type.INSTANCES) {
			if (element_type.getValue().equalsIgnoreCase(proxy.getElementType())) {
				element_type.setChecked(true);
			} else {
				element_type.setChecked(false);
			}
		}

		ViewProxy viewProxy = new ViewProxy(proxy, Method.INSTANCES, Protocol.INSTANCES, Element_Type.INSTANCES);
		LogUtils.ERROR_LOG.error(viewProxy.toString());

		mmap.put("proxy", viewProxy);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存代理配置
	 */
	@RequiresPermissions("module:proxy:edit")
	@Log(title = "代理配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestParam(value = "method") String method, @RequestParam(value = "protocol") String protocol, @RequestParam(value = "element_type") String element_type, ViewProxy viewProxy)
	{
		Proxy proxy = proxyService.selectProxyById(viewProxy.getId());
		proxy.Update(viewProxy , method, protocol, element_type);

//		proxy.setServiceId(viewProxy.getServiceId());
//
//		proxy.setServiceName(viewProxy.getServiceName());
//		proxy.setMethod(method);
//		proxy.setProtocol(protocol);
//		proxy.setHost(viewProxy.getHost());
//		proxy.setAllows(viewProxy.getAllows());
//		proxy.setPath(viewProxy.getPath());
//		proxy.setPort(viewProxy.getPort());
//		if ("on".equalsIgnoreCase(viewProxy.getAttachment())) {
//			proxy.setAttachment("yes");
//		} else {
//			proxy.setAttachment("no");
//		}
//
//
//		proxy.setElementType(element_type);
//		proxy.setAttachmentFn(viewProxy.getAttachment_fn());
//		proxy.setAttachmentCon(viewProxy.getAttachment_con());
//
//		if ("on".equalsIgnoreCase(viewProxy.getElement_type_check_in())) {
//			proxy.setElementTypeCheckIn("yes");
//		} else {
//			proxy.setElementTypeCheckIn("no");
//		}
//		if ("on".equalsIgnoreCase(viewProxy.getElement_type_check_out())) {
//            proxy.setElementTypeCheckOut("yes");
//        } else {
//            proxy.setElementTypeCheckOut("no");
//        }

		//proxy.setElements(viewProxy.getElement());

		return toAjax(proxyService.updateProxy(proxy));
	}
	
	/**
	 * 删除代理配置
	 */
	@RequiresPermissions("module:proxy:remove")
	@Log(title = "代理配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proxyService.deleteProxyByIds(ids));
	}

	/**
	 * 上传文件
	 * @param flag
	 * @return
	 */
	@RequiresPermissions("module:wdiodeConfig:edit")
	@GetMapping("/general/{flag}")
	public String general(@PathVariable(value = "flag") String flag, ModelMap mmap) {
	    System.out.println(flag);
	    
		mmap.put("flag", flag);
		return prefix + "/upload";
	}

	@Log(title = "代理配置-上传XSD", businessType = BusinessType.UPDATE)
	@PostMapping("/{flag}/upload")
	@ResponseBody
	public AjaxResult upload(@PathVariable(value = "flag") String flag, @RequestParam("file") MultipartFile file)
	{
	    //System.out.println("gofor : " + gofor);
	    String serviceId = flag.substring(0,flag.length()-1);
	    String gofor = null;
	    if (flag.endsWith("I")) 
	        gofor="in";
	    else if (flag.endsWith("O")) 
	        gofor="out";
//	    System.out.println("flag :" + flag);
//	    System.out.println("serviceId :" + serviceId);
//	    System.out.println("gofor :" + gofor);
		return proxyService.upload(serviceId, gofor, file);
	}

	
	/**
	 * 校验ServiceId
	 */
	@PostMapping("/checkNewServiceID")
	@ResponseBody
	public String checkNewServiceId(@RequestParam(value = "serviceId") String serviceId)
	{
		return proxyService.checkServiceId(serviceId, 0);
	} 
	
	/**
	 * 校验ServiceId
	 */
	@PostMapping("/checkServiceID")
	@ResponseBody
	public String checkServiceId(@RequestParam(value = "serviceId") String serviceId, @RequestParam(value = "id") int id)
	{
		//System.out.println(id);
		return proxyService.checkServiceId(serviceId, id);
	} 
	
	@PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return proxyService.downloadF();
    }
	
}
