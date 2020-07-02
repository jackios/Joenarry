package com.cs2c.project.module.wdiodeConfig.controller;

import java.util.List;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.file.FileUploadUtils;
import com.cs2c.framework.config.JoenasConfig;
import com.cs2c.framework.config.Cs2cConfig;
import com.cs2c.project.module.wdiodeConfig.domain.EditViewWdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.domain.ViewWdiodeConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.project.module.wdiodeConfig.domain.WdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.service.IWdiodeConfigService;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.View;

/**
 * wdiode配置 信息操作处理
 * 
 * @author Joenas
 * @date 2018-10-14
 */
@Controller
@RequestMapping("/module/wdiodeConfig")
public class WdiodeConfigController extends BaseController
{
    private String prefix = "module/wdiodeConfig";
	
	@Autowired
	private IWdiodeConfigService wdiodeConfigService;

	@Autowired
	private JoenasConfig joenasConfig;
	
	@RequiresPermissions("module:wdiodeConfig:view")
	@GetMapping()
	public String wdiodeConfig()
	{
	    return prefix + "/wdiodeConfig";
	}


	
	/**
	 * 查询wdiode配置列表
	 */
	@RequiresPermissions("module:wdiodeConfig:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ViewWdiodeConfig viewWdiodeConfig)
	{
		startPage();
        List<ViewWdiodeConfig> list = wdiodeConfigService.selectWdiodeConfigList(viewWdiodeConfig);
		return getDataTable(list);
	}

	/**
	 * 修改wdiode配置
	 */
	@GetMapping("/edit/{w_key}")
	public String edit(@PathVariable("w_key") String w_key, ModelMap mmap)
	{
		EditViewWdiodeConfig editViewWdiodeConfig = wdiodeConfigService.selectWdiodeConfigByKey(w_key);
		mmap.put("wdiodeConfig", editViewWdiodeConfig);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存wdiode配置
	 */
	@RequiresPermissions("module:wdiodeConfig:edit")
	@Log(title = "wdiode配置-修改配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit/{reversion}")
	@ResponseBody
	public AjaxResult editSave(EditViewWdiodeConfig editViewWdiodeConfig, @PathVariable(value = "reversion") String reversion)
	{
		//LogUtils.ERROR_LOG.error(editViewWdiodeConfig.toString() + "      " + reversion);
		return toAjax(wdiodeConfigService.updateWdiodeConfig(editViewWdiodeConfig, reversion));

		//return toAjax(1);
	}

	/**
	 * 检查版本描述信息是否填写
	 */
	@PostMapping("/checkComment")
	@ResponseBody
	public String checkComment(@RequestParam(value = "comment") String comment,  @RequestParam(value = "reversion") String reversion)
	{
		//LogUtils.ERROR_LOG.error("================ " + comment + "   " + reversion);
		return wdiodeConfigService.checkComment(comment, reversion);
	}


	@RequiresPermissions("module:wdiodeConfig:view")
	@GetMapping("/version")
	public String wdiodeConfigReversion()
	{
		return prefix + "/wdiodeConfigReversion";
	}

	/**
	 * 查询wdiode配置列表
	 */
	@RequiresPermissions("module:wdiodeConfig:list")
	@PostMapping("/versionList")
	@ResponseBody
	public TableDataInfo listReversion()
	{
		startPage();
		List<WdiodeConfig> list = wdiodeConfigService.selectMulWdiodeConfigList(joenasConfig.getConfigVersions());
		//List<ViewWdiodeConfig> list = wdiodeConfigService.selectWdiodeConfigList();
		return getDataTable(list);
	}



	/**
	 * 删除wdiode配置
	 */
	@RequiresPermissions("module:wdiodeConfig:remove")
	@Log(title = "wdiode配置-删除配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(wdiodeConfigService.deleteWdiodeConfigByIds(ids));
	}

	/**
	 * 配置文件回退
	 */
	@RequiresPermissions("module:wdiodeConfig:edit")
	@Log(title = "wdiode配置-配置回退", businessType = BusinessType.UPDATE)
	@PostMapping("/reversion")
	@ResponseBody
	public AjaxResult reversion(@RequestParam(value = "id") Integer id)
	{
		//LogUtils.ERROR_LOG.error(editViewWdiodeConfig.toString() + "      " + reversion);
		return toAjax(wdiodeConfigService.reversion(id));

		//return toAjax(1);
	}

	/**
	 * 配置文件恢复默认值
	 */
	@RequiresPermissions("module:wdiodeConfig:edit")
	@Log(title = "wdiode配置-配置还原", businessType = BusinessType.UPDATE)
	@PostMapping("/reinit")
	@ResponseBody
	public AjaxResult reinit()
	{
		return toAjax(wdiodeConfigService.reinit());
	}

	/**
	 * 导出配置文件
	 * @param id
	 * @return
	 */
	@Log(title = "wdiode配置-导出配置", businessType = BusinessType.EXPORT)
	@RequiresPermissions("module:wdiodeConfig:export")
	@PostMapping("/export/{id}")
	@ResponseBody
	public AjaxResult export(@PathVariable(value = "id") Integer id)
	{
		//return util.exportExcel(list, "operLog");
		return wdiodeConfigService.export(id);
	}

//	@RequiresPermissions("module:wdiodeConfig:view")
//	@GetMapping("/viewVersionInfo")
//	public String viewVersionInfo()
//	{
//		return prefix + "/wdiodeConfigVersionInfo";
//	}


	/**
	 * 查看指定版本的配置文件
	 * @param id
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("module:wdiodeConfig:view")
	@GetMapping("/viewVersionInfo/{id}")
	public String viewVersionInfo(@PathVariable(value = "id") Integer id, ModelMap mmap)
	{
		mmap.put("versionID", id);

		return prefix + "/wdiodeConfigVersionInfo";
	}


	/**
	 * 查询wdiode配置列表
	 */
	@RequiresPermissions("module:wdiodeConfig:list")
	@PostMapping("/viewVersion/{id}")
	@ResponseBody
	public TableDataInfo viewVersionInfoList(ViewWdiodeConfig viewWdiodeConfig, @PathVariable(value = "id") Integer id)
	{
		startPage();
		List<ViewWdiodeConfig> list = wdiodeConfigService.selectWdiodeConfigList(viewWdiodeConfig, id);
		return getDataTable(list);
	}


	/**
	 * 上传文件
	 * @param flag
	 * @return
	 */
	@RequiresPermissions("module:wdiodeConfig:edit")
	@GetMapping("/general/{flag}")
	public String general(@PathVariable(value = "flag") String flag, ModelMap mmap)
	{
		mmap.put("flag", flag);

		return prefix + "/upload";
	}

	/**
	 * 上传文件
	 * @return
	 */
	@RequiresPermissions("module:wdiodeConfig:edit")
	@GetMapping("/general")
	public String general()
	{
		//mmap.put("versionID", id);

		return prefix + "/upload";
	}


	@Log(title = "wdiode配置-上传配置", businessType = BusinessType.UPDATE)
	@PostMapping("/upload")
	@ResponseBody
	public AjaxResult upload(@RequestParam("file") MultipartFile file)
	{
		return wdiodeConfigService.upload(file);
	}
	
	@PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF()
    {       
        return wdiodeConfigService.downloadF();
    }
}
