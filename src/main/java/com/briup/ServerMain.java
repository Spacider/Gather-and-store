package com.briup;

import com.briup.Server.EnvServer;
import com.briup.Server.Impl.EnvServerImpl;

public final class ServerMain {

    public static void ServerReceiveMain(){
        EnvServer envServer =  new EnvServerImpl();
        envServer.receive();
    }

    public static void main(String[] args) {
        ServerReceiveMain();
    }
}
