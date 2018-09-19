package com.briup.Server.Impl;

import com.briup.Bean.Environment;
import com.briup.Server.DBStore;
import com.briup.Server.EnvServer;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationAware;
import com.briup.util.Impl.ConfigurationImpl;
import com.briup.util.Impl.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 服务器端模块
 */
public class EnvServerImpl implements EnvServer ,ConfigurationAware{

    private int port;
    private Configuration  configuration;

    public void init(Properties properties) {
        port = Integer.parseInt(properties.getProperty("port"));
    }

    public void SetConfiguration(Configuration conf) {
        this.configuration =conf;
    }


    /**
     * 服务器接收方法
     */
    public void receive() {
        Socket socket = null;

        try {
            System.out.println(port);
            ServerSocket server = new ServerSocket(port);
            System.out.println(server);
            while(true){
                socket = server.accept();
                new EnvServerThread(socket,configuration.getDBStore()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class EnvServerThread extends Thread {

    private Socket socket;
    private DBStore  dbStore;



    public EnvServerThread (Socket socket,DBStore dbStore){
        this.socket = socket;
        this.dbStore = dbStore;
    }

    @Override
    public void run() {
        synchronized (this) {
            InputStream is = null;
            ObjectInputStream ois = null;
            try {
                is = socket.getInputStream();
                ois = new ObjectInputStream(is);

                List <Environment> environmentList = (List <Environment>) ois.readObject();

                System.out.println(environmentList);

                // 数据库入库操作
//                ConfigurationImpl configuration = new ConfigurationImpl();
//                dbStore  = configuration.getDBStore();
                dbStore.saveEnvToDB(environmentList);


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(is,ois,socket);
            }
        }
    }
}
