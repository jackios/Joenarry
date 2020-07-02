
package com.cs2c.project.module.webservice.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * <p>diskInformation complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="diskInformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="col1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="col2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="col3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diskAvilabel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diskSize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diskUsed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diskUsedPercent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="mountPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "diskInformation", propOrder = {
    "col1",
    "col2",
    "col3",
    "diskAvilabel",
    "diskName",
    "diskSize",
    "diskUsed",
    "diskUsedPercent",
    "id",
    "mountPath"
})
public class DiskInformation implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String col1;
    protected String col2;
    protected String col3;
    protected String diskAvilabel;
    protected String diskName;
    protected String diskSize;
    protected String diskUsed;
    protected String diskUsedPercent;
    protected Integer id;
    protected String mountPath;

    /**
     * 获取col1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCol1() {
        return col1;
    }

    /**
     * 设置col1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCol1(String value) {
        this.col1 = value;
    }

    /**
     * 获取col2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCol2() {
        return col2;
    }

    /**
     * 设置col2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCol2(String value) {
        this.col2 = value;
    }

    /**
     * 获取col3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCol3() {
        return col3;
    }

    /**
     * 设置col3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCol3(String value) {
        this.col3 = value;
    }

    /**
     * 获取diskAvilabel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskAvilabel() {
        return diskAvilabel;
    }

    /**
     * 设置diskAvilabel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskAvilabel(String value) {
        this.diskAvilabel = value;
    }

    /**
     * 获取diskName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskName() {
        return diskName;
    }

    /**
     * 设置diskName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskName(String value) {
        this.diskName = value;
    }

    /**
     * 获取diskSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskSize() {
        return diskSize;
    }

    /**
     * 设置diskSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskSize(String value) {
        this.diskSize = value;
    }

    /**
     * 获取diskUsed属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskUsed() {
        return diskUsed;
    }

    /**
     * 设置diskUsed属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskUsed(String value) {
        this.diskUsed = value;
    }

    /**
     * 获取diskUsedPercent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiskUsedPercent() {
        return diskUsedPercent;
    }

    /**
     * 设置diskUsedPercent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiskUsedPercent(String value) {
        this.diskUsedPercent = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * 获取mountPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMountPath() {
        return mountPath;
    }

    /**
     * 设置mountPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMountPath(String value) {
        this.mountPath = value;
    }

}
