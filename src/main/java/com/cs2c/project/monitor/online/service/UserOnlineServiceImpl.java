package com.cs2c.project.monitor.online.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.DateUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.shiro.session.OnlineSessionDAO;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.monitor.online.domain.UserOnline;
import com.cs2c.project.monitor.online.mapper.UserOnlineMapper;
import com.cs2c.project.system.user.domain.User;

/**
 * 在线用户 服务层处理
 * 
 * @author cs2c
 */
@Service
public class UserOnlineServiceImpl implements IUserOnlineService
{
    @Autowired
    private UserOnlineMapper userOnlineDao;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    private codezip codeZip = new codezip();
    /**
     * 通过会话序号查询信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public UserOnline selectOnlineById(String sessionId)
    {
        return userOnlineDao.selectOnlineById(sessionId);
    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public void deleteOnlineById(String sessionId)
    {
        UserOnline userOnline = selectOnlineById(sessionId);
        if (StringUtils.isNotNull(userOnline))
        {
            userOnlineDao.deleteOnlineById(sessionId);
        }
    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    @Override
    public void batchDeleteOnline(List<String> sessions)
    {
        for (String sessionId : sessions)
        {
            UserOnline userOnline = selectOnlineById(sessionId);
            if (StringUtils.isNotNull(userOnline))
            {
                userOnlineDao.deleteOnlineById(sessionId);
            }
        }
    }

    /**
     * 保存会话信息
     * 
     * @param online 会话信息
     */
    @Override
    public void saveOnline(UserOnline online)
    {
        userOnlineDao.saveOnline(online);
    }

    /**
     * 查询会话集合
     * 
     * @param pageUtilEntity 分页参数
     */
    @Override
    public List<UserOnline> selectUserOnlineList(UserOnline userOnline)
    {
        return userOnlineDao.selectUserOnlineList(userOnline);
    }

    /**
     * 强退用户
     * 
     * @param sessionId 会话ID
     */
    @Override
    public void forceLogout(String sessionId)
    {
        Session session = onlineSessionDAO.readSession(sessionId);
        if (session == null)
        {
            return;
        }
        session.setTimeout(1000);
        userOnlineDao.deleteOnlineById(sessionId);
    }

    /**
     * 查询会话集合
     * 
     * @param online 会话信息
     */
    @Override
    public List<UserOnline> selectOnlineByExpired(Date expiredDate)
    {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return userOnlineDao.selectOnlineByExpired(lastAccessTime);
    }

    @Override
    public AjaxResult downloadF() {
        List<UserOnline> list = selectUserOnlineList(new UserOnline());
        String stdout = "";
        for(UserOnline user : list) {
            stdout += user.getSessionId()+"\t\t";
            
            if(user.getLoginName().length() >= 8) stdout +=user.getLoginName()+"\t";
            else stdout +=user.getLoginName()+"\t\t";
            
            if(user.getDeptName().length() >= 8) stdout +=user.getDeptName()+"\t";
            else stdout +=user.getDeptName()+"\t\t";
            
            if(user.getIpaddr().length() >= 8) stdout +=user.getIpaddr()+"\t";
            else stdout +=user.getIpaddr()+"\t\t";
            
            if(user.getBrowser().length() >= 8) stdout +=user.getBrowser()+"\t";
            else stdout +=user.getBrowser()+"\t\t";
            
            if(user.getOs().length() >= 8) stdout +=user.getOs()+"\t";
            else stdout +=user.getOs()+"\t\t";
            
            stdout += user.getStartTimestamp()+"\t";

            stdout += user.getLastAccessTime()+"\n";
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "在线用户-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
