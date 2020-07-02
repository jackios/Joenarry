package com.cs2c.project.module.proxy.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.bind.annotation.RequestParam;

import com.cs2c.framework.web.domain.BaseEntity;

import net.sf.jsqlparser.statement.update.Update;

/**
 * 代理配置表 proc_proxy
 * 
 * @author Joenas
 * @date 2020-04-26
 */
public class Proxy extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;
	/** 唯一标识 */
	private String serviceId;
	/** 服务名称 */
	private String serviceName;
	/** 支持的操作方法 */
	private String method;
	/** 支持的访问协议 */
	private String protocol;
	/** 服务主机名 */
	private String host;
	/** 允许的主机IP列表 */
	private String allows;
	/** 服务地址路径 */
	private String path;
	/** 服务端口 */
	private Integer port;
	/** 访问元素类型 */
	private String elementType;
	/** IN文件路径 */
	private String elementTypeFileIn;
	/** 启用IN文件 */
	private String elementTypeCheckIn;
	/** OUT文件路径 */
	private String elementTypeFileOut;
	/** 启用OUT文件 */
	private String elementTypeCheckOut;
	/** 是否有附件 */
	private String attachment;
	/** 访问元素文件名字段 */
	private String attachmentFn;
	/** 附件内容字段 */
	private String attachmentCon;
	/** 回传可视化 */
	private String reqBackgate;
	/** 请求头信息 */
	private String reqHeader;
	/** 筛选头信息 */
	private String reqHeaderC1;
	/** 筛选头信息 */
	private String reqHeaderC2;
	/** URL锁定开关 */
	private String reqUrllock;
	/** URL锁定地址 */
	private String reqPath;
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
	public void setServiceId(String serviceId) 
	{
		this.serviceId = serviceId;
	}

	public String getServiceId() 
	{
		return serviceId;
	}
	public void setServiceName(String serviceName) 
	{
		this.serviceName = serviceName;
	}

	public String getServiceName() 
	{
		return serviceName;
	}
	public void setMethod(String method) 
	{
		this.method = method;
	}

	public String getMethod() 
	{
		return method;
	}
	public void setProtocol(String protocol) 
	{
		this.protocol = protocol;
	}

	public String getProtocol() 
	{
		return protocol;
	}
	public void setHost(String host) 
	{
		this.host = host;
	}

	public String getHost() 
	{
		return host;
	}
	public void setAllows(String allows) 
	{
		this.allows = allows;
	}

	public String getAllows() 
	{
		return allows;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}

	public String getPath() 
	{
		return path;
	}
	public void setPort(Integer port) 
	{
		this.port = port;
	}

	public Integer getPort() 
	{
		return port;
	}
	public void setElementType(String elementType) 
	{
		this.elementType = elementType;
	}

	public String getElementType() 
	{
		return elementType;
	}
	public void setElementTypeFileIn(String elementTypeFileIn) 
	{
		this.elementTypeFileIn = elementTypeFileIn;
	}

	public String getElementTypeFileIn() 
	{
		return elementTypeFileIn;
	}
	public void setElementTypeCheckIn(String elementTypeCheckIn) 
	{
		this.elementTypeCheckIn = elementTypeCheckIn;
	}

	public String getElementTypeCheckIn() 
	{
		return elementTypeCheckIn;
	}
	public void setElementTypeFileOut(String elementTypeFileOut) 
	{
		this.elementTypeFileOut = elementTypeFileOut;
	}

	public String getElementTypeFileOut() 
	{
		return elementTypeFileOut;
	}
	public void setElementTypeCheckOut(String elementTypeCheckOut) 
	{
		this.elementTypeCheckOut = elementTypeCheckOut;
	}

	public String getElementTypeCheckOut() 
	{
		return elementTypeCheckOut;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}

	public String getAttachment() 
	{
		return attachment;
	}
	public void setAttachmentFn(String attachmentFn) 
	{
		this.attachmentFn = attachmentFn;
	}

	public String getAttachmentFn() 
	{
		return attachmentFn;
	}
	public void setAttachmentCon(String attachmentCon) 
	{
		this.attachmentCon = attachmentCon;
	}

	public String getAttachmentCon() 
	{
		return attachmentCon;
	}
	public void setReqBackgate(String reqBackgate) 
	{
		this.reqBackgate = reqBackgate;
	}

	public String getReqBackgate() 
	{
		return reqBackgate;
	}
	public void setReqHeader(String reqHeader) 
	{
		this.reqHeader = reqHeader;
	}

	public String getReqHeader() 
	{
		return reqHeader;
	}
	public void setReqHeaderC1(String reqHeaderC1) 
	{
		this.reqHeaderC1 = reqHeaderC1;
	}

	public String getReqHeaderC1() 
	{
		return reqHeaderC1;
	}
	public void setReqHeaderC2(String reqHeaderC2) 
	{
		this.reqHeaderC2 = reqHeaderC2;
	}

	public String getReqHeaderC2() 
	{
		return reqHeaderC2;
	}
	public void setReqUrllock(String reqUrllock) 
	{
		this.reqUrllock = reqUrllock;
	}

	public String getReqUrllock() 
	{
		return reqUrllock;
	}
	public void setReqPath(String reqPath) 
	{
		this.reqPath = reqPath;
	}

	public String getReqPath() 
	{
		return reqPath;
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
            .append("serviceId", getServiceId())
            .append("serviceName", getServiceName())
            .append("method", getMethod())
            .append("protocol", getProtocol())
            .append("host", getHost())
            .append("allows", getAllows())
            .append("path", getPath())
            .append("port", getPort())
            .append("attachment", getAttachment())
            .append("elementType", getElementType())
            .append("elementTypeFileIn", getElementTypeFileIn())
            .append("elementTypeCheckIn", getElementTypeCheckIn())
            .append("elementTypeFileOut", getElementTypeFileOut())
            .append("elementTypeCheckOut", getElementTypeCheckOut())
            .append("attachmentFn", getAttachmentFn())
            .append("attachmentCon", getAttachmentCon())
            .append("reqHeader", getReqHeader())
            .append("reqUrllock", getReqUrllock())
            .append("reqPath", getReqPath())
            .append("col1", getCol1())
            .append("col2", getCol2())
            .append("col3", getCol3())
            .toString();
    }
    
    public Proxy() {
    	
    }
    
    public Proxy(ViewProxy viewProxy, String method, String protocol, String element_type){
    	Update(viewProxy, method, protocol, element_type);
    }
    
    public void Update(ViewProxy viewProxy, String method,String protocol, String element_type) {
    	this.id = viewProxy.getId();
    	this.serviceId = viewProxy.getServiceId();
    	this.serviceName = viewProxy.getServiceName();
    	this.method = method;
    	this.protocol = protocol;
    	this.host = viewProxy.getHost();
    	this.allows = viewProxy.getAllows();
    	this.path = viewProxy.getPath();
    	this.port = viewProxy.getPort();
    	this.elementType = element_type;
		if ("on".equalsIgnoreCase(viewProxy.getAttachment())) {
			this.attachment = "yes";
		} else {
			this.attachment = "no";
		}
    	this.attachmentFn = viewProxy.getAttachment_fn();
    	this.attachmentCon = viewProxy.getAttachment_con();

    	this.reqBackgate = viewProxy.getReqBackgate();
    	
		if ("on".equalsIgnoreCase(viewProxy.getElement_type_check_in())) {
			this.elementTypeCheckIn = "yes";
		} else {
			this.elementTypeCheckIn = "no";
		}
		if ("on".equalsIgnoreCase(viewProxy.getElement_type_check_out())) {
			this.elementTypeCheckOut = "yes";
		} else {
			this.elementTypeCheckOut = "no";
        }
		if ("on".equalsIgnoreCase(viewProxy.getReqUrllock())) {
			this.reqUrllock = "yes";
		} else {
			this.reqUrllock = "no";
        }
		
		this.reqHeader = viewProxy.getReqHeader();
    	this.reqHeaderC1 = viewProxy.getReqHeaderC1();
    	this.reqHeaderC2 = viewProxy.getReqHeaderC2();
		this.reqPath = viewProxy.getReqPath();
    }
}

















