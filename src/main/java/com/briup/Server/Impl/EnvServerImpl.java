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

    /**
     * 服务器接收方法
     */
    public void receive() {
        Socket socket = null;

        try {
            ServerSocket server = new ServerSocket(9999);
            while(true){
                socket = server.accept();
                new EnvServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init(Properties properties) {

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
                Environment obj = null;
                while ((obj = (Environment) ois.readObject()) != null) {
                    // 一旦读取到一个对象，就将其塞入 list 集合中
                    environmentList.add(obj);
                }

                System.out.println(environmentList);

                // 数据库入库操作
                DBStoreImpl dbStore = new DBStoreImpl();
                dbStore.saveEnvToDB(environmentList);


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(is,ois,socket);
            }
        }
    }
}
