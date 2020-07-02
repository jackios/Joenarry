package com;


import com.cs2c.framework.shiro.service.PasswordService;
import com.cs2c.project.system.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cs2c.common.utils.security.ShiroUtils.getUser;

@Slf4j
public class MainTest {


    public static void main(String[] args) {

        //log.info("username:"+(SecurityUtils.getSubject().getPrincipal()));  //6601f3b3125b95be54a3cf714aa8b206   /  29c67a30398638269fe600f73a054934

       /* String name=(String)SecurityUtils.getSubject().getPrincipal();
        User user=new User();
        user.setUserName(name);
        user.getPassword();*/
        // new Md5Hash(username + password + salt).toHex().toString();

       //System.out.println(new PasswordService().encryptPassword("admin", "admin123", "111111"));
        log.info("" +new Md5Hash("admin"+"admin123"+111111).toHex().toString());


        User user = getUser();
        log.error("user  ......."+user);


    }
}
