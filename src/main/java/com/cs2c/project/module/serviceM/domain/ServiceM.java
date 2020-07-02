package com.cs2c.project.module.serviceM.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;

/**
 * 服务管理表 proc_service_m
 * 
 * @author Joenas
 * @date 2018-10-24
 */
public class ServiceM extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 服务唯一键 */
	private String serviceKey;
	/** 服务名称 */
	private String serviceName;
	/** 服务类型 */
	/**
	 * 001: stop、reboot、enable、disable
	 * 002：start、stop、reboot、enable、disable
	 * 003：start、stop、reboot、reload、enable、disable
	 */
	private String serviceType;
	/** 服务描述 */
	private String serviceComment;
	/** 是否启用该服务管理 */
	private String isValid;
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
	public void setServiceKey(String serviceKey) 
	{
		this.serviceKey = serviceKey;
	}

	public String getServiceKey() 
	{
		return serviceKey;
	}
	public void setServiceName(String serviceName) 
	{
		this.serviceName = serviceName;
	}

	public String getServiceName() 
	{
		return serviceName;
	}
	public void setServiceType(String serviceType) 
	{
		this.serviceType = serviceType;
	}

	public String getServiceType() 
	{
		return serviceType;
	}
	public void setServiceComment(String serviceComment) 
	{
		this.serviceComment = serviceComment;
	}

	public String getServiceComment() 
	{
		return serviceComment;
	}
	public void setIsValid(String isValid) 
	{
		this.isValid = isValid;
	}

	public String getIsValid() 
	{
		return isValid;
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
            .append("serviceKey", getServiceKey())
            .append("serviceName", getServiceName())
            .append("serviceType", getServiceType())
            .append("serviceComment", getServiceComment())
            .append("isValid", getIsValid())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
