package com.briup.Server;

import com.briup.Bean.Environment;
import com.briup.util.WossModel;

import java.util.Collection;

/**
 * 入库模块
 */
public interface DBStore extends WossModel {
    /**
     * 把服务器接收到的数据写入到数据库中持久保存
     * 注意数据的录入是按天进行录入的
     * t_s ---> 拆分成多张表
     * 20/5 取余数
     * t_s0 t_s1 t_s2 t_s3 t_s4
     * @param col
     */
    void saveEnvToDB(Collection<Environment> col);
}
