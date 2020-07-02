package com.cs2c.project.module.endpoint.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.module.endpoint.service.IEndpointService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 端点 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-28
 */
@Controller
@RequestMapping("/module/endpoint")
public class EndpointController extends BaseController
{
    private String prefix = "module/endpoint";
	
	@Autowired
	private IEndpointService endpointService;
	
	@RequiresPermissions("module:endpoint:view")
	@GetMapping()
	public String endpoint()
	{
	    return prefix + "/endpoint";
	}
	
	/**
	 * 查询端点列表
	 */
//	@RequiresPermissions("module:endpoint:list")
//	@PostMapping("/list")
//	@ResponseBody
//	public TableDataInfo list(Endpoint endpoint)
//	{
//		startPage();
//        List<Endpoint> list = endpointService.selectEndpointList(endpoint);
//		return getDataTable(list);
//	}

	/**
	 * 查询端点列表
	 */
	@RequiresPermissions("module:endpoint:list")
	@GetMapping("/list")
	@ResponseBody
	public List<Endpoint> list(Endpoint endpoint)
	{
		List<Endpoint> list = endpointService.selectEndpointList(endpoint);
		return list;
	}
	
	/**
	 * 新增端点
	 */
	@GetMapping("/add/{parentId}")
	public String add(@PathVariable("parentId") Integer parentId, ModelMap mmap)
	{
		Endpoint endpoint = null;
		if (0 != parentId)
		{
			endpoint = endpointService.selectEndpointById(parentId);
		}
		else
		{
			endpoint = new Endpoint();
			endpoint.setId(0);
			endpoint.setName("基目录");
		}
		mmap.put("endpoint", endpoint);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存端点
	 */
	@RequiresPermissions("module:endpoint:add")
	@Log(title = "端点", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Endpoint endpoint)
	{		
		return toAjax(endpointService.insertEndpoint(endpoint));
	}

	/**
	 * 修改端点
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Endpoint endpoint = endpointService.selectEndpointById(id);
		mmap.put("endpoint", endpoint);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存端点
	 */
	@RequiresPermissions("module:endpoint:edit")
	@Log(title = "端点", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Endpoint endpoint)
	{		
		return toAjax(endpointService.updateEndpoint(endpoint));
	}
	
	/**
	 * 删除端点
	 */
	@RequiresPermissions("module:endpoint:remove")
	@Log(title = "端点", businessType = BusinessType.DELETE)
	@PostMapping( "/remove/{id}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("id") Integer id)
	{
		if (endpointService.selectCountEndpointByParentId(id) > 0)
		{
			return error(1, "存在子端点,不允许删除");
		}

		return toAjax(endpointService.deleteEndpointByIds("" + id));
	}

	/**
	 * 校验菜单名称
	 */
	@PostMapping("/checkEndpointNameUnique")
	@ResponseBody
	public String checkMenuNameUnique(Endpoint endpoint)
	{
		return endpointService.checkEndpointNameUnique(endpoint);
	}

	/**
     * 校验主机ip
     */
    @PostMapping("/checkAllowIp")
    @ResponseBody
    public String checkAllow(Endpoint endpoint)
    {
        return endpointService.checkAllow(endpoint);
    }
    
	/**
	 * 校验FTP用户名
	 * */
	@PostMapping("/checkUserNameUnique")
	@ResponseBody
	public String checkUserNameUnique(@RequestParam(value = "username") String username)
	{
		return endpointService.checkUserNameUnique(username);
	}

}
