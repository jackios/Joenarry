package com.cs2c.project.module.table.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.table.mapper.TableMapper;
import com.cs2c.project.module.table.domain.Table;
import com.cs2c.project.module.table.service.ITableService;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * ip禁用 服务层实现
 * 
 * @author Joenas
 * @date 2019-04-24
 */
@Service
public class TableServiceImpl implements ITableService 
{
	@Autowired
	private TableMapper tableMapper;

    private codezip codeZip = new codezip();
	
	int hasiptable = 0;
	/**
     * 查询ip禁用信息
     * 
     * @param id ip禁用ID
     * @return ip禁用信息
     */
    @Override
	public Table selectTableById(Integer id)
	{
        
	    return tableMapper.selectTableById(id);
	}
	
	/**
     * 查询ip禁用列表
     * 
     * @param table ip禁用信息
     * @return ip禁用集合
     */
	@Override
	public List<Table> selectTableList(Table table)
	{
	    
	    String command = new String("systemctl status iptables");
        System.out.println("command:"+command);
        
        CommandLine commandLine = CommandLine.parse(command);
        String stderr= null , stdout = null;
        try {
            Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            stderr = map.get("stderr");
            stdout = map.get("stdout");
        } catch (ExecuteException e) {
        } catch (IOException e) {
        } finally {
        }
        System.out.println("stderr :"+stderr);
        System.out.println(stderr.startsWith("Unit iptables.service could not be found."));
        if( stderr != null && stderr.startsWith("Unit iptables.service could not be found.") ) {
            List<Table> list = new ArrayList<Table>();
            Table tab = new Table();
            tab.setText("没有防火墙规则。");
            tab.setIp("没有防火墙规则。");
            tab.setCol1("x");
            list.add(tab);
            hasiptable = -1;
            return list;
        }
        try {
            commandLine = CommandLine.parse("systemctl enable iptables");
            ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            commandLine = CommandLine.parse("systemctl start iptables");
            ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
        } catch (ExecuteException e) {
        } catch (IOException e) {
            
        }
        hasiptable = 1;
	    List<Table> list = tableMapper.selectTableList(table);
	    
	    return list;
	}
	
    /**
     * 新增ip禁用
     * 
     * @param table ip禁用信息
     * @return 结果
     */
	@Override
	public int insertTable(Table table)
	{  
	    if(hasiptable < 0) {return 0;}
	    System.out.println(table);
	    String command = "";
	    if(table.getCol3().equals("接收")) {
	        command = new String("iptables -I INPUT 1 -m iprange --src-range " + table.getIp() + " -j DROP ");
	    }else if(table.getCol3().equals("发送")) {
	        command = new String("iptables -I OUTPUT 1 -m iprange --dst-range " + table.getIp() + " -j DROP ");
	    }else {return 0;}
        System.out.println("command:"+command);
        
        CommandLine commandLine = CommandLine.parse(command);
        String stderr= null , stdout = null;
        try {
            Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            commandLine = CommandLine.parse("iptables-save > /etc/sysconfig/iptables");
            ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
            stderr = map.get("stderr");
            stdout = map.get("stdout");
        } catch (ExecuteException e) {
        } catch (IOException e) {
        } finally {
        }
        System.out.println("stdout :"+stdout);
        System.out.println("stderr :"+stderr);

	    return tableMapper.insertTable(table);
	}
	
	/**
     * 修改ip禁用
     * 
     * @param table ip禁用信息
     * @return 结果
     */
	@Override
	public int updateTable(Table table)
	{
	    if(hasiptable < 0) {return 0;}
	    return tableMapper.updateTable(table);
	}

	/**
     * 删除ip禁用对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTableByIds(String ids)
	{
	    if(hasiptable < 0) {return 0;}
	    String[] strs  = Convert.toStrArray(ids);
	    for(int i = 0 ; i<strs.length ; i++) {
	        Table table =tableMapper.selectTableById(Integer.parseInt(strs[i]));
	        String command = "";
	        if(table.getCol3().equals("接收")) {
	            command = new String("iptables -D INPUT  -m iprange --src-range " + table.getIp() + " -j DROP ");
	        }else if(table.getCol3().equals("发送")) {
	            command = new String("iptables -D OUTPUT  -m iprange --dst-range " + table.getIp() + " -j DROP ");
            }else {return 0;
	        }
	        System.out.println("command:"+command);
            
            CommandLine commandLine = CommandLine.parse(command);
            String stderr= null , stdout = null;
            try {
                Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
                commandLine = CommandLine.parse("iptables-save > /etc/sysconfig/iptables");
                ShellUtils.runAndGetOutput(commandLine, 60*1000, false);
                
                stderr = map.get("stderr");
                stdout = map.get("stdout");
            } catch (ExecuteException e) {
            } catch (IOException e) {
            } finally {
            }
            System.out.println("stdout :"+stdout);
            System.out.println("stderr :"+stderr);
	    }
	    return tableMapper.deleteTableByIds(strs);
	}

    
    @Override
    public AjaxResult downloadF() {
        if(hasiptable < 0) {return AjaxResult.error("无iptables服务。");}
        CommandLine commandLine = CommandLine.parse("iptables-save");
        String stderr= null , stdout = null;
        try {
            Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000, false);

            stderr = map.get("stderr");
            stdout = map.get("stdout");
        } catch (ExecuteException e) {
        } catch (IOException e) {
        } finally {
        }
        //System.out.println("stdout :"+stdout);
        //System.out.println("stderr :"+stderr);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "防火墙配置-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }


}




