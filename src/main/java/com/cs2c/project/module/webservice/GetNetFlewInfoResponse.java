
package com.cs2c.project.module.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getNetFlewInfoResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getNetFlewInfoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getNetFlewInfo" type="{http://service.webservice.module.project.cs2c.com/}webServiceResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetNetFlewInfoResponse", propOrder = {
    "getNetFlewInfo"
})
public class GetNetFlewInfoResponse {

    protected WebServiceResponse getNetFlewInfo;

    /**
     * 获取getNetFlewInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WebServiceResponse }
     *     
     */
    public WebServiceResponse getGetNetFlewInfo() {
        return getNetFlewInfo;
    }

    /**
     * 设置getNetFlewInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceResponse }
     *     
     */
    public void setGetNetFlewInfo(WebServiceResponse value) {
        this.getNetFlewInfo = value;
    }

}
