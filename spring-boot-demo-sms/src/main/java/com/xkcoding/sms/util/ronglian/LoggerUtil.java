package com.xkcoding.sms.util.ronglian;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LoggerUtil {
    private static boolean enableLog = true;

    public static void setEnableLog(boolean enableLog) {
        LoggerUtil.enableLog = enableLog;
    }

    public static void debug(String msg) {
        if (enableLog)
            log.debug(msg);
    }

    public static void info(String msg) {
        if (enableLog)
            log.info(msg);
    }

    public static void warn(String msg) {
        if (enableLog)
            log.warn(msg);
    }

    public static void error(String msg) {
        if (enableLog)
            log.error(msg);
    }

    public static void fatal(String msg) {
        if (enableLog)
            log.error(msg);
    }
}
