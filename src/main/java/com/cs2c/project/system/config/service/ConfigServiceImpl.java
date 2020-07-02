package com.cs2c.project.system.config.service;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.system.config.domain.Config;
import com.cs2c.project.system.config.mapper.ConfigMapper;
import com.cs2c.project.system.user.domain.User;

/**
 * 参数配置 服务层实现
 * 
 * @author cs2c
 */
@Service
public class ConfigServiceImpl implements IConfigService
{
    @Autowired
    private ConfigMapper configMapper;

    private codezip codeZip = new codezip();
    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public Config selectConfigById(Long configId)
    {
        Config config = new Config();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configName 参数名称
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        Config config = new Config();
        config.setConfigKey(configKey);
        Config retConfig = configMapper.selectConfig(config);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";
    }

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<Config> selectConfigList(Config config)
    {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(Config config)
    {
        config.setCreateBy(ShiroUtils.getLoginName());
        return configMapper.insertConfig(config);
    }

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(Config config)
    {
        config.setUpdateBy(ShiroUtils.getLoginName());
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除参数配置对象
     * 
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(String ids)
    {
        return configMapper.deleteConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(Config config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        Config info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

    @Override
    public AjaxResult downloadF() {
        List<Config> list = selectConfigList(new Config());
        String stdout = "";
        for(Config config : list) {
            stdout += config.getConfigId()+"\t";
            if(config.getConfigName().length() >= 8) stdout +=config.getConfigName()+"\t";
            else stdout +=config.getConfigName()+"\t\t";
            
            if(config.getConfigKey().length() >= 16) stdout +=config.getConfigKey()+"\t";
            else if(config.getConfigKey().length() >= 8) stdout +=config.getConfigKey()+"\t\t";
            else stdout +=config.getConfigKey()+"\t\t\t";
            
            if(config.getConfigValue().length() >= 8) stdout +=config.getConfigValue()+"\t";
            else stdout +=config.getConfigValue()+"\t\t";
            
            
            stdout += (config.getConfigType().equals("Y")? "是":"否") +"\t";
           
            stdout += config.getCreateTime()+"\n";
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "参数设置-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }

}
