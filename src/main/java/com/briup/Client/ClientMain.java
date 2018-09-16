package com.briup.Client;

import com.briup.Bean.Environment;
import com.briup.Client.Impl.EnvClientImpl;
import com.briup.Client.Impl.GatherImpl;

import java.util.List;

public final class ClientMain {

    public static void ClientSendMain(){
        GatherImpl gather = new GatherImpl();
        List<Environment> environmentList = (List <Environment>) gather.gather();
        new EnvClientImpl().send(environmentList);
    }

    public static void main(String[] args) {
        ClientSendMain();
    }

}
