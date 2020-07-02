
package com.cs2c.project.module.webservice;

import com.cs2c.project.module.webservice.domain.DiskInformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getDiskInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getDiskInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="diskInformation" type="{http://service.webservice.module.project.cs2c.com/}diskInformation" minOccurs="0"/&gt;
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDiskInfo", propOrder = {
    "diskInformation",
    "username",
    "password"
})
public class GetDiskInfo {

    protected DiskInformation diskInformation;
    protected String username;
    protected String password;

    /**
     * 获取diskInformation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DiskInformation }
     *     
     */
    public DiskInformation getDiskInformation() {
        return diskInformation;
    }

    /**
     * 设置diskInformation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DiskInformation }
     *     
     */
    public void setDiskInformation(DiskInformation value) {
        this.diskInformation = value;
    }

    /**
     * 获取username属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置username属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * 获取password属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}