package com.cs2c.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author cs2c
 */
@Component
@ConfigurationProperties(prefix = "joenas")
public class JoenasConfig
{
    /** 配置文件历史版本数 */
    private Integer configVersions;

    private String logoutTimeout;

    private String ddosPath;

    public String getDdosPath() {
        return ddosPath;
    }

    public void setDdosPath(String ddosPath) {
        this.ddosPath = ddosPath;
    }

    public String getLogoutTimeout() {
        return logoutTimeout;
    }

    public void setLogoutTimeout(String logoutTimeout) {
        this.logoutTimeout = logoutTimeout;
    }

    public Integer getConfigVersions() {
        return configVersions;
    }

    public void setConfigVersions(Integer configVersions) {
        this.configVersions = configVersions;
    }


    private  String proxy_config_path;
    private  String proxy_xsd_path;
    private  String proxy_json_path;
    private String wdiode_config_path;
    private String limitPerc;

    public String getLimitPerc() {
        return limitPerc;
    }

    public void setLimitPerc(String limitPerc) {
        this.limitPerc = limitPerc;
    }

    public String getGarbageFOD() {
        return garbageFOD;
    }

    public void setGarbageFOD(String garbageFOD) {
        this.garbageFOD = garbageFOD;
    }

    private String garbageFOD;

    public String getWdiode_config_path() {
        return wdiode_config_path;
    }

    public void setWdiode_config_path(String wdiode_config_path) {
        this.wdiode_config_path = wdiode_config_path;
    }

    public  String getProxy_config_path() {
        return proxy_config_path;
    }

    public void setProxy_config_path(String proxy_config_path) {
        this.proxy_config_path = proxy_config_path;
    }

    public  String getProxy_xsd_path() {
        return proxy_xsd_path;
    }

    public void setProxy_xsd_path(String proxy_xsd_path) {
        this.proxy_xsd_path = proxy_xsd_path;
    }
    
    public  String getProxy_json_path() {
        return proxy_json_path;
    }

    public void setProxy_json_path(String proxy_json_path) {
        this.proxy_json_path = proxy_json_path;
    }
}
