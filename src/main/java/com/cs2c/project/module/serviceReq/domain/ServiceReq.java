package com.cs2c.project.module.serviceReq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 系统服务查询表 sys_service_req
 * 
 * @author Joenas
 * @date 2020-04-30
 */
public class ServiceReq extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Long id;
	/** host地址 */
	private String host;
	/** 请求url */
	private String url;
	/** 请求时间 */
	private Date time;
	/** 请求大小 */
	private String rSize;
	/** 请求文件 */
	private String rFile;
	/** 进程号 */
	private String pid;
	/** 请求方法 */
	private String method;
	/** 附加列1 */
	private String col1;
	/** 附加列2 */
	private String col2;
	/** 附加列3 */
	private String col3;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setHost(String host) 
	{
		this.host = host;
	}

	public String getHost() 
	{
		return host;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getUrl() 
	{
		return url;
	}
	public void setTime(Date time) 
	{
		this.time = time;
	}

	public Date getTime() 
	{
		return time;
	}
	public void setRSize(String rSize) 
	{
		this.rSize = rSize;
	}

	public String getRSize() 
	{
		return rSize;
	}
	public void setRFile(String rFile) 
	{
		this.rFile = rFile;
	}

	public String getRFile() 
	{
		return rFile;
	}
	public void setPid(String pid) 
	{
		this.pid = pid;
	}

	public String getPid() 
	{
		return pid;
	}
	public void setMethod(String method) 
	{
		this.method = method;
	}

	public String getMethod() 
	{
		return method;
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
            .append("host", getHost())
            .append("url", getUrl())
            .append("time", getTime())
            .append("rSize", getRSize())
            .append("rFile", getRFile())
            .append("pid", getPid())
            .append("method", getMethod())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}