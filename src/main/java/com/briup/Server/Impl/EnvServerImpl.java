package com.briup.Server.Impl;

import com.briup.Bean.Environment;
import com.briup.Server.EnvServer;
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
public class EnvServerImpl implements EnvServer {

    private int port;

    public void init(Properties properties) {
        port = Integer.parseInt(properties.getProperty("port"));
    }

    /**
     * 服务器接收方法
     */
    public void receive() {
        Socket socket = null;

        try {
            System.out.println(port);
//            port = 9999;
            ServerSocket server = new ServerSocket(port);
            System.out.println(server);
            while(true){
                socket = server.accept();
                new EnvServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class EnvServerThread extends Thread{

    private List<Environment> environmentList = new ArrayList <>();
    private Socket socket;

    public EnvServerThread (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        synchronized (this) {
            InputStream is = null;
            ObjectInputStream ois = null;
            try {
                is = socket.getInputStream();
                ois = new ObjectInputStream(is);

                environmentList = (List <Environment>) ois.readObject();

                System.out.println(environmentList);

                // 数据库入库操作
                DBStoreImpl dbStore = new DBStoreImpl();
                dbStore.saveEnvToDB(environmentList);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(is,ois,socket);
            }
        }
    }
}
