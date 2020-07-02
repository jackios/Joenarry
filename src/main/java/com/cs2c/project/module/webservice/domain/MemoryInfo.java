
package com.cs2c.project.module.webservice.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;


/**
 * <p>memoryInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="memoryInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="avaliable" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="buffCache" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="col1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="col2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="col3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="free" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="shared" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="used" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "memoryInfo", propOrder = {
    "avaliable",
    "buffCache",
    "col1",
    "col2",
    "col3",
    "dataTime",
    "free",
    "id",
    "shared",
    "suffix",
    "total",
    "used"
})
public class MemoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Float avaliable;
    protected Float buffCache;
    protected String col1;
    protected String col2;
    protected String col3;
//    @XmlSchemaType(name = "dateTime")
    protected Date dataTime;
    protected Float free;
    protected Integer id;
    protected Float shared;
    protected String suffix;
    protected Float total;
    protected Float used;

    /**
     * 获取avaliable属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getAvaliable() {
        return avaliable;
    }

    /**
     * 设置avaliable属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setAvaliable(Float value) {
        this.avaliable = value;
    }

    /**
     * 获取buffCache属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getBuffCache() {
        return buffCache;
    }

    /**
     * 设置buffCache属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setBuffCache(Float value) {
        this.buffCache = value;
    }

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
     * 获取free属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFree() {
        return free;
    }

    /**
     * 设置free属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFree(Float value) {
        this.free = value;
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
     * 获取shared属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getShared() {
        return shared;
    }

    /**
     * 设置shared属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setShared(Float value) {
        this.shared = value;
    }

    /**
     * 获取suffix属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置suffix属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

    /**
     * 获取total属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTotal() {
        return total;
    }

    /**
     * 设置total属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTotal(Float value) {
        this.total = value;
    }

    /**
     * 获取used属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getUsed() {
        return used;
    }

    /**
     * 设置used属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setUsed(Float value) {
        this.used = value;
    }

    @Override
    public String toString() {
        return "\nMemoryInfo{" +
                "avaliable=" + avaliable +
                ", buffCache=" + buffCache +
                ", col1='" + col1 + '\'' +
                ", col2='" + col2 + '\'' +
                ", col3='" + col3 + '\'' +
                ", dataTime=" + dataTime +
                ", free=" + free +
                ", id=" + id +
                ", shared=" + shared +
                ", suffix='" + suffix + '\'' +
                ", total=" + total +
                ", used=" + used +
                '}';
    }
}
