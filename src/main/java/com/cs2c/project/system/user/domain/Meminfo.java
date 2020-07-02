package com.cs2c.project.system.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 内存表 proc_meminfo
 * 
 * @author Joenas
 * @date 2018-10-10
 */
public class Meminfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 总内存 */
	private Float total;
	/** 被使用的内存 */
	private Float used;
	/** 剩余内存 */
	private Float free;
	/** 共享内存 */
	private Float shared;
	/** buff和cache */
	private Float buffCache;
	/** 可用内存 */
	private Float avaliable;
	/** 数据时间戳 */
	private Date dataTime;
	/** 数据单位 */
	private String suffix;
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
	public void setTotal(Float total) 
	{
		this.total = total;
	}

	public Float getTotal() 
	{
		return total;
	}
	public void setUsed(Float used) 
	{
		this.used = used;
	}

	public Float getUsed() 
	{
		return used;
	}
	public void setFree(Float free) 
	{
		this.free = free;
	}

	public Float getFree() 
	{
		return free;
	}
	public void setShared(Float shared) 
	{
		this.shared = shared;
	}

	public Float getShared() 
	{
		return shared;
	}
	public void setBuffCache(Float buffCache) 
	{
		this.buffCache = buffCache;
	}

	public Float getBuffCache() 
	{
		return buffCache;
	}
	public void setAvaliable(Float avaliable) 
	{
		this.avaliable = avaliable;
	}

	public Float getAvaliable() 
	{
		return avaliable;
	}
	public void setDataTime(Date dataTime) 
	{
		this.dataTime = dataTime;
	}

	public Date getDataTime() 
	{
		return dataTime;
	}
	public void setSuffix(String suffix) 
	{
		this.suffix = suffix;
	}

	public String getSuffix() 
	{
		return suffix;
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
            .append("total", getTotal())
            .append("used", getUsed())
            .append("free", getFree())
            .append("shared", getShared())
            .append("buffCache", getBuffCache())
            .append("avaliable", getAvaliable())
            .append("dataTime", getDataTime())
            .append("suffix", getSuffix())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
