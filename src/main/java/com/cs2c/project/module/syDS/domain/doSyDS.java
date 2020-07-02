package com.cs2c.project.module.syDS.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.os.ShellUtils.ShellResultHandler;
import com.cs2c.project.module.syDS.mapper.SyDSMapper;

public class doSyDS extends Thread{
    
    String engine = "corp-000";
    String port = "18033";
    @Autowired
    private SyDSMapper syDSMapper;
    SyDS syDS = null;
    boolean going = false;
    
    public void setEnginePort(String engine , String port , SyDS syDS) {
        this.engine = engine;
        this.port = port;
        this.syDS = syDS;
    }
    
    public void setGoing(boolean going) {
        this.going = going;
    }
    
    public SyDS getSyDS() {
        return  syDS;
    }
    
    public boolean isGoing(String group) {
        if (this.syDS.getGroupId().equals(group)) {return true;}
        return  false;
    }
    
    @Override
    public void run() {
        this.going = true;
        //String command = new String("setsid " +System.getProperty("user.dir")+"/syDS/bin/sym --engine "+engine+" --port "+port +" >/dev/null 2>&1 &");
        String command = new String("setsid /usr/sbin/syDS/bin/sym --engine "+engine+" --port "+port +" >/dev/null 2>&1 &");
        System.out.println("command:"+command);
        CommandLine commandLine = CommandLine.parse(command);
        String stderr= null , stdout = null;
        try {
            ShellResultHandler resultHandler = ShellUtils.run(commandLine, 0, false);
            
            //exec.execute(commandLine);
        } catch (ExecuteException e) {
            // TODO Auto-generated catch block
            //LogUtils.ERROR_LOG.error("创建子线程并执行时， 发生异常 ===> \n");
            //e.printStackTrace();
            //System.out.println(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } finally {
            //System.out.println(syDS.getGroupId()+" group node is ending , its id is"+syDS.getId());
            //syDS.setCol1("否");
            //syDSMapper.updateSyDS(syDS);
            //System.out.println(syDS.getGroupId()+" : "+syDS.getId()+"  node is end complet");
            this.going = false;
        }
    }
    
    public String toString() {
        String str = "engine"+engine+"going"+going;
        return  str;
    }
}
