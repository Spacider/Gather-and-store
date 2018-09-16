package com.briup.util;

/**
 * 日志模块
 * 记录整个程序的运行流程
 * debug info warn error
 */
public interface Log extends WossModel{

    /**
     * 输出 debug 级别的日志
     * @param msg
     */
    void debug(String msg);

    /**
     * 输出 info 级别的日志
     * @param msg
     */
    void info(String msg);

    /**
     * 输出 warn 级别的日志
     * @param msg
     */
    void warn(String msg);


    /**
     * 输出 error 级别的日志
     * @param msg
     */
    void error(String msg);

}
