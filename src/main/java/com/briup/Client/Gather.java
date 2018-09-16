package com.briup.Client;

import com.briup.Bean.Environment;
import com.briup.util.WossModel;

import java.util.Collection;

public interface Gather extends WossModel {

    /**
     * 采集模块接口实现
     * 对 src 下日志进行处理，一行日志封装成一个 Environment 对象或者两个 Environment 对象
     * 所有采集到的对象封装到集合中
     * @return
     */
    Collection<Environment> gather();
}
