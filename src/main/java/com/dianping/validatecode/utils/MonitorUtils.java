package com.dianping.validatecode.utils;

import org.apache.log4j.Logger;

public class MonitorUtils {
    private static final Logger log = Logger.getLogger(MonitorUtils.class);

    public static void traceError(String msg, Throwable e) {
        log.error(msg, e);
    }

    public static void traceError(String msg) {
        log.error(msg);
    }

    public static void logInfo(String msg) {
        log.info(msg);
    }

}
