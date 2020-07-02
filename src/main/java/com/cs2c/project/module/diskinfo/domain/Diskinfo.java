package com.cs2c.project.module.diskinfo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;

/**
 * 磁盘表 proc_diskinfo
 * 
 * @author Joenas
 * @date 2018-11-01
 */
public class Diskinfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 挂载点名称 */
	private String diskName;
	/** 磁盘容量 */
	private String diskSize;
	/** 已用容量 */
	private String diskUsed;
	/** 可用容量 */
	private String diskAvilabel;
	/** 已用百分比 */
	private String diskUsedPercent;
	/** 挂载点 */
	private String mountPath;
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
	public void setDiskName(String diskName) 
	{
		this.diskName = diskName;
	}

	public String getDiskName() 
	{
		return diskName;
	}
	public void setDiskSize(String diskSize) 
	{
		this.diskSize = diskSize;
	}

	public String getDiskSize() 
	{
		return diskSize;
	}
	public void setDiskUsed(String diskUsed) 
	{
		this.diskUsed = diskUsed;
	}

	public String getDiskUsed() 
	{
		return diskUsed;
	}
	public void setDiskAvilabel(String diskAvilabel) 
	{
		this.diskAvilabel = diskAvilabel;
	}

	public String getDiskAvilabel() 
	{
		return diskAvilabel;
	}
	public void setDiskUsedPercent(String diskUsedPercent) 
	{
		this.diskUsedPercent = diskUsedPercent;
	}

	public String getDiskUsedPercent() 
	{
		return diskUsedPercent;
	}
	public void setMountPath(String mountPath) 
	{
		this.mountPath = mountPath;
	}

	public String getMountPath() 
	{
		return mountPath;
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
            .append("diskName", getDiskName())
            .append("diskSize", getDiskSize())
            .append("diskUsed", getDiskUsed())
            .append("diskAvilabel", getDiskAvilabel())
            .append("diskUsedPercent", getDiskUsedPercent())
            .append("mountPath", getMountPath())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
}
