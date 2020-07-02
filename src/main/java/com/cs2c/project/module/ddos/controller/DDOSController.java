package com.cs2c.project.module.ddos.controller;

import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.framework.config.JoenasConfig;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.project.module.ddos.domain.DDOS;
import com.cs2c.project.module.keyword.service.IKeywordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 关键字 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Controller
@RequestMapping("/module/ddos")
public class DDOSController extends BaseController
{

	@Autowired
	private JoenasConfig joenasConfig;

    private String prefix = "module/ddos";
	
	@RequiresPermissions("module:ddos:view")
	@GetMapping()
	public String ddos()
	{
	    return prefix + "/ddos";
	}
	
	/**
	 * 查询关键字列表
	 */
	@RequiresPermissions("module:ddos:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list()
	{
		startPage();
        List<DDOS> list = getDdosInfos();
		return getDataTable(list);
	}

	private List<DDOS> getDdosInfos() {
		String ddosLogPath = joenasConfig.getDdosPath();

		ArrayList<DDOS> lists = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ddosLogPath))));
			//InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(ddosLogPath)));
			String line = null;

			while ((line = reader.readLine()) != null) {
				lists.add(new DDOS(line));
			}
		} catch (FileNotFoundException e) {
			return lists;
		} catch (IOException e) {
			return lists;
		}

		return lists;
	}
}
