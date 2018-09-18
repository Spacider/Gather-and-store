package com.briup;

import com.briup.Server.EnvServer;
import com.briup.Server.Impl.EnvServerImpl;

public final class ServerMain {

    /**
     * Server 端主函数，接收从采集模块发送过来的对象
     */
    public static void main(String args[]){
        EnvServer envServer =  new EnvServerImpl();
        envServer.receive();
    }

}
