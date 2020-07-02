package com.cs2c.project.module.netdevinfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;

/**
 * 网络设备表 proc_netdevinfo
 * 
 * @author Joenas
 * @date 2018-10-13
 */
public class Netdevinfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 设备名称 */
	private String devName;
	/** ip地址 */
	private String ipaddr;
	/** 子网掩码 */
	private String netmask;
	/** 网关 */
	private String gateway;
	/** 开机自启动 */
	private String onboot;
	/** 链路状态 */
	private String linkStatus;
	/** 网络状态 */
	private String isActive;
	/** MAC地址 */
	private String mac;
	/** 备注 */
	private String comment;
	/** 附加列1 */
	private String col1;
	/** 附加列2 */
	private String col2;
	/** 附加列3 */
	private String col3;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setDevName(String devName) 
	{
		this.devName = devName;
	}

	public String getDevName() 
	{
		return devName;
	}
	public void setIpaddr(String ipaddr) 
	{
		this.ipaddr = ipaddr;
	}

	public String getIpaddr() 
	{
		return ipaddr;
	}
	public void setNetmask(String netmask) 
	{
		this.netmask = netmask;
	}

	public String getNetmask() 
	{
		return netmask;
	}
	public void setGateway(String gateway) 
	{
		this.gateway = gateway;
	}

	public String getGateway() 
	{
		return gateway;
	}
	public void setOnboot(String onboot) 
	{
		this.onboot = onboot;
	}

	public String getOnboot() 
	{
		return onboot;
	}
	public void setLinkStatus(String linkStatus) 
	{
		this.linkStatus = linkStatus;
	}

	public String getLinkStatus() 
	{
		return linkStatus;
	}
	public void setIsActive(String isActive) 
	{
		this.isActive = isActive;
	}

	public String getIsActive() 
	{
		return isActive;
	}
	public void setMac(String mac) 
	{
		this.mac = mac;
	}

	public String getMac() 
	{
		return mac;
	}
	public void setComment(String comment) 
	{
		this.comment = comment;
	}

	public String getComment() 
	{
		return comment;
	}
	public void setCol1(String col1) 
	{
		this.col1 = col1;
	}

	public String getCol1() 
	{
		return col1;
	}
	public void setCol2(String col2) 
	{
		this.col2 = col2;
	}

	public String getCol2() 
	{
		return col2;
	}
	public void setCol3(String col3) 
	{
		this.col3 = col3;
	}

	public String getCol3() 
	{
		return col3;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("devName", getDevName())
            .append("ipaddr", getIpaddr())
            .append("netmask", getNetmask())
            .append("gateway", getGateway())
            .append("onboot", getOnboot())
            .append("linkStatus", getLinkStatus())
            .append("isActive", getIsActive())
            .append("mac", getMac())
            .append("comment", getComment())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
