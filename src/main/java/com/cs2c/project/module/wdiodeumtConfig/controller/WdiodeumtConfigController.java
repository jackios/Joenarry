package com.cs2c.project.module.wdiodeumtConfig.controller;

import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.framework.config.JoenasConfig;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.project.module.wdiodeumtConfig.domain.EditViewWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.ViewWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.WdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.service.IWdiodeumtConfigService;
import com.cs2c.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 端口转发配置文件 信息操作处理
 *
 * @author Joenas
 * @date 2020-03-05
 */
@Controller
@RequestMapping("/module/wdiodeumtConfig")
public class WdiodeumtConfigController extends BaseController {
    private String prefix = "module/wdiodeumtConfig";

    @Autowired
    private IWdiodeumtConfigService wdiodeumtConfigService;

    @Autowired
    private JoenasConfig joenasConfig;


    @RequiresPermissions("module:wdiodeumtConfig:view")
    @GetMapping()
    public String wdiodeumtConfig() {
        return prefix + "/wdiodeumtConfig";
    }

    /**
     * 查询端口转发配置文件列表
     */
    //RequiresPermissions("module:wdiodeumtConfig:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ViewWdiodeumtConfig viewWdiodeumtConfig) {
        startPage();
        List<ViewWdiodeumtConfig> list = wdiodeumtConfigService.selectWdiodeumtConfigList(viewWdiodeumtConfig);
        return getDataTable(list);
    }

    /**
     * 新增端口转发配置文件
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存端口转发配置文件
     */
    //RequiresPermissions("module:wdiodeumtConfig:add")
    @Log(title = "端口转发配置文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WdiodeumtConfig wdiodeumtConfig) {
        return toAjax(wdiodeumtConfigService.insertWdiodeumtConfig(wdiodeumtConfig));
    }

    /**
     * 修改端口转发配置文件
     */
    @GetMapping("/edit/{w_key}")
    public String edit(@PathVariable("w_key") String w_key, ModelMap mmap) {
        EditViewWdiodeumtConfig editViewWdiodeumtConfig = wdiodeumtConfigService.selectWdiodeumtConfigByKey(w_key);
        mmap.put("wdiodeumtConfig", editViewWdiodeumtConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存端口转发配置文件
     */
    //RequiresPermissions("module:wdiodeumtConfig:edit")
    @Log(title = "端口转发配置文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit/{reversion}")
    @ResponseBody
    public AjaxResult editSave(EditViewWdiodeumtConfig editViewWdiodeumtConfig, @PathVariable(value = "reversion") String reversion) {
        return toAjax(wdiodeumtConfigService.updateWdiodeumtConfig(editViewWdiodeumtConfig, reversion));
    }

    /**
     * 删除端口转发配置文件
     */
    //RequiresPermissions("module:wdiodeumtConfig:remove")
    @Log(title = "端口转发配置文件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(wdiodeumtConfigService.deleteWdiodeumtConfigByIds(ids));
    }

    /**
     * 检查版本描述信息是否填写
     */
    @PostMapping("/checkComment")
    @ResponseBody
    public String checkComment(@RequestParam(value = "comment") String comment, @RequestParam(value = "reversion") String reversion) {
        //LogUtils.ERROR_LOG.error("================ " + comment + "   " + reversion);
        return wdiodeumtConfigService.checkComment(comment, reversion);
    }

    @RequiresPermissions("module:wdiodeumtConfig:view")
    @GetMapping("/version")
    public String wdiodeConfigReversion() {
        return prefix + "/wdiodeumtConfigReversion";
    }

    //RequiresPermissions("module:wdiodeumtConfig:list")
    @PostMapping("/versionList")
    @ResponseBody
    public TableDataInfo listReversion() {
        startPage();
        List<WdiodeumtConfig> list = wdiodeumtConfigService.selectMulWdiodeumtConfigList(joenasConfig.getConfigVersions());
        //List<ViewWdiodeConfig> list = wdiodeConfigService.selectWdiodeConfigList();
        return getDataTable(list);
    }

    /**
     * 配置文件回退
     */
    //RequiresPermissions("module:wdiodeumtConfig:edit")
    @Log(title = "wdiodeumt配置-配置回退", businessType = BusinessType.UPDATE)
    @PostMapping("/reversion")
    @ResponseBody
    public AjaxResult reversion(@RequestParam(value = "id") Integer id) {
        //LogUtils.ERROR_LOG.error(editViewWdiodeConfig.toString() + "      " + reversion);
        return wdiodeumtConfigService.reversion(id);

        //return toAjax(1);
    }

    /**
     * 配置文件恢复默认值
     */
    //RequiresPermissions("module:wdiodeumtConfig:edit")
    @Log(title = "wdiodeumt配置-配置还原", businessType = BusinessType.UPDATE)
    @PostMapping("/reinit")
    @ResponseBody
    public AjaxResult reinit() {
        return toAjax(wdiodeumtConfigService.reinit());
    }

    /**
     * 导出配置文件
     *
     * @param id
     * @return
     */
    @Log(title = "wdiode配置-导出配置", businessType = BusinessType.EXPORT)
    //RequiresPermissions("module:wdiodeumtConfig:export")
    @PostMapping("/export/{id}")
    @ResponseBody
    public AjaxResult export(@PathVariable(value = "id") Integer id) {
        //return util.exportExcel(list, "operLog");
        return wdiodeumtConfigService.export(id);
    }

//	@RequiresPermissions("module:wdiodeConfig:view")
//	@GetMapping("/viewVersionInfo")
//	public String viewVersionInfo()
//	{
//		return prefix + "/wdiodeConfigVersionInfo";
//	}


    /**
     * 查看指定版本的配置文件
     *
     * @param id
     * @param mmap
     * @return
     */
    @RequiresPermissions("module:wdiodeumtConfig:view")
    @GetMapping("/viewVersionInfo/{id}")
    public String viewVersionInfo(@PathVariable(value = "id") Integer id, ModelMap mmap) {
        mmap.put("versionID", id);

        return prefix + "/wdiodeumtConfigVersionInfo";
    }


    /**
     * 查询wdiode配置列表
     */
    //RequiresPermissions("module:wdiodeumtConfig:list")
    @PostMapping("/viewVersion/{id}")
    @ResponseBody
    public TableDataInfo viewVersionInfoList(ViewWdiodeumtConfig viewWdiodeumtConfig, @PathVariable(value = "id") Integer id) {
        startPage();
        List<ViewWdiodeumtConfig> list = wdiodeumtConfigService.selectWdiodeumtConfigList(viewWdiodeumtConfig, id);
        return getDataTable(list);
    }


    /**
     * 上传文件
     *
     * @param flag
     * @return
     */
    //RequiresPermissions("module:wdiodeConfig:edit")
    @GetMapping("/general/{flag}")
    public String general(@PathVariable(value = "flag") String flag, ModelMap mmap) {
        mmap.put("flag", flag);

        return prefix + "/upload";
    }

    /**
     * 上传文件
     *
     * @return
     */
    //RequiresPermissions("module:wdiodeConfig:edit")
    @GetMapping("/general")
    public String general() {
        //mmap.put("versionID", id);

        return prefix + "/upload";
    }


    @Log(title = "wdiodeumt配置-上传配置", businessType = BusinessType.UPDATE)
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") MultipartFile file) {

        return wdiodeumtConfigService.upload(file);

    }

    @PostMapping("/fileSync")
    @ResponseBody
    public AjaxResult fileSync() {
        return wdiodeumtConfigService.fileSync();
    }


    @PostMapping("/uploadToDB")
    @ResponseBody
    public AjaxResult uploadToDB() {
        User user = ShiroUtils.getUser();
//        if (User.isAdmin(user.getUserId())) {
            return wdiodeumtConfigService.uploadToDB();
//        } else {
//            return AjaxResult.error("您不是管理员，请联系管理员进行该操作");
//        }
    }


    @PostMapping("/DF")
    @ResponseBody
    public AjaxResult downloadF() {
        return wdiodeumtConfigService.downloadF();
    }

}
