package com.cs2c.project.module.license.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * License表 proc_license
 * 
 * @author Joenas
 * @date 2018-10-11
 */
public class License extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 注册码 */
	private String content;
	/** 注册码 */
	private String hostid;
	/** 注册命令 */
	private String registerCmd;
	/** 确认是否注册的命令 */
	private String verifyCmd;
	/** 获取hostid的命令 */
	private String hostidCmd;
	/** 注册的时间 */
	private Date dataTime;
	/** license所属的产品类别 */
	private String type;
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
	public void setContent(String content) 
	{
		this.content = content;
	}

	public String getContent() 
	{
		return content;
	}
	public void setHostid(String hostid) 
	{
		this.hostid = hostid;
	}

	public String getHostid() 
	{
		return hostid;
	}
	public void setRegisterCmd(String registerCmd) 
	{
		this.registerCmd = registerCmd;
	}

	public String getRegisterCmd() 
	{
		return registerCmd;
	}
	public void setVerifyCmd(String verifyCmd) 
	{
		this.verifyCmd = verifyCmd;
	}

	public String getVerifyCmd() 
	{
		return verifyCmd;
	}
	public void setHostidCmd(String hostidCmd) 
	{
		this.hostidCmd = hostidCmd;
	}

	public String getHostidCmd() 
	{
		return hostidCmd;
	}
	public void setDataTime(Date dataTime) 
	{
		this.dataTime = dataTime;
	}

	public Date getDataTime() 
	{
		return dataTime;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
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
            .append("content", getContent())
            .append("hostid", getHostid())
            .append("registerCmd", getRegisterCmd())
            .append("verifyCmd", getVerifyCmd())
            .append("hostidCmd", getHostidCmd())
            .append("dataTime", getDataTime())
            .append("type", getType())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
