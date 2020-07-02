package com.cs2c.project.module.syDS.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;

/**
 * DS设置表 sys_sy_d_s
 * 
 * @author Joenas
 * @date 2019-01-03
 */
public class SyDS extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Integer id;
	/** 节点编号 */
	private Integer externalId;
	/** 节点组名 */
	private String groupId;
	/** 数据库驱动 */
	private String dbDriver;
	/** 数据库ip */
	private String ip;
	/** 数据库名 */
	private String dataBaseN;
	/** 数据库表名 */
	private String dataBaseTableN;
	/** 数据库登陆账号 */
	private String usrn;
	/** 数据库登陆密码 */
	private String usrp;
	/** 同步ip */
	private String registrationUrl;
	/** 检查数据库频率 */
	private Integer routingTime;
	/** 推送频率 */
	private Integer pushTime;
	/** 不再重发 */
	private String dontResend;
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
	public void setExternalId(Integer externalId) 
	{
		this.externalId = externalId;
	}

	public Integer getExternalId() 
	{
		return externalId;
	}
	public void setGroupId(String groupId) 
	{
		this.groupId = groupId;
	}

	public String getGroupId() 
	{
		return groupId;
	}
	public void setDbDriver(String dbDriver) 
	{
		this.dbDriver = dbDriver;
	}

	public String getDbDriver() 
	{
		return dbDriver;
	}
	public void setIp(String ip) 
	{
		this.ip = ip;
	}

	public String getIp() 
	{
		return ip;
	}
   public void setDataBaseN(String dataBaseN) 
    {
        this.dataBaseN = dataBaseN;
    }

    public String getDataBaseN() 
    {
        return dataBaseN;
    }
    public void setDataBaseTableN(String dataBaseTableN) 
    {
        this.dataBaseTableN = dataBaseTableN;
    }

    public String getDataBaseTableN() 
    {
        return dataBaseTableN;
    }
	public void setUsrn(String usrn) 
	{
		this.usrn = usrn;
	}

	public String getUsrn() 
	{
		return usrn;
	}
	public void setUsrp(String usrp) 
	{
		this.usrp = usrp;
	}

	public String getUsrp() 
	{
		return usrp;
	}
	public void setRegistrationUrl(String registrationUrl) 
	{
		this.registrationUrl = registrationUrl;
	}

	public String getRegistrationUrl() 
	{
		return registrationUrl;
	}
	public void setRoutingTime(Integer routingTime) 
	{
		this.routingTime = routingTime;
	}

	public Integer getRoutingTime() 
	{
		return routingTime;
	}
	public void setPushTime(Integer pushTime) 
	{
		this.pushTime = pushTime;
	}

	public Integer getPushTime() 
	{
		return pushTime;
	}
	public void setDontResend(String dontResend) 
	{
		this.dontResend = dontResend;
	}

	public String getDontResend() 
	{
		return dontResend;
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
            .append("externalId", getExternalId())
            .append("groupId", getGroupId())
            .append("dbDriver", getDbDriver())
            .append("ip", getIp())
            .append("dataBaseN", getDataBaseN())
            .append("dataBaseTableN", getDataBaseTableN())
            .append("usrn", getUsrn())
            .append("usrp", getUsrp())
            .append("registrationUrl", getRegistrationUrl())
            .append("routingTime", getRoutingTime())
            .append("pushTime", getPushTime())
            .append("dontResend", getDontResend())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
