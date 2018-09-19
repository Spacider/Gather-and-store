package com.briup.util;

import com.briup.Bean.Environment;

import java.util.Collection;

/**
 * 备份模块
 * 1. 客户端发送集合没有发送出去或连不上服务器需要备份
 * 2. 服务端接收了集合对象写入数据库出错，对集合进行备份
 */
public interface BackUp extends WossModel{

    /**
     * 将集合保存到文件中
     * @param coll 需要备份的集合
     */
    void storeEnvs(Collection<Environment> coll );


    /**
     * 将集合从文件中读取
     * @return
     */
    Collection<Environment> loadEnvs();
}
