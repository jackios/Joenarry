package com.cs2c.project.module.table.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * ip禁用表 ip_table
 * 
 * @author Joenas
 * @date 2019-04-24
 */
public class Table extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Integer id;
	/** 禁用ip */
	private String ip;
	/** 注释信息 */
	private String text;
	/** 创建时间 */
	private Date createTime;
	/**  */
	private String col1;
	/**  */
	private String col2;
	/**  */
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
	public void setText(String text) 
	{
		this.text = text;
	}

	public String getText() 
	{
		return text;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
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
            .append("text", getText())
            .append("createTime", getCreateTime())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
