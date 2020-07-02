package com.cs2c.project.monitor.logininfor.service;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.monitor.logininfor.domain.Logininfor;
import com.cs2c.project.monitor.logininfor.mapper.LogininforMapper;
import com.cs2c.project.system.user.domain.User;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author cs2c
 */
@Service
public class LogininforServiceImpl implements ILogininforService
{

    @Autowired
    private LogininforMapper logininforMapper;

    private codezip codeZip = new codezip();
    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(Logininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<Logininfor> selectLogininforList(Logininfor logininfor)
    {
        return logininforMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininforByIds(String ids)
    {
        return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    @Override
    public AjaxResult downloadF() {
        List<Logininfor> list = selectLogininforList(new Logininfor());
        String stdout = "";
        StringBuffer sb = new StringBuffer();
        for(Logininfor logininfor : list) {
            sb.append( logininfor.getInfoId()+"\t");
            if(logininfor.getLoginName().length() >= 16) sb.append(logininfor.getLoginName()+"\t");
            else if(logininfor.getLoginName().length() >= 8) sb.append(logininfor.getLoginName()+"\t\t");
            else sb.append(logininfor.getLoginName()+"\t\t\t");
            
            if(logininfor.getIpaddr().length() >= 8) sb.append(logininfor.getIpaddr()+"\t");
            else sb.append(logininfor.getIpaddr()+"\t\t");
            
            if(logininfor.getBrowser().length() >= 8) sb.append(logininfor.getBrowser()+"\t");
            else sb.append(logininfor.getBrowser()+"\t\t");
            
            if(logininfor.getOs().length() >= 8) sb.append(logininfor.getOs()+"\t");
            else sb.append(logininfor.getOs()+"\t\t");
            
            sb.append( (logininfor.getStatus().equals("0")? "成功":"失败") +"\t");
              
            if(logininfor.getMsg().length() >= 10) sb.append(logininfor.getMsg()+"\t");
            else if(logininfor.getMsg().length() >= 5) sb.append(logininfor.getMsg()+"\t\t");
            else sb.append(logininfor.getMsg()+"\t\t\t");


            sb.append( logininfor.getLoginTime()+"\n");
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "登陆日志-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
