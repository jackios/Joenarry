package com.cs2c.project.module.proxy.domain;

import java.util.List;

public class ViewProxy {
	
	private int id;

	private String serviceId; // 服务ID
	private String serviceName; // 服务名称
	private List<Method> methods; // 请求方法
	private List<Protocol> protocols; // 请求协议
	private String host; // 服务主机
	private String allows; // 授权主机
	private String path; // ——弃用
	private Integer port; // 端口
	
	private List<Element_Type> element_types; // 访问元素类型
	private String element_type_check_in; // 是否启用传入校验
	private String element_type_check_out; // 是否启用传出校验

	private String reqBackgate; // 回传可视化
	private String reqHeader; // 请求头信息
	private String reqHeaderC1; // 筛选头信息1
	private String reqHeaderC2; // 	筛选头信息2

	private String attachment; // 是否有附件
	private String attachment_fn; // 附件名字段
	private String attachment_con; // 附件内容字段

	private String reqUrllock; // URL锁定开关
	private String reqPath; // URL锁定地址

	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	
	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public List<Protocol> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<Protocol> protocols) {
		this.protocols = protocols;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAllows() {
		return allows;
	}

	public void setAllows(String allows) {
		this.allows = allows;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	public List<Element_Type> getElement_types() {
		return element_types;
	}

	public void setElement_types(List<Element_Type> element_types) {
		this.element_types = element_types;
	}

	public String getElement_type_check_in() {
		return element_type_check_in;
	}

	public void setElement_type_check_in(String element_type_check) {
		this.element_type_check_in = element_type_check;
	}

	public String getElement_type_check_out() {
		return element_type_check_out;
	}

	public void setElement_type_check_out(String element_type_check) {
		this.element_type_check_out = element_type_check;
	}
	
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getAttachment_fn() {
		return attachment_fn;
	}

	public void setAttachment_fn(String attachment_fn) {
		this.attachment_fn = attachment_fn;
	}

	public String getAttachment_con() {
		return attachment_con;
	}

	public void setAttachment_con(String attachment_con) {
		this.attachment_con = attachment_con;
	}
	
	public void setReqBackgate(String reqBackgate) 
	{
		this.reqBackgate = reqBackgate;
	}

	public String getReqBackgate() 
	{
		return reqBackgate;
	}
	
	public String getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(String ReqHeader) {
		this.reqHeader = ReqHeader;
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

	public String getReqUrllock() {
		return reqUrllock;
	}

	public void setReqUrllock(String reqUrllock) {
		this.reqUrllock = reqUrllock;
	}

	public String getReqPath() {
		return reqPath;
	}

	public void setReqPath(String reqPath) {
		this.reqPath = reqPath;
	}

	@Override
	public String toString() {
		return "ViewProxy{" + "id=" + id + ", serviceId='" + serviceId + '\'' + ", serviceName='" + serviceName + '\''
				+ ", methods=" + methods + ", protocols=" + protocols + ", host='" + host + '\'' + ", allows='" + allows
				+ '\'' + ", path='" + path + '\'' + ", port=" + port + ", attachment='" + attachment + '\''
				+ ", element_type='" + element_types + '\'' + ", element_type_check_in='" + element_type_check_in + '\''
				+ ", element_type_check_out='" + element_type_check_out + '\'' + ", attachment_fn='" + attachment_fn
				+ '\'' + ", attachment_con='" + attachment_con + '\'' + '}';
	}

	public ViewProxy() {
	}

	public ViewProxy(Proxy proxy, List<Method> methods, List<Protocol> protocols, List<Element_Type> element_type) {
		this.id = proxy.getId();
		this.serviceId = proxy.getServiceId();
		this.serviceName = proxy.getServiceName();
		this.methods = methods;
		this.protocols = protocols;
		this.host = proxy.getHost();
		this.allows = proxy.getAllows();
		this.path = proxy.getPath();
		this.port = proxy.getPort();

		this.reqBackgate = proxy.getReqBackgate();
		
		this.element_types = element_type;
		this.element_type_check_in = proxy.getElementTypeCheckIn();
		this.element_type_check_out = proxy.getElementTypeCheckOut();

		this.attachment = proxy.getAttachment();
		this.attachment_fn = proxy.getAttachmentFn();
		this.attachment_con = proxy.getAttachmentCon();

		this.reqHeader = proxy.getReqHeader();
		this.reqHeaderC1 = proxy.getReqHeaderC1();
		this.reqHeaderC2 = proxy.getReqHeaderC2();
		this.reqUrllock = proxy.getReqUrllock();
		this.reqPath = proxy.getReqPath();
	}

//	public ViewProxy(int id, String serviceId, String serviceName, List<Method> methods, List<Protocol> protocols,
//			String host, String allows, String path, Integer port, String attachment, List<Element_Type> element_type,
//			String element_type_check_in, String element_type_check_out, String attachment_fn, String attachment_con,
//			String reqHeader, String reqUrllock, String reqPath) {
//		this.id = id;
//		this.serviceId = serviceId;
//		this.serviceName = serviceName;
//		this.methods = methods;
//		this.protocols = protocols;
//		this.host = host;
//		this.allows = allows;
//		this.path = path;
//		this.port = port;
//		this.attachment = attachment;
//		this.element_types = element_type;
//		this.element_type_check_in = element_type_check_in;
//		this.element_type_check_out = element_type_check_out;
//		this.attachment_fn = attachment_fn;
//		this.attachment_con = attachment_con;
//		this.reqHeader = reqHeader;
//		this.reqUrllock = reqUrllock;
//		this.reqPath = reqPath;
//	}

}
