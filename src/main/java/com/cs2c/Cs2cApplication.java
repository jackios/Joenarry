package com.cs2c;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 启动程序
 * 
 * @author cs2c
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class , SecurityAutoConfiguration.class})//
@MapperScan("com.cs2c.project.*.*.mapper")
public class Cs2cApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Cs2cApplication.class, args);


        System.out.println("(♥◠‿◠)ﾉﾞ  安全传输系统启动成功   ლ(´ڡ`ლ)ﾞ  \n"
                + "\n"
                + "                                        ,s555SB@@&\n"
                + "                                      :9H####@@@@@Xi\n"
                + "                                     1@@@@@@@@@@@@@@8\n"
                + "                                  ,8@@@@@@@@@B@@@@@@8\n"
                + "                                  :B@@@@X3hi8Bs;B@@@@@Ah,\n"
                + "             ,8i                  r@@@B:     1S ,M@@@@@@#8;\n"
                + "            1AB35.i:               X@@8 .   SGhr ,A@@@@@@@@S\n"
                + "            1@h31MX8                18Hhh3i .i3r ,A@@@@@@@@@5\n"
                + "            ;@&i,58r5                 rGSS:     :B@@@@@@@@@@A\n"
                + "             1#i  . 9i                 hX.  .: .5@@@@@@@@@@@1\n"
                + "              sG1,  ,G53s.              9#Xi;hS5 3B@@@@@@@B1\n"
                + "               .h8h.,A@@@MXSs,           #@H1:    3ssSSX@1\n"
                + "               s ,@@@@@@@@@@@@Xhi,       r#@@X1s9M8    .GA981\n"
                + "               ,. rS8H#@@@@@@@@@@#HG51;.  .h31i;9@r    .8@@@@BS;i;\n"
                + "                .19AXXXAB@@@@@@@@@@@@@@#MHXG893hrX#XGGXM@@@@@@@@@@MS\n"
                + "                s@@MM@@@hsX#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&,\n"
                + "              :GB@#3G@@Brs ,1GM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@B,\n"
                + "            .hM@@@#@@#MX 51  r;iSGAM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@8\n"
                + "          :3B@@@@@@@@@@@&9@h :Gs   .;sSXH@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:\n"
                + "      s&HA#@@@@@@@@@@@@@@M89A;.8S.       ,r3@@@@@@@@@@@@@@@@@@@@@@@@@@@r\n"
                + "   ,13B@@@@@@@@@@@@@@@@@@@5 5B3 ;.         ;@@@@@@@@@@@@@@@@@@@@@@@@@@@i\n"
                + "  5#@@#&@@@@@@@@@@@@@@@@@@9  .39:          ;@@@@@@@@@@@@@@@@@@@@@@@@@@@;\n"
                + "  9@@@X:MM@@@@@@@@@@@@@@@#;    ;31.         H@@@@@@@@@@@@@@@@@@@@@@@@@@:\n"
                + "   SH#@B9.rM@@@@@@@@@@@@@B       :.         3@@@@@@@@@@@@@@@@@@@@@@@@@@5\n"
                + "     ,:.   9@@@@@@@@@@@#HB5                 .M@@@@@@@@@@@@@@@@@@@@@@@@@B\n"
                + "           ,ssirhSM@&1;i19911i,.             s@@@@@@@@@@@@@@@@@@@@@@@@@@S\n"
                + "              ,,,rHAri1h1rh&@#353Sh:          8@@@@@@@@@@@@@@@@@@@@@@@@@#:\n"
                + "            .A3hH@#5S553&@@#h   i:i9S          #@@@@@@@@@@@@@@@@@@@@@@@@@A.\n"
                + "          \n"
                + "          \n"
                + "    你瞅啥！\n"
                + "    你瞅啥！\n"
                + "    你瞅啥！\n");

    }
}