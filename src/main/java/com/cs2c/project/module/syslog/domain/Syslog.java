package com.cs2c.project.module.syslog.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;

/**
 * 日志管理表 proc_syslog
 * 
 * @author Joenas
 * @date 2018-11-06
 */
public class Syslog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 远端IP */
	private String ip;
	/** 描述信息 */
	private String description;
	/** 日志类型 */
	private String type;
	/** 同步间隔 */
	private Integer synctime;
	/** 是否启用 */
	private String valid;
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
	public void setIp(String ip) 
	{
		this.ip = ip;
	}

	public String getIp() 
	{
		return ip;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}
	public void setSynctime(Integer synctime) 
	{
		this.synctime = synctime;
	}

	public Integer getSynctime() 
	{
		return synctime;
	}
	public void setValid(String valid) 
	{
		this.valid = valid;
	}

	public String getValid() 
	{
		return valid;
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
            .append("ip", getIp())
            .append("description", getDescription())
            .append("type", getType())
            .append("synctime", getSynctime())
            .append("valid", getValid())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
