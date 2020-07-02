package com.cs2c.project.system.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.framework.config.JoenasConfig;
import com.cs2c.project.module.diskinfo.domain.Diskinfo;
import com.cs2c.project.module.diskinfo.service.IDiskinfoService;
import com.cs2c.project.module.netflow.domain.Netflow;
import com.cs2c.project.module.netflow.service.INetflowService;
import com.cs2c.project.module.wdiodeConfig.service.IWdiodeConfigService;
import com.cs2c.project.system.user.domain.Cpuinfo;
import com.cs2c.project.system.user.domain.Meminfo;
import com.cs2c.project.system.user.service.ICpuinfoService;
import com.cs2c.project.system.user.service.IMeminfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.cs2c.framework.config.Cs2cConfig;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.project.system.menu.domain.Menu;
import com.cs2c.project.system.menu.service.IMenuService;
import com.cs2c.project.system.user.domain.User;

/**
 * 首页 业务处理
 * 
 * @author cs2c
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private IDiskinfoService diskinfoService;


    @Autowired
    private JoenasConfig joenasConfig;


    @Autowired
    private IWdiodeConfigService wdiodeConfigService;

    @Autowired
    private ICpuinfoService cpuinfoService;

    @Autowired
    private IMeminfoService meminfoService;

    @Autowired
    private INetflowService netflowService;

    @Autowired
    private Cs2cConfig ruoYiConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());

        // 0. 注入欢迎语信息
        SimpleDateFormat time_t=new SimpleDateFormat("yyyy年MM月dd日");
        mmap.put("welcomeMsg", "单向隔离一体机－入口端(TCN), 今天是 " + time_t.format(new Date())+"。");

        int logoutTimeout;
        try {
            logoutTimeout = Integer.parseInt(joenasConfig.getLogoutTimeout());
        } catch (NumberFormatException e) {
            LogUtils.ERROR_LOG.error("应用程序配置文件错误，请修改logoutTimeout为正确的值，可接受的值为 可整数化 的值");
            logoutTimeout = 60;
        }

        mmap.put("logoutTimeout", logoutTimeout);

        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        // 1. 注入版本信息
        mmap.put("version", ruoYiConfig.getVersion());

        // 2. 注入cpu负载、iowait和使用率信息
        SimpleDateFormat time=new SimpleDateFormat("HH:mm:ss");
        ArrayList<String> steplabels = new ArrayList<>();
        ArrayList<ArrayList<Float>> avgdata = new ArrayList<>();
        ArrayList<Float> cpudata =  new ArrayList<>();
        ArrayList<Float> iowaitdata =  new ArrayList<>();

        ArrayList<Float> avgdata_1 =  new ArrayList<>();
        ArrayList<Float> avgdata_5 =  new ArrayList<>();
        ArrayList<Float> avgdata_15 =  new ArrayList<>();
        List<Cpuinfo> cpuinfos = cpuinfoService.selectCpuinfoList(new Cpuinfo());
        Collections.reverse(cpuinfos);
        for (Cpuinfo cpuinfo : cpuinfos) {
            steplabels.add(time.format(cpuinfo.getDataTime()));
            avgdata_1.add(cpuinfo.getOma());
            avgdata_5.add(cpuinfo.getFma());
            avgdata_15.add(cpuinfo.getFtma());
            cpudata.add(100.0f - cpuinfo.getIdle());
            iowaitdata.add(cpuinfo.getIowait());
        }
        avgdata.add(avgdata_1);
        avgdata.add(avgdata_5);
        avgdata.add(avgdata_15);

        // 3. 注入网卡流量监控信息
        ArrayList<String> netflowsteplabels = new ArrayList<>();
        List<Netflow> netflows = netflowService.selectNetflowListLastN(new Netflow());
        ArrayList<Float> netflowData =  new ArrayList<>();

        Collections.reverse(netflows);

        int size = netflows.size();
        int index = 0;
        ArrayList<Netflow> nfs = new ArrayList<>();
        if (size <= 30) {
            for (Netflow netflow : netflows) {
                netflowsteplabels.add(time.format(netflow.getCreateDate()));
                netflowData.add(netflow.getValue() / 1024.0f); // MB
            }
        } else {
            int count = size / 30;
            while (index < count) {
                Netflow nf = netflows.get(index * 30 + 29);
                if (null == nf) {
                    break;
                }

                //Netflow nf = netflows.get(index * 30 + 29);
                if (null == nf) {
                    break;
                }

                Date date = nf.getCreateDate();

                long total = 0;
                int tmpIndex = 0;
                for (Netflow netflow : netflows.subList(index * 30, index * 30 + 30)) {
                    total += netflow.getValue();
                    tmpIndex++;
                }

                Netflow netflow = new Netflow();
                netflow.setCreateDate(date);
                netflow.setValue(total / tmpIndex);
                nfs.add(netflow);

                index++;
            }

            for (Netflow netflow : nfs) {
                netflowsteplabels.add(time.format(netflow.getCreateDate()));
                netflowData.add(netflow.getValue() / 1024.0f); // MB
            }
        }



        mmap.put("avgsteplabels", steplabels);
        mmap.put("avgdata", avgdata);
        mmap.put("cpusteplabels", steplabels);
        mmap.put("cpudata", cpudata);
        mmap.put("iowaitsteplabels", steplabels);
        mmap.put("iowaitdata", iowaitdata);
        mmap.put("netflowData", netflowData);
        mmap.put("netflowsteplabels", netflowsteplabels);

        // 3. 注入内存信息
        List<Meminfo> meminfos = meminfoService.selectMeminfoList(new Meminfo());
        ArrayList<Float> memdata =  new ArrayList<>();
        ArrayList<String> memsteplabels = new ArrayList<>();
        for (Meminfo meminfo : meminfos) {
            memdata.add(meminfo.getUsed() * 1.0f / meminfo.getTotal());
            memsteplabels.add(time.format(meminfo.getDataTime()));
        }

        mmap.put("memsteplabels", memsteplabels);
        mmap.put("memdata", memdata);


        // 4. 注入磁盘使用率信息 - 20190123
        //String c_file_dir = wdiodeConfigService.selectWdiodeConfigByKey("c_file_dir").getW_value();
        String diskdev = wdiodeConfigService.selectWdiodeConfigByKey("diskdev").getW_value();
        String warn_value = wdiodeConfigService.selectWdiodeConfigByKey("diskwar").getW_value();

        Boolean warnFlag = false;
        String warnMsg = "";
        if (diskdev == null || diskdev.trim().length() == 0) {
            warnFlag = true;
            warnMsg = "获取磁盘使用率异常";
        }

        float wv = 0.0f;
        try {
            wv = Float.parseFloat(warn_value);

            List<Diskinfo> list = diskinfoService.selectDiskinfoList(new Diskinfo());
            //boolean diskUsage = false;
            //c_file_dir = c_file_dir.trim() + "/";

            //while (true) {
            //    if (diskUsage)
            //        break;

            //    c_file_dir = c_file_dir.substring(0, c_file_dir.lastIndexOf('/'));

            //    if (c_file_dir.lastIndexOf('/') == 0)
            //        diskUsage = true;

                for (Diskinfo diskinfo : list) {
                    if (diskinfo.getDiskName().trim().equalsIgnoreCase(diskdev)) {
                        Float vv = Float.parseFloat(diskinfo.getDiskUsedPercent().replace("%", ""));
                        if (wv > vv) {
                            warnFlag = true;
                            warnMsg = "磁盘空间使用已达到警告值(" + vv + ")";
                        }
                    }
                }
            //}

        } catch (Exception e) {
            warnFlag = true;
            warnMsg = "磁盘预警配置错误";
        }

        mmap.put("warnFlag", warnFlag);
        mmap.put("warnMsg", warnMsg);

        return "main";
    }

}
