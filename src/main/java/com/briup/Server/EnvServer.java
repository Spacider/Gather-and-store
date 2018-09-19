package com.briup.Server;

import com.briup.util.WossModel;

/**
 * 服务器端模块
 */
public interface EnvServer extends WossModel  {

    /**
     * 接收各个客户端发送过来的集合对象 Collection(Environment)
     */
    void receive();

}
