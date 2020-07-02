package com.cs2c.project.monitor.operlog.service;

import java.util.Calendar;
import java.util.List;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.project.monitor.operlog.domain.OperLog;
import com.cs2c.project.monitor.operlog.mapper.OperLogMapper;
import com.cs2c.project.system.user.domain.User;

/**
 * 操作日志 服务层处理
 * 
 * @author cs2c
 */
@Service
public class OperLogServiceImpl implements IOperLogService
{



    @Autowired
    private OperLogMapper operLogMapper;

    private codezip codeZip = new codezip();
    /**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog)
    {
        String str = operLog.getOperParam();
        JSONObject json = JSONObject.parseObject(str);
        
        if(json.containsKey("userId")) {
            json.put("userId", "[***]");
            //System.out.println(json);
        }
        if(json.containsKey("oldPassword")) {
            json.put("oldPassword", "[***]");
            //System.out.println(json);
        }
        if(json.containsKey("password")) {
            json.put("password", "[***]");
            //System.out.println(json);
        }
        if(json.containsKey("confirm")) {
            json.put("confirm", "[***]");
            //System.out.println(json);
        }
        if(json.containsKey("loginName")) {
            json.put("loginName", "[***]");
            //System.out.println(json);
        }
        
        
        operLog.setOperParam(json.toJSONString());
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     * 
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<OperLog> selectOperLogList(OperLog operLog)
    {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperLogByIds(String ids)
    {
        return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     * 
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public OperLog selectOperLogById(Long operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

    @Override
    public AjaxResult downloadF() {
        List<OperLog> list = selectOperLogList(new OperLog());
        String stdout = "";
        StringBuffer sb = new StringBuffer();
        for(OperLog operLog : list) {
            sb.append( operLog.getOperId()+"\t");
            if(operLog.getTitle().length() >= 5) sb.append(operLog.getTitle()+"\t");
            else sb.append(operLog.getTitle()+"\t\t");
            
            switch (operLog.getBusinessType()) {
                case 1:
                    sb.append("新增\t\t");
                    break;
                case 2:
                    sb.append("修改\t\t");
                    break;
                case 3:
                    sb.append("删除\t\t");
                    break;
                case 4:
                    sb.append("授权\t\t");
                    break;
                case 5:
                    sb.append("导出\t\t");
                    break;
                case 6:
                    sb.append("导入\t\t");
                    break;
                case 7:
                    sb.append("强退\t\t");
                    break;
                case 8:
                    sb.append("生成代码\t\t");
                    break;
            }
            if(operLog.getOperName().length() >= 8) sb.append(operLog.getOperName()+"\t");
            else sb.append(operLog.getOperName()+"\t\t");
            
            if(operLog.getDeptName().length() >= 8) sb.append(operLog.getDeptName()+"\t");
            else sb.append(operLog.getDeptName()+"\t\t");
            
            if(operLog.getOperIp().length() >= 8) sb.append(operLog.getOperIp()+"\t");
            else sb.append(operLog.getOperIp()+"\t\t");
            
            sb.append( (operLog.getStatus() == 0 ? "成功":"失败") +"\t");

            sb.append( operLog.getOperTime()+"\t");
            
            sb.append( operLog.getOperTime()+"\t");
            
            sb.append( operLog.getOperParam()+"\n");
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "操作日志-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
