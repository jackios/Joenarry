
package com.cs2c.project.module.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getMemInfoResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getMemInfoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getMemInfo" type="{http://service.webservice.module.project.cs2c.com/}webServiceResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetMemInfoResponse", propOrder = {
    "getMemInfo"
})
public class GetMemInfoResponse {

    protected WebServiceResponse getMemInfo;

    /**
     * 获取getMemInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WebServiceResponse }
     *     
     */
    public WebServiceResponse getGetMemInfo() {
        return getMemInfo;
    }

    /**
     * 设置getMemInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WebServiceResponse }
     *     
     */
    public void setGetMemInfo(WebServiceResponse value) {
        this.getMemInfo = value;
    }

}
