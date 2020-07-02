package com.cs2c.project.module.trustHost.service;

import java.util.Calendar;
import java.util.List;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.trustHost.mapper.TrustHostMapper;
import com.cs2c.project.module.trustHost.domain.TrustHost;
import com.cs2c.project.module.trustHost.service.ITrustHostService;
import com.cs2c.project.system.user.domain.User;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 可信主机 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Service
public class TrustHostServiceImpl implements ITrustHostService 
{
    @Autowired
    private TrustHostMapper trustHostMapper;

    private codezip codeZip = new codezip();
    /**
     * 查询可信主机信息
     * 
     * @param id 可信主机ID
     * @return 可信主机信息
     */
    @Override
    public TrustHost selectTrustHostById(Integer id)
    {
        return trustHostMapper.selectTrustHostById(id);
    }
    
    /**
     * 查询可信主机列表
     * 
     * @param trustHost 可信主机信息
     * @return 可信主机集合
     */
    @Override
    public List<TrustHost> selectTrustHostList(TrustHost trustHost)
    {
        return trustHostMapper.selectTrustHostList(trustHost);
    }
    
    /**
     * 新增可信主机
     * 
     * @param trustHost 可信主机信息
     * @return 结果
     */
    @Override
    public int insertTrustHost(TrustHost trustHost)
    {
        try {
        return trustHostMapper.insertTrustHost(trustHost);
        }catch(Exception e) {
            return 0;
        }
    }
    
    /**
     * 修改可信主机
     * 
     * @param trustHost 可信主机信息
     * @return 结果
     */
    @Override
    public int updateTrustHost(TrustHost trustHost)
    {
        return trustHostMapper.updateTrustHost(trustHost);
    }

    /**
     * 删除可信主机对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTrustHostByIds(String ids)
    {
        return trustHostMapper.deleteTrustHostByIds(Convert.toStrArray(ids));
    }


    /**
     * 校验IPADDR
     */
    @Override
    public String checkIpAddress(String ip)
    {
        if (ip.matches(UserConstants.IPADDRESS_PATTERN)) {
            return UserConstants.NORMAL;
        }
        return UserConstants.EXCEPTION;
    }

    @Override
    public AjaxResult downloadF() {
        List<TrustHost> list = selectTrustHostList(new TrustHost());
        String stdout = "";
        for(TrustHost host : list) {
            stdout += host.getId()+"\t";
            
            if(host.getIp().length() >= 8) stdout +=host.getIp()+"\t";
            else stdout +=host.getIp()+"\t\t";

            if(host.getMac().length() >= 16) stdout +=host.getMac()+"\t";
            else if(host.getMac().length() >= 8) stdout +=host.getMac()+"\t\t";
            else stdout +=host.getMac()+"\t\t\t";
            
            stdout += host.getIsValid()+"\t";

            stdout += host.getDescription()+"\n";
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "可信主机-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }

}
