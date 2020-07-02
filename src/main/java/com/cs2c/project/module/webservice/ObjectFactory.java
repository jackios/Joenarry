
package com.cs2c.project.module.webservice;

import com.cs2c.project.module.webservice.domain.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cs2c.webservice.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
@XmlType(name = "ObjectFactory")
public class ObjectFactory {

    private final static QName _GetCpuInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getCpuInfo");
    private final static QName _GetCpuInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getCpuInfoResponse");
    private final static QName _GetDDOSInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getDDOSInfo");
    private final static QName _GetDDOSInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getDDOSInfoResponse");
    private final static QName _GetDiskInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getDiskInfo");
    private final static QName _GetDiskInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getDiskInfoResponse");
    private final static QName _GetFileTransferInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getFileTransferInfo");
    private final static QName _GetFileTransferInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getFileTransferInfoResponse");
    private final static QName _GetMemInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getMemInfo");
    private final static QName _GetMemInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getMemInfoResponse");
    private final static QName _GetNetFlewInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getNetFlewInfo");
    private final static QName _GetNetFlewInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getNetFlewInfoResponse");
    private final static QName _GetSysRequestInfo_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getSysRequestInfo");
    private final static QName _GetSysRequestInfoResponse_QNAME = new QName("http://service.webservice.module.project.cs2c.com/", "getSysRequestInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cs2c.project.module.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCpuInfo }
     * 
     */
    public GetCpuInfo createGetCpuInfo() {
        return new GetCpuInfo();
    }

    /**
     * Create an instance of {@link GetCpuInfoResponse }
     * 
     */
    public GetCpuInfoResponse createGetCpuInfoResponse() {
        return new GetCpuInfoResponse();
    }

    /**
     * Create an instance of {@link GetDDOSInfo }
     * 
     */
    public GetDDOSInfo createGetDDOSInfo() {
        return new GetDDOSInfo();
    }

    /**
     * Create an instance of {@link GetDDOSInfoResponse }
     * 
     */
    public GetDDOSInfoResponse createGetDDOSInfoResponse() {
        return new GetDDOSInfoResponse();
    }

    /**
     * Create an instance of {@link GetDiskInfo }
     * 
     */
    public GetDiskInfo createGetDiskInfo() {
        return new GetDiskInfo();
    }

    /**
     * Create an instance of {@link GetDiskInfoResponse }
     * 
     */
    public GetDiskInfoResponse createGetDiskInfoResponse() {
        return new GetDiskInfoResponse();
    }

    /**
     * Create an instance of {@link GetFileTransferInfo }
     * 
     */
    public GetFileTransferInfo createGetFileTransferInfo() {
        return new GetFileTransferInfo();
    }

    /**
     * Create an instance of {@link GetFileTransferInfoResponse }
     * 
     */
    public GetFileTransferInfoResponse createGetFileTransferInfoResponse() {
        return new GetFileTransferInfoResponse();
    }

    /**
     * Create an instance of {@link GetMemInfo }
     * 
     */
    public GetMemInfo createGetMemInfo() {
        return new GetMemInfo();
    }

    /**
     * Create an instance of {@link GetMemInfoResponse }
     * 
     */
    public GetMemInfoResponse createGetMemInfoResponse() {
        return new GetMemInfoResponse();
    }

    /**
     * Create an instance of {@link GetNetFlewInfo }
     * 
     */
    public GetNetFlewInfo createGetNetFlewInfo() {
        return new GetNetFlewInfo();
    }

    /**
     * Create an instance of {@link GetNetFlewInfoResponse }
     * 
     */
    public GetNetFlewInfoResponse createGetNetFlewInfoResponse() {
        return new GetNetFlewInfoResponse();
    }

    /**
     * Create an instance of {@link GetSysRequestInfo }
     * 
     */
    public GetSysRequestInfo createGetSysRequestInfo() {
        return new GetSysRequestInfo();
    }

    /**
     * Create an instance of {@link GetSysRequestInfoResponse }
     * 
     */
    public GetSysRequestInfoResponse createGetSysRequestInfoResponse() {
        return new GetSysRequestInfoResponse();
    }

    /**
     * Create an instance of {@link WebServiceResponse }
     * 
     */
    public WebServiceResponse createWebServiceResponse() {
        return new WebServiceResponse();
    }

    /**
     * Create an instance of {@link MemoryInfo }
     * 
     */
    public MemoryInfo createMemoryInfo() {
        return new MemoryInfo();
    }

    /**
     * Create an instance of {@link NetFlowInfo }
     * 
     */
    public NetFlowInfo createNetFlowInfo() {
        return new NetFlowInfo();
    }

    /**
     * Create an instance of {@link CpuDetail }
     * 
     */
    public CpuDetail createCpuDetail() {
        return new CpuDetail();
    }

    /**
     * Create an instance of {@link ServiceRequestInfo }
     * 
     */
    public ServiceRequestInfo createServiceRequestInfo() {
        return new ServiceRequestInfo();
    }

    /**
     * Create an instance of {@link FileTransferInfo }
     * 
     */
    public FileTransferInfo createFileTransferInfo() {
        return new FileTransferInfo();
    }

    /**
     * Create an instance of {@link DdosForWebService }
     * 
     */
    public DdosForWebService createDdosForWebService() {
        return new DdosForWebService();
    }

    /**
     * Create an instance of {@link DiskInformation }
     * 
     */
    public DiskInformation createDiskInformation() {
        return new DiskInformation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCpuInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCpuInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getCpuInfo")
    public JAXBElement<GetCpuInfo> createGetCpuInfo(GetCpuInfo value) {
        return new JAXBElement<GetCpuInfo>(_GetCpuInfo_QNAME, GetCpuInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCpuInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCpuInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getCpuInfoResponse")
    public JAXBElement<GetCpuInfoResponse> createGetCpuInfoResponse(GetCpuInfoResponse value) {
        return new JAXBElement<GetCpuInfoResponse>(_GetCpuInfoResponse_QNAME, GetCpuInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDDOSInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDDOSInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getDDOSInfo")
    public JAXBElement<GetDDOSInfo> createGetDDOSInfo(GetDDOSInfo value) {
        return new JAXBElement<GetDDOSInfo>(_GetDDOSInfo_QNAME, GetDDOSInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDDOSInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDDOSInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getDDOSInfoResponse")
    public JAXBElement<GetDDOSInfoResponse> createGetDDOSInfoResponse(GetDDOSInfoResponse value) {
        return new JAXBElement<GetDDOSInfoResponse>(_GetDDOSInfoResponse_QNAME, GetDDOSInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDiskInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDiskInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getDiskInfo")
    public JAXBElement<GetDiskInfo> createGetDiskInfo(GetDiskInfo value) {
        return new JAXBElement<GetDiskInfo>(_GetDiskInfo_QNAME, GetDiskInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDiskInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetDiskInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getDiskInfoResponse")
    public JAXBElement<GetDiskInfoResponse> createGetDiskInfoResponse(GetDiskInfoResponse value) {
        return new JAXBElement<GetDiskInfoResponse>(_GetDiskInfoResponse_QNAME, GetDiskInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileTransferInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetFileTransferInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getFileTransferInfo")
    public JAXBElement<GetFileTransferInfo> createGetFileTransferInfo(GetFileTransferInfo value) {
        return new JAXBElement<GetFileTransferInfo>(_GetFileTransferInfo_QNAME, GetFileTransferInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileTransferInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetFileTransferInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getFileTransferInfoResponse")
    public JAXBElement<GetFileTransferInfoResponse> createGetFileTransferInfoResponse(GetFileTransferInfoResponse value) {
        return new JAXBElement<GetFileTransferInfoResponse>(_GetFileTransferInfoResponse_QNAME, GetFileTransferInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMemInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetMemInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getMemInfo")
    public JAXBElement<GetMemInfo> createGetMemInfo(GetMemInfo value) {
        return new JAXBElement<GetMemInfo>(_GetMemInfo_QNAME, GetMemInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMemInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetMemInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getMemInfoResponse")
    public JAXBElement<GetMemInfoResponse> createGetMemInfoResponse(GetMemInfoResponse value) {
        return new JAXBElement<GetMemInfoResponse>(_GetMemInfoResponse_QNAME, GetMemInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNetFlewInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetNetFlewInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getNetFlewInfo")
    public JAXBElement<GetNetFlewInfo> createGetNetFlewInfo(GetNetFlewInfo value) {
        return new JAXBElement<GetNetFlewInfo>(_GetNetFlewInfo_QNAME, GetNetFlewInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNetFlewInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetNetFlewInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getNetFlewInfoResponse")
    public JAXBElement<GetNetFlewInfoResponse> createGetNetFlewInfoResponse(GetNetFlewInfoResponse value) {
        return new JAXBElement<GetNetFlewInfoResponse>(_GetNetFlewInfoResponse_QNAME, GetNetFlewInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSysRequestInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSysRequestInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getSysRequestInfo")
    public JAXBElement<GetSysRequestInfo> createGetSysRequestInfo(GetSysRequestInfo value) {
        return new JAXBElement<GetSysRequestInfo>(_GetSysRequestInfo_QNAME, GetSysRequestInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSysRequestInfoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSysRequestInfoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.webservice.module.project.cs2c.com/", name = "getSysRequestInfoResponse")
    public JAXBElement<GetSysRequestInfoResponse> createGetSysRequestInfoResponse(GetSysRequestInfoResponse value) {
        return new JAXBElement<GetSysRequestInfoResponse>(_GetSysRequestInfoResponse_QNAME, GetSysRequestInfoResponse.class, null, value);
    }

}
