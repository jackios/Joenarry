package com.cs2c.project.module.ftppoint.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.ftppoint.mapper.FtppointMapper;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.module.ftppoint.domain.Ftppoint;
import com.cs2c.project.module.ftppoint.service.IFtppointService;
import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 强制访问管理 服务层实现
 * 
 * @author Joenas
 * @date 2019-04-30
 */
@Service
public class FtppointServiceImpl implements IFtppointService 
{
    @Autowired
    private ICommandInfoService commandInfoService;
    
	@Autowired
	private FtppointMapper ftppointMapper;

	/**
     * 查询强制访问管理信息
     * 
     * @param id 强制访问管理ID
     * @return 强制访问管理信息
     */
    @Override
	public Ftppoint selectFtppointById(Integer id)
	{
	    return ftppointMapper.selectFtppointById(id);
	}
	
	/**
     * 查询强制访问管理列表
     * 
     * @param ftppoint 强制访问管理信息
     * @return 强制访问管理集合
     */
	@Override
	public List<Ftppoint> selectFtppointList(Ftppoint ftppoint)
	{
	    return ftppointMapper.selectFtppointList(ftppoint);
	}
	
	/**
     * 新增强制访问管理端点
     * 
     * @param ftppoint 端点信息
     * @return 结果
     */
    @Override
    public int insertFtppoint(Ftppoint ftppoint)
    {
        
        int code = test();
        if(code < 0 ) {return 0;}
        
        //SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
        System.out.println("insert new ftp ");
        ftppoint.setCreateBy(ShiroUtils.getLoginName());
        ftppoint.setCreateTime(new Date());

        if (!("E".equals(ftppoint.getType()) || "N".equals(ftppoint.getType()) || "F".equals(ftppoint.getType()))) {
            return UserConstants.CHANGE_0_RECORD;
        }

        String ftppointPath = getAbsoluteFtppointPath(ftppoint);
        Boolean ret = null;
        if ("E".equals(ftppoint.getType())) {
            ret = endpoint_control("endpoint_control", "add", ftppoint.getType(), ftppointPath, "", "");
        } else if ("F".equals(ftppoint.getType()) ) {
            ret = endpoint_control("endpoint_control", "add", ftppoint.getType(), ftppointPath, ftppoint.getUsername(), ftppoint.getPassword());
        } else {
            ret = endpoint_control("endpoint_control", "add", ftppoint.getType(), ftppointPath, ftppoint.getPerms(), ftppoint.getAllows());
        }


        System.out.println("File ");
        File file = new File("/etc/wdiode_se");  
        String conf = "";
        if(file.exists()) {
            BufferedReader reader = null;  
            try {  
                System.out.println("1 : ");
                reader = new BufferedReader(new FileReader(file)); 
                System.out.println("2 : ");
                String tempString = null;  
                while ((tempString = reader.readLine()) != null) {  
                    System.out.println("t : "+tempString);
                    if(!tempString.split(":")[0].equals(ftppoint.getUsername()))
                        conf += tempString + "\n";
                }  
                reader.close();  
            } catch (IOException e) {
                e.printStackTrace();
                
            } 
            finally {  
                if (reader != null) {  
                    try {  
                        reader.close();  
                    } catch (IOException e1) {}
        }}}
        System.out.println("c : "+conf);
        conf += ftppoint.getUsername()+":object_r:default_t:s0 \n";
        System.out.println("c : "+conf);
        BufferedWriter writer = null;  
        try {  
            writer = new BufferedWriter(new FileWriter(file));  
            writer.write(conf);
            writer.flush();
            writer.close();
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (writer != null) {  
                try {  
                    writer.close();  
                } catch (IOException e1) {  
        }}}
        String command = new String("semanage user -a " + ftppoint.getUsername() + " -r s0:c1 -R unconfined_r");
        System.out.println("command:"+command);
        String stderr= null , stdout = null;
        CommandLine commandLine = CommandLine.parse(command);
        try {
            Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            stderr = map.get("stderr");
            stdout = map.get("stdout");
        } catch (Exception e) {
        } finally {
        }
        
        if (ret) {
            return ftppointMapper.insertFtppoint(ftppoint);
        } else {
            LogUtils.ERROR_LOG.error("新增端点失败，请查看日志确认失败原因, 实例为 " + ftppoint);
        }
        
        return UserConstants.CHANGE_0_RECORD;
    }
	
    
    public int test() {
        String command = new String("getenforce");
        System.out.println("command:"+command);
        CommandLine commandLine = CommandLine.parse(command);
        String stderr= null , stdout = null;
        try {
            Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            stderr = map.get("stderr");
            stdout = map.get("stdout");
            System.out.println("getenforce : "+stdout + " "+!stdout.startsWith("Enforcing"));
            if(stdout.startsWith("Disabled")) {
                return -1;
            }
            stderr= null ; stdout = null;
            System.out.println("command: semanage user -l");
            commandLine = CommandLine.parse("semanage user -l");
            map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            stderr = map.get("stderr");
            stdout = map.get("stdout");
            System.out.println("semanage user -l : "+stdout);
            if(stdout.isEmpty() || stdout == "") {
                return -2;
            }
        } catch (Exception e) {
            System.out.println("error!!"+
            stderr+
            stdout);
            return -2;
        } finally {
        }
        return 0;
    }
	/**
     * 修改强制访问管理
     * 
     * @param ftppoint 强制访问管理信息
     * @return 结果
     */
	@Override
    public int updateFtppoint(Ftppoint ftppoint)
    {
	    ftppoint.setUpdateBy(ShiroUtils.getLoginName());
	    Ftppoint oldFtppoint = ftppointMapper.selectFtppointById(ftppoint.getId());
        ftppoint.setParentId(oldFtppoint.getParentId());

        Boolean ret = null;
        // 基本类型端点 允许更新端点名称
        if ("E".equals(ftppoint.getType())) {
            if (! oldFtppoint.getName().trim().equals(ftppoint.getName().trim())) {
                String ftppointPath = getAbsoluteFtppointPath(ftppoint);
                String oldFtppointPath = getAbsoluteFtppointPath(ftppoint);

                // update type oldPath newPath
                ret = endpoint_control("endpoint_control", "update", "E", oldFtppointPath, ftppointPath, "", "");
            }
        }
        // NFS类型允许更新 主机列表 和 权限
        else if ("N".equals(ftppoint.getType())){
            if (! (oldFtppoint.getAllows().trim().equals(ftppoint.getAllows()) && oldFtppoint.getPerms().trim().equals(ftppoint.getPerms().trim()))) {
                String ftppointPath = getAbsoluteFtppointPath(ftppoint);
                // update type path perms allows
                ret = endpoint_control("endpoint_control", "update", "N", ftppointPath, ftppoint.getPerms(), ftppoint.getAllows(), "");
            }
        }
        // FTP类型允许更新 密码
        else if ("F".equals(ftppoint.getType())) {
            if (!oldFtppoint.getPassword().trim().equals(ftppoint.getPassword().trim())) {
                // update type password
                ret = endpoint_control("endpoint_control", "update", "F", ftppoint.getUsername(), ftppoint.getPassword(), "", "");
            }
        }

        if (ret != null && !ret) {
            LogUtils.ERROR_LOG.error("更新端点失败");
            return UserConstants.CHANGE_0_RECORD;
        } else {
            return ftppointMapper.updateFtppoint(ftppoint);
        }
    }

	/**
     * 删除强制访问管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFtppointByIds(String ids)
    {
	    if(test() < 0 ) {return 0;}
	    
        Boolean ret = null;

        Integer id = Convert.toIntArray(ids)[0];

        Ftppoint ftppoint = ftppointMapper.selectFtppointById(id);
        String ftppointPath = getAbsoluteFtppointPath(ftppoint);

        if ("E".equals(ftppoint.getType())) {
            ret = endpoint_control("endpoint_control", "delete", ftppoint.getType(), ftppointPath, "", "");
        } else if ("F".equals(ftppoint.getType()) ) {
            ret = endpoint_control("endpoint_control", "delete", ftppoint.getType(), ftppointPath, ftppoint.getUsername(), ftppoint.getPassword());
        } else {
            ret = endpoint_control("endpoint_control", "delete", ftppoint.getType(), ftppointPath, ftppoint.getPerms(), ftppoint.getAllows());
        }
        
        
        System.out.println("File ");
        File file = new File("/etc/wdiode_se");  
        String conf = "";
        if(file.exists()) {
            BufferedReader reader = null;  
            try {  
                reader = new BufferedReader(new FileReader(file));  
                String tempString = null;  
                while ((tempString = reader.readLine()) != null) {  
                    if(!tempString.split(":")[0].equals(ftppoint.getUsername()))
                        conf += tempString + "\n";
                }  
                reader.close();  
            } catch (IOException e) {} 
            finally {  
                if (reader != null) {  
                    try {  
                        reader.close();  
                    } catch (IOException e1) {}
        }}}
        
        BufferedWriter writer = null;  
        try {  
            writer = new BufferedWriter(new FileWriter(file));  
            writer.write(conf);
            writer.flush();
            writer.close();
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (writer != null) {  
                try {  
                    writer.close();  
                } catch (IOException e1) {  
        }}}
        String command = new String("semanage user -d " + ftppoint.getUsername() + " -r s0:c1 -R unconfined_r");
        System.out.println("command:"+command);
        String stderr= null , stdout = null;
        CommandLine commandLine = CommandLine.parse(command);
        try {
            Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            stderr = map.get("stderr");
            stdout = map.get("stdout");
        } catch (Exception e) {
        } finally {
        }

        if (ret) {
            return ftppointMapper.deleteFtppointByIds(Convert.toStrArray(ids));
        } else {
            LogUtils.ERROR_LOG.error("删除端点失败，请查看日志确认失败原因, 实例为 " + ftppoint);
        }

        return UserConstants.CHANGE_0_RECORD;
    }

    /**
     * 查询子菜单数量
     *
     * @param parentId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountFtppointByParentId(Integer parentId) {
        // TODO Auto-generated method stub
        return ftppointMapper.selectCountFtppointByParentId(parentId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param endpoint 菜单信息
     * @return 结果
     */
    @Override
    public String checkFtppointNameUnique(Ftppoint ftppoint)
    {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (ftppoint.getName().contains(" ") || !pattern.matcher(ftppoint.getName()).find()) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }

        int id = StringUtils.isNull(ftppoint.getId()) ? -1 : ftppoint.getId();
        Ftppoint info = ftppointMapper.checkFtppointNameUnique(ftppoint.getName(), ftppoint.getParentId());

        if (StringUtils.isNotNull(info) && info.getId().intValue() != id)
        {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

    /**
     * 校验用户名是否唯一
     *
     * @param username 菜单信息
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String username)
    {
        if (username.contains(" ")) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }

        Boolean ret = endpoint_control("endpoint_control", "check", username, "", "", "", "");

        if (!ret)
        {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

    private String getAbsoluteFtppointPath(Ftppoint ftppoint) {
        List<Ftppoint> allDatas = ftppointMapper.selectFtppointList(new Ftppoint());

        Map<Integer, Ftppoint> allDataMaps = convertListToMap(allDatas);

        String[] paths = new String[20];
        int index = 0;
        paths[index++] = ftppoint.getName();
        Ftppoint tmpInst = null;
        int MaxDepth = 20;

        if (ftppoint.getParentId() == 0) {
            return ftppoint.getName() + File.separator;
        }

        int parentId = ftppoint.getParentId();

        while (MaxDepth > 0) {
            tmpInst = allDataMaps.get(parentId);

            if (tmpInst == null )
                break;

            paths[index++] = tmpInst.getName();

            if (parentId == 0) { // 0 => 根目录
                break;
            }

            parentId = tmpInst.getParentId();
            MaxDepth--;
        }

        StringBuilder sb = new StringBuilder();
        while (--index >= 0) {
            sb.append(paths[index].trim()).append(File.separator);
        }
        return sb.toString();
    }
    
    private Map<Integer, Ftppoint>  convertListToMap(List<Ftppoint> allDatas) {
        Map<Integer, Ftppoint> result = new HashMap<>();

        for (Ftppoint instance : allDatas) {
            result.put(instance.getId(), instance);
        }

        return result;
    }
    
    /**
     * 新增删除使用
     * @param commandKey
     * @param oper
     * @param type
     * @param endpoint
     * @param usernameOrPerms
     * @param passwdOrAllows
     * @return
     */
    private  Boolean endpoint_control(String commandKey, String oper, String type, String endpoint, String usernameOrPerms, String passwdOrAllows) {
        if ("E".equals(type)) {
            return endpoint_control(commandKey, oper, "Z", type, endpoint, "", "");
        } else {
            return endpoint_control(commandKey, oper, "Z", type, endpoint, usernameOrPerms, passwdOrAllows);
        }
    }
    
    /**
     * 端点管理
     *
     * @param commandKey
     * @param oper
     * @param type 端点类型
     * @param endpoint
     * @return
     */
    private Boolean endpoint_control(String commandKey, String oper, String currentType, String type, String endpoint, String usernameOrPerms, String passwdOrAllows) {
        CommandInfo commandInfo = commandInfoService.selectCommandInfoByKey(commandKey);
        if (commandInfo != null && !commandInfo.getValue().trim().equals("")) {
            boolean jobBackground = false;
            if (commandInfo.getBackground().trim().equals("yes")) {
                jobBackground = true;
            }

            long jobTimeout = 3000; // 默认3s超时
            if (!jobBackground) {
                if (commandInfo.getTimeout() != 0) {
                    jobTimeout = commandInfo.getTimeout();
                }
            }

            StringBuilder command = new StringBuilder(commandInfo.getValue());

            command.append(UserConstants.SPACE_ONE).append(oper)
                    .append(UserConstants.SPACE_ONE).append(currentType)
                    .append(UserConstants.SPACE_ONE).append(type)
                    .append(UserConstants.SPACE_ONE).append("\"" + endpoint + "\"")
                    .append(UserConstants.SPACE_ONE).append(usernameOrPerms)
                    .append(UserConstants.SPACE_ONE).append(passwdOrAllows);

            CommandLine commandLine = CommandLine.parse(command.toString());

            try {
                Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
                String stdout = output.get("stdout");
                String stderr = output.get("stderr");
                if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
                    return true;
                } else {
                    LogUtils.ERROR_LOG.error("执行端点操作出错，命令 " + command.toString() + " 时出错\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
                }
            } catch (IOException e) {
                LogUtils.ERROR_LOG.error("执行端点操作失败，命令 " + command.toString() + "失败 ", e);
            }
        } else {
            LogUtils.ERROR_LOG.error("数据库中不存在端点控制的command或command信息错误, 获取command时参数为 " + commandKey);
        }
        return false;
    }
    
    @Override

    public String checkAllow(Ftppoint ftppoint) {
        String ip = ftppoint.getAllows();
        int point = 0 , star = 0 ;
        for(int i=0;i<ip.length();i++){
            char ch = ip.charAt(i);
            if( ch == '.') {point++;}
            if( ch == '*') {star++;}
        }
        if( star > point/3) {
            return "1";
        }
        return "0";
    }
	
}
