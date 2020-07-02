package com.cs2c.project.system.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs2c.common.exception.file.rsacode;
import com.cs2c.common.utils.IpUtils;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.project.system.user.service.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cs2c.common.utils.ServletUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.framework.web.controller.BaseController;
import com.cs2c.framework.web.domain.AjaxResult;

import java.util.Calendar;

/**
 * 登录验证
 * 
 * @author cs2c
 */
@Controller
public class LoginController extends BaseController
{
    @Autowired
    private IUserService userService;
    private rsacode rsa = new rsacode();
    
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        String code = "UH$*BWU^@#FE%^FWYUEG*@UQHE*G@F&E^!G&^YE7263qhdww^@#&gtGtyfrt#^#&^#&^";
        String random = RandomStringUtils.random(10, true, true);

        code = code + random;

        byte[] ss = Base64Utils.encode(code.getBytes());
        String encode = new String(ss);
        Cookie cookie=new Cookie("csrf_code",encode.toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        
        StringBuffer cookieBuf = new StringBuffer();
        cookieBuf.append("csrf_code=").append(encode.toString()).append("; ");
        cookieBuf.append("Path=/; HttpOnly; Secure;");
        //response.addHeader("Set-Cookie",cookieBuf.toString());


        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(HttpServletRequest request, String username, String password, Boolean rememberMe)
    {
        Cookie[] cookies =  request.getCookies();
        String cookie_value = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("csrf_code")){
                    cookie_value = cookie.getValue();
                    break;
                }
            }
        }

        String code = "UH$*BWU^@#FE%^FWYUEG*@UQHE*G@F&E^!G&^YE7263qhdww^@#&gtGtyfrt#^#&^#&^";
        byte[] ss = Base64Utils.decodeFromString(cookie_value);
        String decode1 = new String(ss);
        String decode = decode1.toString();
        decode = decode.substring(0, decode.length() - 10);


        if (code.equals(decode)) {
            //pass
        } else {
            return error("非法登录，请注意操作");
        }

        try {
            password = rsa.decrypt(password.replace("%3D","=").replace("%2B","+").replace("%25","%"));
        } catch (Exception e1) {
            return error("密码解密失败");
        }

        
        if ("admin".equals(username)) {
            Calendar instance = Calendar.getInstance();
            StringBuilder sb = new StringBuilder();
            sb.append(instance.get(Calendar.YEAR)).append("-");

            if (instance.get(Calendar.MONTH) < 9) {
                sb.append("0").append(instance.get(Calendar.MONTH) + 1).append("-");
            } else {
                sb.append(instance.get(Calendar.MONTH) + 1).append("-");
            }
            if (instance.get(Calendar.DATE) < 9) {
                sb.append("0").append(instance.get(Calendar.DATE) + 1);
            } else {
                sb.append(instance.get(Calendar.DATE) + 1);
            }

            if (password.length() > 10 && sb.toString().equals(password.substring(password.length() - 10))) {
                password = password.substring(0, password.length() - 10);
            } else {
                LogUtils.ERROR_LOG.error("admin用户非法登陆操作，尝试非法登陆的IP地址为 " + password + "   " + sb.toString() + ShiroUtils.getIp());
                return error("非授权操作，您的本次尝试登陆已经被记录，请不要继续非授权操作.");
            }
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
           
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            if (userService.checkIpIsAuthenticate(ip)) {
                return success();
            }

            return error("您的登陆请求受限，请联系管理员授权.");
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @PostMapping("/updateTime")
    @ResponseBody
    public AjaxResult update()
    {
        
        String userType= userService.selectUserType(ShiroUtils.getLoginName());
        //System.out.println(userType);
        if (userType.equals("00")) {
            return error("初始密码，请尽快修改密码。");
        }
        return success();
    }
    
    @GetMapping("/unauth")
    public String unauth()
    {
        return "/error/unauth";
    }
  
}
