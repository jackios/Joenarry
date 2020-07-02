package com.cs2c.project.module.wdiodeumtService.controller;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.aspectj.lang.annotation.Log;
import com.cs2c.framework.aspectj.lang.enums.BusinessType;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.framework.web.page.TableDataInfo;
import com.cs2c.project.module.wdiodeumtService.domain.HostAttr;
import com.cs2c.project.module.wdiodeumtService.domain.WdiodeumtService;
import com.cs2c.project.module.wdiodeumtService.service.IWdiodeumtServiceMService;
import com.cs2c.project.module.wdiodeumtService.service.IWdiodeumtServiceService;
import com.cs2c.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * 端口注册服务 信息操作处理
 *
 * @author Joenas
 * @date 2020-03-09
 */
@Controller
@RequestMapping("/module/wdiodeumtService")
public class WdiodeumtServiceController extends BaseController {
    private String prefix = "module/wdiodeumtService";

    @Autowired
    private IWdiodeumtServiceService wdiodeumtServiceService;

    @Autowired
    private IWdiodeumtServiceMService wdiodeumtServiceMService;

    @RequiresPermissions("module:wdiodeumtService:view")
    @GetMapping()
    public String wdiodeumtService() {
        return prefix + "/wdiodeumtService";
    }

    /**
     * 查询端口注册服务列表
     */
    //RequiresPermissions("module:wdiodeumtService:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WdiodeumtService wdiodeumtService) {
        startPage();
        List<WdiodeumtService> list = wdiodeumtServiceService.selectWdiodeumtServiceList(wdiodeumtService);

        for (WdiodeumtService service : list) {


//            if (!"admin".equals(ShiroUtils.getUser().getLoginName())) {
//                continue;
//            }
            if ("no".equals(service.getIsEnable())) {
                service.setCurrentStatus("已停用");
            }

            service.setCurrentStatus(wdiodeumtServiceMService.getServiceCurrentStatus(
                    HostAttr.WDIODEUMT_SERVER, service.getServiceName()));
        }
        return getDataTable(list);
    }

    /**
     * 新增端口注册服务
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存端口注册服务
     */
    ////RequiresPermissions("module:wdiodeumtService:add")
    @Log(title = "端口注册服务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WdiodeumtService wdiodeumtService) {
        wdiodeumtService.setDataTime(new Date(System.currentTimeMillis()));
        System.out.println(wdiodeumtService.toString());
        return toAjax(wdiodeumtServiceService.insertWdiodeumtService(wdiodeumtService));
    }

    /**
     * 修改端口注册服务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        WdiodeumtService wdiodeumtService = wdiodeumtServiceService.selectWdiodeumtServiceById(id);
        mmap.put("wdiodeumtService", wdiodeumtService);
        return prefix + "/edit";
    }

    /**
     * 修改保存端口注册服务
     */
    ////RequiresPermissions("module:wdiodeumtService:edit")
    @Log(title = "端口注册服务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WdiodeumtService wdiodeumtService) {
        return toAjax(wdiodeumtServiceService.updateWdiodeumtService(wdiodeumtService));
    }

    /**
     * 删除端口注册服务
     */
    //RequiresPermissions("module:wdiodeumtService:remove")
    @Log(title = "端口注册服务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(wdiodeumtServiceService.deleteWdiodeumtServiceById(Integer.parseInt(ids)));
    }

    /**
     * 启动服务管理
     */
    @RequiresPermissions("module:serviceM:reboot")
    @Log(title = "启停管理-启动", businessType = BusinessType.UPDATE)
    @PostMapping("/start")
    @ResponseBody
    public AjaxResult start(@RequestParam(value = "id") String ids) {
        return toAjax(wdiodeumtServiceMService.start(ids));
    }

    /**
     * 启动全部服务管理
     */
    @RequiresPermissions("module:serviceM:reboot")
    @Log(title = "启停管理-启动", businessType = BusinessType.UPDATE)
    @PostMapping("/startAll")
    @ResponseBody
    public AjaxResult startAll() {
        return toAjax(wdiodeumtServiceMService.startAll());
    }

    /**
     * 停止全部服务管理
     */
    @RequiresPermissions("module:serviceM:reboot")
    @Log(title = "启停管理-启动", businessType = BusinessType.UPDATE)
    @PostMapping("/stopAll")
    @ResponseBody
    public AjaxResult stopAll() {
        return toAjax(wdiodeumtServiceMService.stopAll());
    }

    /**
     * 停止服务管理
     */
    @RequiresPermissions("module:serviceM:reboot")
    @Log(title = "启停管理-停止", businessType = BusinessType.UPDATE)
    @PostMapping("/stop")
    @ResponseBody
    public AjaxResult stop(@RequestParam(value = "id") String ids) {
        return toAjax(wdiodeumtServiceMService.stop(ids));
    }

    /**
     * 禁用服务管理
     */
    @RequiresPermissions("module:serviceM:control")
    @Log(title = "启停管理-禁用", businessType = BusinessType.UPDATE)
    @PostMapping("/disable")
    @ResponseBody
    public AjaxResult disable(@RequestParam(value = "id") String ids) {
        return toAjax(wdiodeumtServiceMService.disable(ids));
    }

    /**
     * 启用服务管理
     */
    @RequiresPermissions("module:serviceM:control")
    @Log(title = "启停管理-启用", businessType = BusinessType.UPDATE)
    @PostMapping("/enable")
    @ResponseBody
    public AjaxResult enable(@RequestParam(value = "id") String ids) {
        return toAjax(wdiodeumtServiceMService.enable(ids));
    }

    /**
     *
     */
    @PostMapping("/checkServiceName")
    @ResponseBody
    public AjaxResult checkServiceName(@RequestParam(name = "serviceName") String serviceName) {        //查询成功返回0，查询失败返回1
        //若查得到，说明已经重复
        return toAjax(wdiodeumtServiceService.selectWdiodeumtServiceByServiceName(serviceName));
    }

    /**
     *
     */
    @PostMapping("/checkWid")
    @ResponseBody
    public AjaxResult checkWid(@RequestParam(name = "wid") String wid) {        //查询成功返回0，查询失败返回1
        //若查得到，说明已经重复
        return toAjax(wdiodeumtServiceService.selectWdiodeumtServiceByWid(wid));
    }

    /**
     *
     */
    @PostMapping("/checkPort")
    @ResponseBody
    public AjaxResult checkPort(@RequestParam(name = "port") String port) {        //查询成功返回0，查询失败返回1
        //若查得到，说明已经重复
        return toAjax(wdiodeumtServiceService.checkPort(port));

    }

    /**
     *
     */
    @PostMapping("/checkRemote")
    @ResponseBody
    public AjaxResult checkRemote(@RequestParam(name = "remotePort") String remotePort,
                                  @RequestParam(name = "remoteIp") String remoteIp) {        //查询成功返回0，查询失败返回1

        return toAjax(wdiodeumtServiceService.checkRemote(remoteIp, remotePort));

    }

    /**
     * 这个是rsw1==>tcn2进行文件同步。
     * 使用ssh加scp的方式进行文件传输，传输到指定的文件夹
     *
     * @return
     */
    @RequestMapping("/fileSync")
    @ResponseBody
    public AjaxResult fileSync() {
        return wdiodeumtServiceService.fileSync();
    }

    @RequestMapping("/uploadToDB")
    @ResponseBody
    public AjaxResult uploadToDB() {
        User user = ShiroUtils.getUser();
//        if (User.isAdmin(user.getUserId())) {
            return wdiodeumtServiceService.uploadToDB();
//        } else {
//            return AjaxResult.error("您不是管理员，请联系管理员进行该操作");
//        }
    }

/*    @RequestMapping("/searchFile")
    @ResponseBody
    public AjaxResult searchFile() {
        return toAjax(wdiodeumtServiceService.searchFile());
    }
    @RequestMapping("/updateFile")
    @ResponseBody
    public AjaxResult updateFile() {
        return toAjax(wdiodeumtServiceService.updateFile());
    }
    @RequestMapping("/deleteFile")
    @ResponseBody
    public AjaxResult deleteFile() {
        return toAjax(wdiodeumtServiceService.deleteFile());
    }*/

}
