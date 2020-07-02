package com.cs2c.project.system.notice.service;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.system.notice.mapper.NoticeMapper;
import com.cs2c.project.system.notice.domain.Notice;
import com.cs2c.project.system.notice.service.INoticeService;
import com.cs2c.project.system.user.domain.User;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;

/**
 * 公告 服务层实现
 * 
 * @author cs2c
 * @date 2018-06-25
 */
@Service
public class NoticeServiceImpl implements INoticeService
{
    @Autowired
    private NoticeMapper noticeMapper;

    private codezip codeZip = new codezip();
    
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public Notice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(Notice notice)
    {
        notice.setCreateBy(ShiroUtils.getLoginName());
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(Notice notice)
    {
        notice.setUpdateBy(ShiroUtils.getLoginName());
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids)
    {
        return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
    }

    @Override
    public AjaxResult downloadF() {
        List<Notice> list = selectNoticeList(new Notice());
        String stdout = "";
        for(Notice note : list) {
            stdout += note.getNoticeId()+"\t";
            
            if(note.getNoticeTitle().length() >= 10) stdout +=note.getNoticeTitle()+"\t";
            else if(note.getNoticeTitle().length() >= 5) stdout +=note.getNoticeTitle()+"\t\t";
            else stdout +=note.getNoticeTitle()+"\t\t\t";
            
            stdout += note.getNoticeType()+"\t";
            
            if(note.getCreateBy().length() >= 8) stdout +=note.getCreateBy()+"\t";
            else stdout +=note.getCreateBy()+"\t\t";

            stdout += note.getCreateTime()+"\n";
            
            stdout += note.getNoticeContent()+"\n";
        }
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "通知公告-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }

}
