
package com.cs2c.project.module.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getDDOSInfoResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getDDOSInfoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getDDOSInfo" type="{http://service.webservice.module.project.cs2c.com/}webServiceResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDDOSInfoResponse", propOrder = {
    "getDDOSInfo"
})
public class GetDDOSInfoResponse {

    protected WebServiceResponse getDDOSInfo;

    /**
     * 获取getDDOSInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WebServiceResponse }
     *     
     */
    public WebServiceResponse getGetDDOSInfo() {
        return getDDOSInfo;
    }

    /**
     * 设置getDDOSInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceResponse }
     *     
     */
    public void setGetDDOSInfo(WebServiceResponse value) {
        this.getDDOSInfo = value;
    }

}
