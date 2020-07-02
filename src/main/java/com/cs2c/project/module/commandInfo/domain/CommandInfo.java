package com.cs2c.project.module.commandInfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;

/**
 * CommandInfo表 proc_command_info
 * 
 * @author Joenas
 * @date 2018-10-12
 */
public class CommandInfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 执行命令的用户 */
	private String user;
	/** 命令键 */
	private String key;
	/** 命令值 */
	private String value;
	/** 是否后台运行 */
	private String background;
	/** 是否后台运行 */
	private Long timeout;
	/** 描述信息 */
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
	public void setUser(String user) 
	{
		this.user = user;
	}

	public String getUser() 
	{
		return user;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}

	public String getKey() 
	{
		return key;
	}
	public void setValue(String value) 
	{
		this.value = value;
	}

	public String getValue() 
	{
		return value;
	}
	public void setBackground(String background) 
	{
		this.background = background;
	}

	public String getBackground() 
	{
		return background;
	}
	public void setTimeout(Long timeout)
	{
		this.timeout = timeout;
	}

	public Long getTimeout()
	{
		return timeout;
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
            .append("user", getUser())
            .append("key", getKey())
            .append("value", getValue())
            .append("background", getBackground())
            .append("timeout", getTimeout())
            .append("comment", getComment())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
