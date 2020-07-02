package com.cs2c.project.module.webservice;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author Administrator
 * @create 2020 -06 -29 9:50
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        XMLGregorianCalendar xmlGregorianCalendar = xmlToDate(calendar.getTime());
        XMLGregorianCalendar xmlGregorianCalendar1 = xmlToDate(new Date("2019/04/02 08:39:01"));
        WebServiceInterface webServiceInterface = new WebServiceInterfaceImplService().getWebServiceInterfacePort();
        WebServiceResponse cpuInfo = webServiceInterface.getCpuInfo(xmlGregorianCalendar1, xmlGregorianCalendar, "admin", "29c67a30398638269fe600f73a054934");
        WebServiceResponse memInfo = webServiceInterface.getMemInfo(null, xmlGregorianCalendar, "admin", "29c67a30398638269fe600f73a054934");
        WebServiceResponse netInfo = webServiceInterface.getNetFlewInfo(null, xmlGregorianCalendar, "admin", "29c67a30398638269fe600f73a054934");
        WebServiceResponse diskInfo = webServiceInterface.getDiskInfo(null, "admin", "29c67a30398638269fe600f73a054934");
        WebServiceResponse ddos = webServiceInterface.getDDOSInfo("admin", "29c67a30398638269fe600f73a054934");
        WebServiceResponse fileTransferInfo = webServiceInterface.getFileTransferInfo(null, xmlGregorianCalendar, "admin", "29c67a30398638269fe600f73a054934");
        WebServiceResponse sysReqInfo = webServiceInterface.getSysRequestInfo(null, xmlGregorianCalendar, "admin", "29c67a30398638269fe600f73a054934");

        System.out.println(uncompressList(cpuInfo.getData()));
        System.out.println(uncompressList(cpuInfo.getData()).size());
        System.out.println(uncompressList(memInfo.getData()));
        System.out.println(uncompressList(memInfo.getData()).size());
        System.out.println(uncompressList(netInfo.getData()));
        System.out.println(uncompressList(netInfo.getData()).size());
        System.out.println(diskInfo);
        System.out.println();
        System.out.println(ddos);
        System.out.println();
        System.out.println(fileTransferInfo);
        System.out.println();
        System.out.println(sysReqInfo);
    }

    public static XMLGregorianCalendar xmlToDate(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }

    //UTC日期转换为ISO日期
    public Date DateToXML(XMLGregorianCalendar gc) {
        GregorianCalendar ca = gc.toGregorianCalendar();
        return ca.getTime();
    }

    /**
     * 功能说明:将byte[]数据解压成List对象于上面过程逆向
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static List uncompressList(byte[] data) throws IOException {
        List result = null;

        ByteArrayInputStream i = new ByteArrayInputStream(data);

        GZIPInputStream gzin = new GZIPInputStream(i);

        ObjectInputStream in = new ObjectInputStream(gzin);

        try {
            result = (List) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        i.close();
        gzin.close();
        in.close();
        return result;
    }

}
