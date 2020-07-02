/*package com.cs2c.project.module.syDS.service;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import com.cs2c.project.module.syDS.domain.doSyDS;
import com.cs2c.common.utils.os.ShellUtils;
public class test {
    
    @Autowired
    static doSyDS DS = new doSyDS();
    
    public static void main(String[] args) {
        boolean flag = false; 
        int port = 1;
        while(!flag) {
            try {  
                
                Socket socket = new Socket("127.0.0.1",port);  //建立一个Socket连接
                flag = true;  
                socket.close();
            } catch (IOException e) {  
    
            }  finally {
                System.out.println("port at :"+port +"is"+ !flag +"inused");
            }
            port++;  
        }
        
        System.out.println("DS test is :"+DS.toString());
        String command = "/home/fu/workspace/Joenarry/Joenarry/syDS/bin/sym --engine corp-000 --port 8080";
        //CommandLine commandLine = CommandLine.parse(command);
        System.out.println("asdasd");
        System.out.println(DS.getGoing());
        DS.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        System.out.println("DS test 2 is :"+DS.getGoing());
        //DS.interrupt();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("sleep error");
        }
        System.out.println("DS test 3 is :"+DS.getGoing());
    }

}*/















