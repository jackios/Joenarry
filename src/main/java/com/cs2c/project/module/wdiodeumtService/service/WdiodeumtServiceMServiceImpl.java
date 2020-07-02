package com.cs2c.project.module.wdiodeumtService.service;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.project.module.wdiodeumtService.domain.HostAttr;
import com.cs2c.project.module.wdiodeumtService.domain.WdiodeumtService;
import com.cs2c.project.module.wdiodeumtService.mapper.WdiodeumtServiceMapper;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 服务管理 服务层实现
 *
 * @author Joenas
 * @date 2018-10-23
 */
@Service
public class WdiodeumtServiceMServiceImpl implements IWdiodeumtServiceMService {
    @Autowired
    private WdiodeumtServiceMapper wdiodeumtServiceMapper;

    @Value("${joenas.wdiodeumt_service_config_path}")
    private String wdiodeumtServiceConfigPath;

    /**
     * 查询服务管理信息
     *
     * @param id 服务管理ID
     * @return 服务管理信息
     */
 /*   @Override
	public ServiceM selectServiceMById(Integer id)
	{
	    return serviceMMapper.selectServiceMById(id);
	}
	*/
    /**
     * 查询服务管理列表
     *
     * @param serviceM 服务管理信息
     * @return 服务管理集合
     */
/*	@Override
	public List<ServiceM> selectServiceMList(ServiceM serviceM)
	{
	    return serviceMMapper.selectServiceMList(serviceM);
	}
	*/
    /**
     * 新增服务管理
     *
     * @param serviceM 服务管理信息
     * @return 结果
     */
    //@Override
    //public int insertServiceM(ServiceM serviceM)
//	{
//	    return serviceMMapper.insertServiceM(serviceM);
//	}

    /**
     * 修改服务管理
     *
     * @param serviceM 服务管理信息
     * @return 结果
     */
/*
	@Override
	public int updateServiceM(ServiceM serviceM)
	{
	    return serviceMMapper.updateServiceM(serviceM);
	}
*/

    /**
     * 删除服务管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    //@Override
    //public int deleteServiceMByIds(String ids)
    //{
    //	return serviceMMapper.deleteServiceMByIds(Convert.toStrArray(ids));
    //}
    private WdiodeumtService getCurrentInstance(String ids) {
        Integer[] firstIds = Convert.toIntArray(ids);
        if (firstIds.length == 0) {
            return null;
        }

        return wdiodeumtServiceMapper.selectWdiodeumtServiceById(firstIds[0]);
    }

    /**
     * 从这里的ids都是每个注册服务对应的数据库id号
     *
     * @param ids 需要删除的数据ID
     * @return 返回命令运行的结果
     */
    @Override
    public int start(String ids) {
        return runTask(ids, "on");
    }

    @Override
    public int stop(String ids) {
        return runTask(ids, "off");
    }

    @Override
    public int reboot(String ids) {
        if (1 == runTask(ids, "off")) {
            return runTask(ids, "on");
        } else {
            LogUtils.ERROR_LOG.error("重启服务的时候出现错误");
            return runTask(ids, "on");
        }
        //return runTask(ids, "reboot");
    }

    @Override
    public int reload(String ids) {
        return runTask(ids, "reload");
    }

    @Override
    public int enable(String ids) {
        WdiodeumtService wdiodeumtService = getCurrentInstance(ids);
        if (null == wdiodeumtService) {
            return UserConstants.CHANGE_0_RECORD;
        }
        wdiodeumtService.setIsEnable("yes");
        int row = wdiodeumtServiceMapper.updateWdiodeumtService(wdiodeumtService);
        int status = persistConfig();
        if (status == 0) {
            return UserConstants.CHANGE_0_RECORD;
        }
        //wdiodeumtService.setIsValid("yes");
        return row;
    }

    public int persistConfig() {
        List<WdiodeumtService> wdiodeumtServices = wdiodeumtServiceMapper.selectWdiodeumtServiceList(new WdiodeumtService());
        StringBuilder stringBuilder = new StringBuilder();
        wdiodeumtServices.forEach(wdiodeumtService -> {
            stringBuilder.append("[").append(wdiodeumtService.getServiceName()).append("]").append("\n")
                    .append("#description:").append(wdiodeumtService.getDescription()).append("\n")
                    .append("wid=").append(wdiodeumtService.getWid()).append("\n")
                    .append("wiport=").append(wdiodeumtService.getWiport()).append("\n")
                    .append("woport=").append(wdiodeumtService.getWoport()).append("\n")
                    .append("protocol=").append(wdiodeumtService.getProtocol()).append("\n")
                    .append("allow_ip=").append(wdiodeumtService.getAllowIp()).append("\n")
                    .append("in_parmsa=").append(wdiodeumtService.getInParmsa()).append("\n")
                    .append("in_parmsb=").append(wdiodeumtService.getInParmsb()).append("\n")
                    .append("out_parms=").append(wdiodeumtService.getOutParms()).append("\n")
                    .append("log_enable=").append(wdiodeumtService.getLogEnable()).append("\n")
                    .append("logfile=").append(wdiodeumtService.getLogfile()).append("\n")
                    .append("is_enable=").append(wdiodeumtService.getIsEnable()).append("\n");
        });
        File file = new File(wdiodeumtServiceConfigPath);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int disable(String ids) {
        WdiodeumtService wdiodeumtService = getCurrentInstance(ids);
        if (null == wdiodeumtService) {
            return UserConstants.CHANGE_0_RECORD;
        }
        wdiodeumtService.setIsEnable("no");
        int row = wdiodeumtServiceMapper.updateWdiodeumtService(wdiodeumtService);
        int status = persistConfig();
        if (status == 0) {
            return UserConstants.CHANGE_0_RECORD;
        }
        return row;
    }

    private int runTask(String ids, String oper) {
        WdiodeumtService wdiodeumtService = getCurrentInstance(ids);
        if (null == wdiodeumtService) {
            return UserConstants.CHANGE_0_RECORD;
        }
		/*	wdiodeumt service1(注册文件中的[]中的名称) tcn1(根据主机名) on
			wdiodeumt core1 tcn1(根据主机名) on
			wdiodeumt core2 tcn1(根据主机名) on*/
        String hostName = null;
        try {
            //获取主机名
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
//            e.printStackTrace();
            LogUtils.ERROR_LOG.error("获取本机名称出错");
            return UserConstants.CHANGE_0_RECORD;
        }
        String serviceKey = HostAttr.CONFIG_NAME + UserConstants.SPACE_ONE + wdiodeumtService.getServiceName() + UserConstants.SPACE_ONE + hostName;
        String status = operation(serviceKey, oper);
        
        LogUtils.ERROR_LOG.error("执行的命令==>" + serviceKey + UserConstants.SPACE_ONE + oper);
        if ("0".equals(status)) {
            return UserConstants.CHANGE_1_RECORD;
        }
        return UserConstants.CHANGE_0_RECORD;
    }

    @Override
    public String getServiceCurrentStatus(String cmd, String oper) {
        boolean jobBackground = false;
        // 默认10s超时
        long jobTimeout = 10000;
        cmd += UserConstants.SPACE_ONE + "status";
        StringBuilder command = new StringBuilder(cmd);
        command.append(UserConstants.SPACE_ONE).append(oper);
        CommandLine commandLine = CommandLine.parse(command.toString());

        try {
            Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
            String stdout = output.get("stdout");
            String stderr = output.get("stderr");
            if (StringUtils.isEmpty(stderr) && !isStdoutContainsCustomError(stdout)) {
                if (stdout.contains("not running")) {
                    return "未运行";
                } else if (stdout.contains("running")) {
                    return "运行中";
                } else {
                    return "状态未知";
                }
            } else {
                LogUtils.ERROR_LOG.error("获取服务状态操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
            }
        } catch (IOException e) {
//            LogUtils.ERROR_LOG.error("获取服务状态操作失败， 命令为 " + command.toString(), e);
            LogUtils.ERROR_LOG.error("获取服务状态操作失败， 命令为 " + command.toString());
        }

        return "状态未知";
    }

    @Override
    public int startAll() {
        //命令格式wdiodeumt_server.sh start all
        //wdiodeumt_server.sh stop all
        //wdiodeumt_server.sh status all
//        String cmd = HostAttr.WDIODEUMT_SERVER + UserConstants.SPACE_ONE + "start" + UserConstants.SPACE_ONE + "all";
        String operation = operation(HostAttr.WDIODEUMT_SERVER, "start" + UserConstants.SPACE_ONE + "all");
        if ("0".equals(operation)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int stopAll() {
        String operation = operation(HostAttr.WDIODEUMT_SERVER, "stop" + UserConstants.SPACE_ONE + "all");
        if ("0".equals(operation)) {
            return 1;
        } else {
            return 0;
        }
    }

    private String operation(String cmd, String oper) {
        boolean jobBackground = false;
        //默认超时时间10秒
        long jobTimeout = 10000;

        StringBuilder command = new StringBuilder(cmd);
        command.append(UserConstants.SPACE_ONE).append(oper);
        CommandLine commandLine = CommandLine.parse(command.toString());

        try {
            Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
            String stdout = output.get("stdout");
            String stderr = output.get("stderr");
            if (StringUtils.isEmpty(stderr) && !isStdoutContainsCustomError(stdout)) {
                return UserConstants.NORMAL;
            } else {
                LogUtils.ERROR_LOG.error("操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
            }
        } catch (IOException e) {
//            LogUtils.ERROR_LOG.error("操作失败， 命令为 " + command.toString(), e);
            LogUtils.ERROR_LOG.error("操作失败， 命令为 " + command.toString());
        }

        return UserConstants.EXCEPTION;
    }

    /**
     * 检查标准输出是否包含Error自定义输出
     */
    private boolean isStdoutContainsCustomError(String string) {
        String result[] = string.split("\n");
        for (String s : result) {
            if (s.trim().startsWith("Error")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int sysreboot(String ids) {
        return runTask(ids, "reboot");

    }

    @Override
    public int poweroff(String ids) {
        // TODO Auto-generated method stub
        return runTask(ids, "poweroff");
    }

}
