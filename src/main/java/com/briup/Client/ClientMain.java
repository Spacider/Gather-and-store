package com.briup.Client;

import com.briup.Bean.Environment;
import com.briup.Client.Impl.EnvClientImpl;
import com.briup.Client.Impl.GatherImpl;

import java.util.List;

public final class ClientMain {

    /**
     * 集合 Client 并提供向外的入口
     * 通过采集所获得的 list 发给 Server
     */
    public static void ClientSendMain(){
        GatherImpl gather = new GatherImpl();
        List<Environment> environmentList = (List <Environment>) gather.gather();
        new EnvClientImpl().send(environmentList);
    }

}
