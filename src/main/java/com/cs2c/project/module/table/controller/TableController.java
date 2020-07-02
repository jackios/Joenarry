package com.cs2c.project.module.table.controller;

import java.util.List;
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
import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.module.table.domain.Table;
import com.cs2c.project.module.table.service.ITableService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * ip禁用 信息操作处理
 * 
 * @author Joenas
 * @date 2019-04-24
 */
@Controller
@RequestMapping("/module/table")
public class TableController extends BaseController
{
    private String prefix = "module/table";
	
	@Autowired
	private ITableService tableService;
	
	@RequiresPermissions("module:table:view")
	@GetMapping()
	public String table()
	{
	    return prefix + "/table";
	}
	
	/**
	 * 查询ip禁用列表
	 */
	@RequiresPermissions("module:table:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Table table)
	{
		startPage();
        List<Table> list = tableService.selectTableList(table);
		return getDataTable(list);
	}
	
	/**
	 * 新增ip禁用
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存ip禁用
	 */
	@RequiresPermissions("module:table:add")
	@Log(title = "ip禁用", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Table table)
	{		
		return toAjax(tableService.insertTable(table));
	}

	/**
	 * 修改ip禁用
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Table table = tableService.selectTableById(id);
		mmap.put("table", table);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存ip禁用
	 */
	@RequiresPermissions("module:table:edit")
	@Log(title = "ip禁用", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Table table)
	{		
		return toAjax(tableService.updateTable(table));
	}
	
	/**
	 * 删除ip禁用
	 */
	@RequiresPermissions("module:table:remove")
	@Log(title = "ip禁用", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(tableService.deleteTableByIds(ids));
	}
	
	
	/**
     * 修改保存审计备份
     */
    @RequiresPermissions("module:odit:edit")
    @Log(title = "审计备份", businessType = BusinessType.UPDATE)
    @PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return tableService.downloadF();
    }
    
	 /**
     * 校验ip格式
     */
    @PostMapping("/isip")
    @ResponseBody
    public String isip(Table table)
    {
        int NUM = 0;
        System.out.println(table);
        String ips = table.getIp();         // 1.1.1.1-1.1.1.255
        String[] ip = ips.split("-");       // 1.1.1.1  1.1.1.255
        for(int i=0 ; i<ip.length ; i++) {
            String[] point = ip[i].split("\\."); //1 1 1 1   1 1 1 255        127.0.0.1
            if(point.length != 4) {System.out.println("point.length :"+point.length +" "+ ips +" "+ i +" "+ ip[i]);return "1";}
            int num = 0;
            for(int j=0 ; j<4 ; j++) {
                try {
                    int k =Integer.parseInt(point[j]);
                    num += k*Math.pow(256, 3-j);
                }catch(NumberFormatException e) {System.out.println("point[j] :"+point[j]);return "1";}
            }
            if(num >= NUM) {NUM=num;}
            else {return "1";}
        }
        return "0";
    }
    
    
	
}






