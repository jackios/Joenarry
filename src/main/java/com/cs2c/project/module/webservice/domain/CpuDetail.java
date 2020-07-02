
package com.cs2c.project.module.webservice.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>cpuDetail complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="cpuDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="col1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="col2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="col3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="fma" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="ftma" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="idle" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="iowait" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="oma" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="system" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cpuDetail", propOrder = {
    "col1",
    "col2",
    "col3",
    "dataTime",
    "fma",
    "ftma",
    "id",
    "idle",
    "iowait",
    "oma",
    "system",
    "user"
})
public class CpuDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String col1;
    protected String col2;
    protected String col3;
//    @XmlSchemaType(name = "dateTime")
    protected Date dataTime;
    protected Float fma;
    protected Float ftma;
    protected Integer id;
    protected Float idle;
    protected Float iowait;
    protected Float oma;
    protected Float system;
    protected Float user;

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
     * 获取dataTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
//    public XMLGregorianCalendar getDataTime() {
//        return dataTime;
//    }

    /**
     * 设置dataTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
//    public void setDataTime(XMLGregorianCalendar value) {
//        this.dataTime = value;
//    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * 获取fma属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFma() {
        return fma;
    }

    /**
     * 设置fma属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFma(Float value) {
        this.fma = value;
    }

    /**
     * 获取ftma属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFtma() {
        return ftma;
    }

    /**
     * 设置ftma属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFtma(Float value) {
        this.ftma = value;
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
     * 获取idle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getIdle() {
        return idle;
    }

    /**
     * 设置idle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setIdle(Float value) {
        this.idle = value;
    }

    /**
     * 获取iowait属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getIowait() {
        return iowait;
    }

    /**
     * 设置iowait属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setIowait(Float value) {
        this.iowait = value;
    }

    /**
     * 获取oma属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getOma() {
        return oma;
    }

    /**
     * 设置oma属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setOma(Float value) {
        this.oma = value;
    }

    /**
     * 获取system属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getSystem() {
        return system;
    }

    /**
     * 设置system属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setSystem(Float value) {
        this.system = value;
    }

    /**
     * 获取user属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getUser() {
        return user;
    }

    /**
     * 设置user属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setUser(Float value) {
        this.user = value;
    }

    @Override
    public String toString() {
        return "\nCpuDetail{" +
                "col1='" + col1 + '\'' +
                ", col2='" + col2 + '\'' +
                ", col3='" + col3 + '\'' +
                ", dataTime=" + dataTime +
                ", fma=" + fma +
                ", ftma=" + ftma +
                ", id=" + id +
                ", idle=" + idle +
                ", iowait=" + iowait +
                ", oma=" + oma +
                ", system=" + system +
                ", user=" + user +
                '}';
    }
}
