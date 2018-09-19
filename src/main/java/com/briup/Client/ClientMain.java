package com.briup.Client;

import com.briup.Bean.Environment;
import com.briup.Client.Impl.EnvClientImpl;
import com.briup.Client.Impl.GatherImpl;
import com.briup.util.Impl.ConfigurationImpl;

import java.util.List;

public final class ClientMain {

    /**
     * 集合 Client 并提供向外的入口
     * 通过采集所获得的 list 发给 Server
     */
    public static void ClientSendMain(){
        ConfigurationImpl configuration = new ConfigurationImpl();
        GatherImpl gather = (GatherImpl) configuration.getGather();
        List<Environment> environmentList = (List <Environment>) gather.gather();
        configuration.getClient().send(environmentList);
    }

}
