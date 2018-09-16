package com.briup.util;

import java.util.Properties;

/**
 * 初始化配置参数
 */
public interface WossModel {
    /**
     *
     * @param properties
     *
     * .properties
     * 读取之后
     * user = com.briup.client.gather;
     * Class.forName(user).newInstance();
     */
    void init(Properties properties);




}
