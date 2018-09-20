package com.briup.util.Impl;

import com.briup.util.Configuration;
import com.briup.util.Log;
import com.briup.util.WossModel;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * 日志模块实现
 */
public class LogImpl implements Log  {

    private Logger LOGGER = Logger.getLogger(LogImpl.class);

    public LogImpl() {
        PropertyConfigurator.configure("/Users/wjh/Desktop/FirstProject/src/main/resources/log4j.properties");
    }

    @Override
    public void debug(String msg) {
        LOGGER.debug(msg);
    }

    @Override
    public void info(String msg) {
        LOGGER.info(msg);
    }

    @Override
    public void warn(String msg) {
        LOGGER.warn(msg);
    }

    @Override
    public void error(String msg) {
        LOGGER.error(msg);
    }

    @Override
    public void init(Properties properties) {

    }

    public static void main(String[] args) {
        Configuration configuration = new ConfigurationImpl();
        configuration.getLog().debug("debug......");
        configuration.getLog().info("info......");
        configuration.getLog().warn("warn......");
        configuration.getLog().error("error......");

    }
}
