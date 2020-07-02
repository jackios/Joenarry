package com.cs2c.project.module.transfer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cs2c.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 传输控制表 proc_transfer
 * 
 * @author Joenas
 * @date 2018-10-25
 */
public class Transfer extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 文件名 */
	private String transferFile;
	/** 开始时间 */
	private String timeStamp;
	/** 进程号 */
	private String pid;
	/** 端口号 */
	private String port;
	/** 查看传输详情 */
	private String details;
	/** 结果预览 */
	private String preview;
	/** 传输状态 */
	private String statusTransfer;
	/** 放行 */
	private String startTransfer;
	/** 取消 */
	private String breakTransfer;

	@Override
	public String toString() {
		return "Transfer{" +
				"id=" + id +
				", transferFile='" + transferFile + '\'' +
				", timeStamp='" + timeStamp + '\'' +
				", pid='" + pid + '\'' +
				", port='" + port + '\'' +
				", details='" + details + '\'' +
				", preview='" + preview + '\'' +
				", statusTransfer='" + statusTransfer + '\'' +
				", startTransfer='" + startTransfer + '\'' +
				", breakTransfer='" + breakTransfer + '\'' +
				", realDir='" + realDir + '\'' +
				'}';
	}

	public String getRealDir() {
		return realDir;
	}

	public void setRealDir(String realDir) {
		this.realDir = realDir;
	}

	/** 实际路径 */
	private String realDir;

	public Transfer(){}

	public Transfer(Integer id, String transferFile, String timeStamp, String pid, String port, String details, String preview, String statusTransfer, String startTransfer, String breakTransfer, String realDir) {
		this.id = id;
		this.transferFile = transferFile;
		this.timeStamp = timeStamp;
		this.pid = pid;
		this.port = port;
		this.details = details;
		this.preview = preview;
		this.statusTransfer = statusTransfer;
		this.startTransfer = startTransfer;
		this.breakTransfer = breakTransfer;
		this.realDir = realDir;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setTransferFile(String transferFile) 
	{
		this.transferFile = transferFile;
	}

	public String getTransferFile() 
	{
		return transferFile;
	}
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp()
	{
		return timeStamp;
	}
	public void setPid(String pid) 
	{
		this.pid = pid;
	}

	public String getPid() 
	{
		return pid;
	}
	public void setPort(String port) 
	{
		this.port = port;
	}

	public String getPort() 
	{
		return port;
	}
	public void setDetails(String details) 
	{
		this.details = details;
	}

	public String getDetails() 
	{
		return details;
	}
	public void setPreview(String preview) 
	{
		this.preview = preview;
	}

	public String getPreview() 
	{
		return preview;
	}
	public void setStatusTransfer(String statusTransfer) 
	{
		this.statusTransfer = statusTransfer;
	}

	public String getStatusTransfer() 
	{
		return statusTransfer;
	}
	public void setStartTransfer(String startTransfer) 
	{
		this.startTransfer = startTransfer;
	}

	public String getStartTransfer() 
	{
		return startTransfer;
	}
	public void setBreakTransfer(String breakTransfer) 
	{
		this.breakTransfer = breakTransfer;
	}

	public String getBreakTransfer() 
	{
		return breakTransfer;
	}

}
