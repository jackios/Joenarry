
package com.cs2c.project.module.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getSysRequestInfoResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getSysRequestInfoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getSysRequestInfo" type="{http://service.webservice.module.project.cs2c.com/}webServiceResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetSysRequestInfoResponse", propOrder = {
    "getSysRequestInfo"
})
public class GetSysRequestInfoResponse {

    protected WebServiceResponse getSysRequestInfo;

    /**
     * 获取getSysRequestInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WebServiceResponse }
     *     
     */
    public WebServiceResponse getGetSysRequestInfo() {
        return getSysRequestInfo;
    }

    /**
     * 设置getSysRequestInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceResponse }
     *     
     */
    public void setGetSysRequestInfo(WebServiceResponse value) {
        this.getSysRequestInfo = value;
    }

}
