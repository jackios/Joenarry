package com.cs2c.project.module.ftplog.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.ftppoint.mapper.FtppointMapper;
import com.cs2c.project.module.ftppoint.domain.Ftppoint;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 强制访问管理 服务层实现
 * 
 * @author Joenas
 * @date 2019-04-30
 */
@Service
public class FtplogServiceImpl implements IFtplogService 
{
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
     * 新增强制访问管理
     * 
     * @param ftppoint 强制访问管理信息
     * @return 结果
     */
	@Override
	public int insertFtppoint(Ftppoint ftppoint)
	{
	    return ftppointMapper.insertFtppoint(ftppoint);
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
	    return ftppointMapper.updateFtppoint(ftppoint);
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
		return ftppointMapper.deleteFtppointByIds(Convert.toStrArray(ids));
	}

    @Override
    public AjaxResult showlog() {
        
        String command = new String("netstat -anp | grep ':21 '");
        System.out.println("command:"+command);
        List<String> cmds = new ArrayList<String>(); 
        cmds.add("sh"); 
        cmds.add("-c"); 
        cmds.add(command); 
        ProcessBuilder pb=new ProcessBuilder(cmds); 
        BufferedReader reader = null;
        String line;
        Map<String, String> outstr = new HashMap<String, String>(); 
        try {
            Process p = pb.start();
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = reader.readLine()) != null) {outstr.put((line.split("\\s+")[4].split(":")[0]+":"+line.split("\\s+")[6].split("/")[0]),"0");}
            reader.close();
            //System.out.println("out : "+ outstr.size());
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            if(reader != null) 
                try {reader.close();} catch (IOException e) {}
        }
        
        if(outstr.size() == 0) {
            return AjaxResult.error("ftp服务未启动。");
        }
        //outstr.remove("0.0.0.0:*");
        //outstr.put("10.1.30.121", "0");
        for (Map.Entry<String, String> entry: outstr.entrySet()) {
            if(entry.getKey().startsWith("0.0.0.0")) {
                outstr.remove(entry.getKey());
                break;
            }
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
        }

        File file = new File("/var/log/vsftpd.log");  
        if( file.exists()) {AjaxResult.error("无日志文件。");}
        reader = null;  
        String text = "", user = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-HH:mm:ss-yyyy", Locale.US);
        Date date = new Date();
        try {
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) { 
                String str[] = tempString.split("\\s+");

                if((str[8] +str[9]).equals("OKLOGIN:")) {
                    String ip = str[str.length-1].substring(1, str[str.length-1].length()-1)+":"+str[6].substring(0,str[6].length()-1);
                    
                    String time = str[1]+"-"+str[2]+"-"+str[3]+"-"+str[4];
                    Date date2 = sdf.parse(time);
                    
                    long diff = date.getTime() - date2.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) ;
                    
                    String use = "用户:"+str[7]+" 登陆时长:"+diffHours + "时" + diffMinutes + "分" + diffSeconds+"秒";
                    System.out.println("user :"+use + " ip:"+ip);
                    
                    if(outstr.containsKey(ip)) 
                        outstr.put(ip, use);
                }
                text += tempString + "\n";
            }  
            for (Map.Entry<String, String> entry: outstr.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
                user += entry.getValue() + "\n";
            }
            reader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        } 
        if(user.isEmpty() || user == "") {user = "<无>";}
        //System.out.println(text);
        return AjaxResult.success2s(text, user);
    }
	
}







