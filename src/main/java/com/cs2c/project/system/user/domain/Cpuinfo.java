package com.cs2c.project.system.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * CPU负载使用率表 proc_cpuinfo
 * 
 * @author Joenas
 * @date 2018-10-10
 */
public class Cpuinfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 一分钟负载 */
	private Float oma;
	/** 五分钟负载 */
	private Float fma;
	/** 十五分钟负载 */
	private Float ftma;
	/** 空闲 */
	private Float idle;
	/** 用户空间比例 */
	private Float user;
	/** 系统空间占用 */
	private Float system;
	/** 系统空间占用 */
	private Float iowait;
	/** 数据时间戳 */
	private Date dataTime;
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
	public void setOma(Float oma) 
	{
		this.oma = oma;
	}

	public Float getOma() 
	{
		return oma;
	}
	public void setFma(Float fma) 
	{
		this.fma = fma;
	}

	public Float getFma() 
	{
		return fma;
	}
	public void setFtma(Float ftma) 
	{
		this.ftma = ftma;
	}

	public Float getFtma() 
	{
		return ftma;
	}
	public void setIdle(Float idle) 
	{
		this.idle = idle;
	}

	public Float getIdle() 
	{
		return idle;
	}
	public void setUser(Float user) 
	{
		this.user = user;
	}

	public Float getUser() 
	{
		return user;
	}
	public void setSystem(Float system) 
	{
		this.system = system;
	}

	public Float getSystem() 
	{
		return system;
	}
	public void setIowait(Float iowait) 
	{
		this.iowait = iowait;
	}

	public Float getIowait() 
	{
		return iowait;
	}
	public void setDataTime(Date dataTime) 
	{
		this.dataTime = dataTime;
	}

	public Date getDataTime() 
	{
		return dataTime;
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
            .append("oma", getOma())
            .append("fma", getFma())
            .append("ftma", getFtma())
            .append("idle", getIdle())
            .append("user", getUser())
            .append("system", getSystem())
            .append("iowait", getIowait())
            .append("dataTime", getDataTime())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
