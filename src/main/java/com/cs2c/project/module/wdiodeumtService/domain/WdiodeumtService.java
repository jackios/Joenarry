package com.cs2c.project.module.wdiodeumtService.domain;

import com.cs2c.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 端口注册服务表 pro_wdiodeumt_service
 *
 * @author Joenas
 * @date 2020-03-09
 */
public class WdiodeumtService extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 注册端口标识，唯一
     */
    private String serviceName;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 修改时间
     */
    private Date dataTime;
    /**
     * wid号，不能重复，支持w001-w0100
     */
    private String wid;
    /**
     * 开放的端口
     */
    private String wiport;
    /**
     * 开放的端口
     */
    private String woport;
    /**
     * 协议tcp或udp
     */
    private String protocol;
    /**
     * tcn1允许接如的IP地址，每个地址之间都好隔开，最大支持10个
     */
    private String allowIp;
    /**
     * 入口端的参数，端口与上面一直，TCP为TCP-LISTEN:端口，udp为UPD-LISTEN:端口
     */
    private String inParmsa;
    /**
     * ip是rsw1单向光纤网卡的IP，这里端口必须跟上面3个一致，只有TCP
     */
    private String inParmsb;
    /**
     * 这里的IP和端口值得是要映射的服务器地址和端口，TCP/UDP注意
     */
    private String outParms;
    /**
     * 日志是否开启
     */
    private String logEnable;
    /**
     * 日志文件位置
     */
    private String logfile;
    /**
     * 是否启用
     */
    private String isEnable;

    private String currentStatus;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWid() {
        return wid;
    }

    public void setWiport(String wiport) {
        this.wiport = wiport;
    }

    public String getWiport() {
        return wiport;
    }

    public void setWoport(String woport) {
        this.woport = woport;
    }

    public String getWoport() {
        return woport;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setAllowIp(String allowIp) {
        this.allowIp = allowIp;
    }

    public String getAllowIp() {
        return allowIp;
    }

    public void setInParmsa(String inParmsa) {
        this.inParmsa = inParmsa;
    }

    public String getInParmsa() {
        return inParmsa;
    }

    public void setInParmsb(String inParmsb) {
        this.inParmsb = inParmsb;
    }

    public String getInParmsb() {
        return inParmsb;
    }

    public void setOutParms(String outParms) {
        this.outParms = outParms;
    }

    public String getOutParms() {
        return outParms;
    }

    public void setLogEnable(String logEnable) {
        this.logEnable = logEnable;
    }

    public String getLogEnable() {
        return logEnable;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public String getLogfile() {
        return logfile;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("serviceName", getServiceName())
                .append("description", getDescription())
                .append("dataTime", getDataTime())
                .append("wid", getWid())
                .append("wiport", getWiport())
                .append("woport", getWoport())
                .append("protocol", getProtocol())
                .append("allowIp", getAllowIp())
                .append("inParmsa", getInParmsa())
                .append("inParmsb", getInParmsb())
                .append("outParms", getOutParms())
                .append("logEnable", getLogEnable())
                .append("logfile", getLogfile())
                .append("isEnable", getIsEnable())
                .append("currentStatus", getCurrentStatus())
                .toString();
    }
}
