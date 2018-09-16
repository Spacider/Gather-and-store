package com.briup.Client;

import com.briup.Bean.Environment;
import com.briup.util.WossModel;

import java.util.Collection;

/**
 * 客户端模块
 */
public interface EnvClient extends WossModel {
    /**
     * 发送采集模块采集的集合对象
     */
    void send(Collection<Environment> col);
}
