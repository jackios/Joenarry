package com.cs2c.common.utils.os;

import com.cs2c.common.utils.LogUtils;
import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ShellUtils {

    private static Map<String, String> environment = new HashMap<>();
    static {
        environment.put("LANG", "c");
    }

    public class ShellResultHandler extends DefaultExecuteResultHandler {

        private ExecuteWatchdog watchdog;

        public ShellResultHandler(final ExecuteWatchdog watchdog) {
            this.watchdog = watchdog;
        }

        public ShellResultHandler(final int exitValue) {
            super.onProcessComplete(exitValue);
        }


        @Override
        public void onProcessComplete(final int exitValue) {
            super.onProcessComplete(exitValue);
            LogUtils.ACCESS_LOG.info("Shell命令执行成功");
        }

        @Override
        public void onProcessFailed(final ExecuteException e) {
            super.onProcessFailed(e);
            if (watchdog != null && watchdog.killedProcess()) {
                LogUtils.ERROR_LOG.error("Shell命令执行超时");
            } else {
                LogUtils.ERROR_LOG.error("Shell命令执行失败", e);
                System.err.println("[resultHandler] The print process failed to do : " + e.getMessage());
            }
        }
    }

    public static ShellResultHandler run(final CommandLine commandLine, final long jobTimeout /*ms*/, final boolean jobInBackground)
            throws IOException {
        int exitValue;
        ExecuteWatchdog watchdog = null;
        ShellResultHandler resultHandler;

        // 退出1 代表 成功
        final Executor executor = new DefaultExecutor();
        executor.setExitValue(1);

        // 如果设置了timeout，则创建watchdog
        if (jobTimeout > 0) {
            watchdog = new ExecuteWatchdog(jobTimeout);
            executor.setWatchdog(watchdog);
        }

        // 非阻塞执行， 即后台执行
        if (jobInBackground) {
           //System.out.println("[print] Executing non-blocking  job  ...");
            resultHandler = new ShellUtils().new ShellResultHandler(watchdog);
            executor.execute(commandLine, environment, resultHandler);
        }
        // 阻塞执行
        else {
            //System.out.println("[print] Executing blocking  job  ...");
            exitValue = executor.execute(commandLine);
            resultHandler = new ShellUtils().new ShellResultHandler(exitValue);
        }

        return resultHandler;
    }

    public static Map<String, String> runAndGetOutput(final CommandLine commandLine, final long jobTimeout /*ms*/, final boolean jobInBackground)
            throws IOException {
        //接收正常结果流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValues(null);

        //设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(jobTimeout);
        exec.setWatchdog(watchdog);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);
        exec.setStreamHandler(streamHandler);

        exec.execute(commandLine, environment);
        //不同操作系统注意编码，否则结果乱码
        HashMap<String, String> result = new HashMap<>();
        result.put("stdout", outputStream.toString("UTF-8"));
        result.put("stderr", errorStream.toString("UTF-8"));

        return result;
    }
}
