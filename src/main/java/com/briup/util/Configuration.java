package com.briup.util;

import com.briup.Client.EnvClient;
import com.briup.Client.Gather;
import com.briup.Server.DBStore;
import com.briup.Server.EnvServer;

/**
 * 获取各个配置模块对象
 * 对所有对象进行管理
 */
public interface Configuration {
    /**
     * 获取采集模块对象
     */
    Gather getGather();

    /**
     * 获取客户端模块对象
     */
    EnvClient getClient();

    /**
     * 获取服务器端对象
     */
    EnvServer getServer();

    /**
     * 获取入库模块对象
     */
    DBStore getDBStore();

    /**
     * 获取日志模块对象
     */
    Log getLog();

    /**
     * 获取备份模块对象
     */
    BackUp getBackUp();

}
