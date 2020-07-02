package com.cs2c.project.monitor.job.task;

import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.framework.config.JoenasConfig;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务调度测试
 * 
 * @author cs2c
 */
@Component("securitySlot")
public class SecuritySlotTask
{
    @Autowired
    private JoenasConfig joenasConfig;
    // 清理文件 和 目录
    public void securitySlot(String params) {
        Map<String, String> paramMap = parseParams(params);

        // 指定要清理的日志文件 或 目录
        String logPath = paramMap.get("path");

        // 0 => 删除指定的文件夹或文件
        // 1 => 清空文件内容， 如果是文件夹， 则清空文件夹下文件的内容
        // 2 => 按照文件大小进行清空， 如果指定的是文件夹， 则对文件夹下所有文件按照此策略执行
        String type = paramMap.get("type");

        // 如果type为2的时候此配置才有效， 指定日志文件的最大大小
        long size = 0;
        try {
           size = Long.parseLong(paramMap.get("maxSize"));
        } catch (NumberFormatException e) {
            LogUtils.ERROR_LOG.error("指定的最大文件大小参数错误，默认为MB， 指定该参数时， 不要附带参数单位, params = " + params, e);
        }

        LogUtils.ERROR_LOG.error("logpath = " + logPath + "; type = " + type + "; size = " + size);

        // 指定保留的历史日志数量， 如果指定的是目录， 则对目录下所有文件有效
        // 备份的日志的命名为 <filename>-<timestamp>
        //String histSize = paramMap.get("history");

        // 如果指定的路径不存在则直接退出
        File log = new File(logPath);
        if (!log.exists()) {
            LogUtils.ERROR_LOG.error("指定的操作目标文件或目录不存在, 目标文件为 " + logPath);
            return;
        }

        // 如果指定的路径存在
        if (log.isDirectory()) {
            clearDir(log, type, size);
        } else if (log.isFile()) {
            clearFile(log, type, size);
        } else {
            LogUtils.ERROR_LOG.warn("指定的操作目标文件的格式不被支持， 只支持目录 或 文件");
        }
    }

    private Boolean allowOperation(File file, String type) {
        return true;
    }

    private void clearDir(File file, String type, long size) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                clearFile(f, type, size);
            } else if(f.isDirectory()){
                clearDir(f, type, size);
            }
        }
    }

    private void clearFile(File file, String type, long size) {
        if (!file.canWrite()) {
            LogUtils.ERROR_LOG.error("清理指定的文件时， 没有权限操作指定的文件, 文件为 " + file.getAbsolutePath());
            return;
        }

        if ("0".equals(type)) {
            if (allowOperation(file, type)) {
                file.delete();
            } else {
                LogUtils.ERROR_LOG.warn("清理指定的日志文件时，当前不具备清理条件, 文件为 " + file.getAbsolutePath());
            }
        } else if ("1".equals(type)) {
            if (allowOperation(file, type)) {
                emptyFile(file);
            } else {
                LogUtils.ERROR_LOG.warn("清空指定的日志文件时，当前不具备清理条件, 文件为 " + file.getAbsolutePath());
            }
        } else if ("2".equals(type)) {
            if (allowOperation(file, type)) {
                long currentSize = file.length();
                if (currentSize < size * 1024 * 1024) {
                    return;
                }

                emptyFile(file);
            } else {
                LogUtils.ERROR_LOG.warn("清空指定的日志文件时，当前不具备清理条件, 文件为 " + file.getAbsolutePath());
            }
        } else {
            LogUtils.ERROR_LOG.error("当清空指定的文件时， 传递的清空类型参数不正确, 当前参数为 " + type);
        }
    }


    private void emptyFile(File file) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            LogUtils.ERROR_LOG.error("清空指定的日志文件时，发生异常 ", e);
        }
    }

    public void autoClear() {
        String paths = joenasConfig.getGarbageFOD();
        String limitPerc = joenasConfig.getLimitPerc();

        if (paths == null) {
            LogUtils.ERROR_LOG.error("自动清理垃圾文件失败, 指定的清理文件/路径为空");
            return ;
        }

        int defaultLimitPerc = 80;
        try {
            defaultLimitPerc = Integer.parseInt(limitPerc);
        } catch (NumberFormatException e) {
            LogUtils.ERROR_LOG.error("配置文件中配置的存储空间使用率参数值非法", e);
            defaultLimitPerc = 80;
        }

        String[] files = paths.split("%26");
        for (String file : files) {
            if ("".equals(file.trim())) {
                continue;
            }

            File f = new File(file);
            if (!f.exists()) {
                continue;
            }

            String checkCommand = "/usr/bin/df -Th \"" + file + "\"";
            CommandLine commandLine = CommandLine.parse(checkCommand); //new CommandLine(checkCommand);
            try {
                Map<String, String> outputs = ShellUtils.runAndGetOutput(commandLine, 30000 /*30000ms = 30s*/, true);
                String stdout = outputs.get("stdout");
                String stderr = outputs.get("stderr");

                if (stderr == null || StringUtils.isNotEmpty(stderr) || StringUtils.isEmpty(stdout)) {
                    LogUtils.ERROR_LOG.error("检查磁盘空间时，命令执行异常, stderr => " + stderr + "; stdout => " + stdout);
                    return;
                }
                String[] lines = stdout.split("\n");
                if (lines.length != 2) {
                    LogUtils.ERROR_LOG.error("检查磁盘空间时，命令执行异常, stderr => " + stderr + "; stdout => " + stdout);
                    return;
                }

                String[] currentPercStr = lines[1].split("\\s+");
                if (currentPercStr.length == 7) {
                    String perc = currentPercStr[5];
                    int currentPerc = Integer.MAX_VALUE;
                    try {
                        currentPerc = Integer.parseInt(perc.substring(0, perc.length() - 1));
                        if (defaultLimitPerc > currentPerc) {
                            return;
                        }
                    } catch (NumberFormatException e) {
                        LogUtils.ERROR_LOG.error("检查字盘空间，分析结果时发生异常, stdout => " + stdout, e);
                        return;
                    }
                } else {
                    LogUtils.ERROR_LOG.error("检查字盘空间，分析结果时发生异常, stdout => " + stdout);
                    return;
                }
            } catch (IOException e) {
                LogUtils.ERROR_LOG.error("检查磁盘空间时，检查命令执行时发生异常, 请排查问题, 本次任务未做任何处理", e);
                return;
            }

            if (f.isFile()) {
                try {
                    new FileOutputStream(f).write(null);
                } catch (FileNotFoundException e){
                    LogUtils.ERROR_LOG.error("清理垃圾文件时，指定的文件不存在", e);
                } catch (IOException e) {
                    LogUtils.ERROR_LOG.error("清理垃圾文件时，写入异常", e);
                }
            } else if (f.isDirectory()) {
                try {
                    File[] subFiles = f.listFiles();
                    for (File ff : subFiles) {
                        if (ff.isFile()) {
                            new FileOutputStream(ff).write(null);
                        }
                    }
                } catch (SecurityException e) {
                    LogUtils.ERROR_LOG.error("清理垃圾文件时，对指定的目标文件/目录 " + f.getAbsolutePath() + " 没有操作权限", e);
                    return;
                } catch (FileNotFoundException e){
                    LogUtils.ERROR_LOG.error("清理垃圾文件时，指定的文件不存在", e);
                } catch (IOException e) {
                    LogUtils.ERROR_LOG.error("清理垃圾文件时，写入异常", e);
                }
            } else {
                LogUtils.ERROR_LOG.error("清理垃圾文件时，指定了不支持的文件类型");
                return;
            }
        }
    }

    private Map<String, String> parseParams(String params) {
        Map<String, String > result = new HashMap<>();

        String splitFlag = ";";

        if (params.indexOf(";") == 0) {
            params = params.substring(2);
        } else {

            String[] paramArray = params.split(";");

            // 获取分割符号
            if (paramArray.length > 0) {
                String[] kk = paramArray[0].split("=");
                if (kk.length != 2 || (!"splitFlag".equals(kk[0]) || StringUtils.isEmpty(kk[1]))) {
                    LogUtils.ERROR_LOG.error("调度任务的参数传递错误");
                    return result;
                }

                splitFlag = kk[1];
            }


            // 去除splitFlag参数， 开始实际解析后面的参数
            params = params.substring(params.indexOf(";"));
        }
        String[] paramArray = params.split(splitFlag);
        for (String param : paramArray) {
            String[] kv = param.split("=");
            if (kv.length != 2)
                continue;

            result.put(kv[0], kv[1]);
        }

        return result;
    }

}
