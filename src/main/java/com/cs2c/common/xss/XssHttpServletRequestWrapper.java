package com.cs2c.common.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.security.ShiroUtils;

/**
 * XSS过滤处理
 * 
 * @author cs2c
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
{

    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request)
    {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name)
    {
        String[] values = super.getParameterValues(name);
        if (values != null)
        {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++)
            {
                
                // 防xss攻击和过滤前后空格
                escapseValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
                //System.out.println("xss :"+escapseValues[i]);
                if (escapseValues[i].contains("&amp;") || escapseValues[i].contains("&lt;") ||   //< , > , &
                        escapseValues[i].contains("&gt;") || escapseValues[i].contains("\"") ||
                        escapseValues[i].contains("\'") || escapseValues[i].contains("?") || //escapseValues[i].contains(";") ||
                        //escapseValues[i].contains("=") ||
                        escapseValues[i].contains("+")) {
                    LogUtils.ERROR_LOG.warn("信息包含危险字符（<>&\"\'?:+），已屏蔽。原信息段 :" + escapseValues[i]);
                    escapseValues[i] = "";
                    
                }
                //System.out.println("xss2 :"+escapseValues[i]);
            }
            
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}