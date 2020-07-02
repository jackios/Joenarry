package com.cs2c.project.module.wdiodeConfig.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * wdiode配置表 proc_wdiode_config
 * 
 * @author Joenas
 * @date 2018-10-14
 */
public class WdiodeConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 配置文件内容 */
	private String contents;
	/** 保存日期 */
	private Date dataTime;
	/** 描述信息 */
	private String description;
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
	public void setContents(String contents) 
	{
		this.contents = contents;
	}

	public String getContents() 
	{
		return contents;
	}
	public void setDataTime(Date dataTime) 
	{
		this.dataTime = dataTime;
	}

	public Date getDataTime() 
	{
		return dataTime;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
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
            .append("contents", getContents())
            .append("dataTime", getDataTime())
            .append("description", getDescription())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
