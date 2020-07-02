package com.cs2c.project.module.ftppoint.controller;

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

import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.module.endpoint.service.IEndpointService;
import com.cs2c.project.module.ftppoint.domain.Ftppoint;
import com.cs2c.project.module.ftppoint.service.IFtppointService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 强制访问管理 信息操作处理
 * 
 * @author Joenas
 * @date 2019-04-30
 */
@Controller
@RequestMapping("/module/ftppoint")
public class FtppointController extends BaseController
{
    private String prefix = "module/ftppoint";
	
	@Autowired
	private IFtppointService ftppointService;

    
    @RequiresPermissions("module:ftppoint:view")
    @GetMapping()
    public String ftppoint()
    {
        return prefix + "/ftppoint";
    }
    
    /**
     * 查询端点列表
     */
//  @RequiresPermissions("module:endpoint:list")
//  @PostMapping("/list")
//  @ResponseBody
//  public TableDataInfo list(Endpoint endpoint)
//  {
//      startPage();
//        List<Endpoint> list = endpointService.selectEndpointList(endpoint);
//      return getDataTable(list);
//  }

    /**
     * 查询端点列表
     */
    @RequiresPermissions("module:endpoint:list")
    @GetMapping("/list")
    @ResponseBody
    public List<Ftppoint> list(Ftppoint ftppoint)
    {
        List<Ftppoint> list2, list = ftppointService.selectFtppointList(ftppoint);
        if (!ShiroUtils.getLoginName().equals("admin")) {
            list2 = new ArrayList<Ftppoint>();
            for(Ftppoint f : list) {
                if(f.getUsername().equals(ShiroUtils.getLoginName()))
                    list2.add(f);
            }
        }else {
            list2 = list;
        }
        return list2;
    }
    
    /**
     * 新增端点
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Integer parentId, ModelMap mmap)
    {
        Ftppoint ftppoint = null;
        if (0 != parentId)
        {
            ftppoint = ftppointService.selectFtppointById(parentId);
        }
        else
        {
            ftppoint = new Ftppoint();
            ftppoint.setId(0);
            ftppoint.setName("基目录");
        }
        ftppoint.setUsername(ShiroUtils.getLoginName());
        mmap.put("ftppoint", ftppoint);
        return prefix + "/add";
    }
    
    /**
     * 新增保存端点
     */
    @RequiresPermissions("module:ftppoint:add")
    @Log(title = "端点", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Ftppoint ftppoint)
    {       
        return toAjax(ftppointService.insertFtppoint(ftppoint));
    }

    /**
     * 修改端点
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        Ftppoint ftppoint = ftppointService.selectFtppointById(id);
        mmap.put("ftppoint", ftppoint);
        return prefix + "/edit";
    }
    
    /**
     * 修改保存端点
     */
    @RequiresPermissions("module:ftppoint:edit")
    @Log(title = "端点", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Ftppoint ftppoint)
    {       
        return toAjax(ftppointService.updateFtppoint(ftppoint));
    }
    
    /**
     * 删除端点
     */
    @RequiresPermissions("module:ftppoint:remove")
    @Log(title = "端点", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Integer id)
    {
        if (ftppointService.selectCountFtppointByParentId(id) > 0)
        {
            return error(1, "存在子端点,不允许删除");
        }

        return toAjax(ftppointService.deleteFtppointByIds("" + id));
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkFtppointNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(Ftppoint ftppoint)
    {
        return ftppointService.checkFtppointNameUnique(ftppoint);
    }

    /**
     * 校验主机ip
     */
    @PostMapping("/checkAllowIp")
    @ResponseBody
    public String checkAllow(Ftppoint ftppoint)
    {
        return ftppointService.checkAllow(ftppoint);
    }
    
    /**
     * 校验FTP用户名
     * */
    @PostMapping("/checkUserNameUnique")
    @ResponseBody
    public String checkUserNameUnique(@RequestParam(value = "username") String username)
    {
        return ftppointService.checkUserNameUnique(username);
    }

}
