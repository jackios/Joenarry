package com.cs2c.common.exception.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.framework.web.domain.AjaxResult;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.*;
import net.lingala.zip4j.util.*;

public class codezip {
    /**
     * 压缩文件且加密
     * @param src
     * @param dest
     * @param is
     * @param passwd
     */
    
    byte[] bytes = new byte[100*1024*1024]; 
    
    public void zip(String src,String dest,boolean is,String passwd){
        File srcfile=new File(src);
        //创建目标文件
        String destname = buildDestFileName(srcfile, dest);
        ZipParameters par=new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if(passwd!=null){
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipfile=new ZipFile(destname);
            if(srcfile.isDirectory()){
                if(!is){
                    File[] listFiles = srcfile.listFiles();
                    ArrayList<File> temp=new ArrayList<File>();
                    Collections.addAll(temp, listFiles);
                    zipfile.addFiles(temp, par);
                }
                zipfile.addFolder(srcfile, par);
            }else{
                zipfile.addFile(srcfile, par);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
        
        
    }
    /**
     * 目标文件名称
     * @param srcfile
     * @param dest
     * @return
     */
    public String buildDestFileName(File srcfile,String dest){
        if(dest==null){//没有给出目标路径时
            if(srcfile.isDirectory()){
                dest=srcfile.getParent()+File.separator+srcfile.getName()+".zip";
            }else{
                String filename=srcfile.getName().substring(0,srcfile.getName().lastIndexOf("."));
                dest=srcfile.getParent()+File.separator+filename+".zip";
            }
        }else{
            createPath(dest);//路径的创建
            if(dest.endsWith(File.separator)){
                String filename="";
                if(srcfile.isDirectory()){
                    filename=srcfile.getName();
                }else{
                    filename=srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest+=filename+".zip";
            }
        }
        return dest;
    }
    /**
     * 路径创建
     * @param dest
     */
    private void createPath(String dest){
        File destDir=null;
        if(dest.endsWith(File.separator)){
            destDir=new File(dest);//给出的是路径时
        }else{
            destDir=new File(dest.substring(0,dest.lastIndexOf(File.separator)));
        }
        if(!destDir.exists()){
            destDir.mkdirs();
        }
    }
    
    public  byte[] makeZip(String data, String Filename){
        //System.out.println("makezip");
        File file = new File("/usr/sbin/"+Filename);
        BufferedWriter bw = null;
        FileInputStream is = null;
        byte[] data3 = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/usr/sbin/"+Filename, true)));
            bw.write(data);
            bw.newLine();
            bw.flush();

            Calendar cal = Calendar.getInstance();
            String pass = cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
            //System.out.println(pass);
            String src="/usr/sbin/"+Filename;
            String dest="/usr/sbin/"+Filename+".zip";
            zip(src, dest, true, pass);
            file.delete();
            
            //System.out.println("makezip is ");
            is = new FileInputStream("/usr/sbin/"+Filename+".zip");  
            // 一次读多个字节  
            byte[] tempbytes = new byte[100];  
            int byteread = 0, byt = 0;   
            
            while ((byteread = is.read(tempbytes)) != -1) {  
                
                System.arraycopy(tempbytes, 0, bytes, byt, byteread);
                byt += byteread;
            }  
            data3 = new byte[byt]; 
            System.arraycopy(bytes, 0, data3, 0, byt);
            //Close the output stream  
            is.close();  
        } catch (FileNotFoundException e) {
            LogUtils.ERROR_LOG.error("创建时发生异常 " );
        } catch (IOException e) {
            LogUtils.ERROR_LOG.error("写入时发生异常 " );
        } catch (SecurityException e) {
            LogUtils.ERROR_LOG.error("文件删除时发生异常 " );
        }finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {}
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        //System.out.println(data3 == null);
        if ( data3 != null ) {
            return data3;
        }
        return null;
    }
    public static void main(String[] args) {
        codezip cod = new codezip();
        cod.makeZip("1","2");
    }
    
   public void Test(){
       String src="/home/fenghao/document/书籍类资料/Maven实战 高清扫描完整版.pdf";
       String dest="/home/fenghao/zip/maven/123.zip";
       zip(src, dest, true, "123456");
   }
}
