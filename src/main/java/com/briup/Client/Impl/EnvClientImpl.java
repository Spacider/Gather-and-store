package com.briup.Client.Impl;

import com.briup.Bean.Environment;
import com.briup.Client.EnvClient;
import com.briup.util.Impl.BackUpImpl;
import com.briup.util.Impl.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * 客户端模块实现
 */
public class EnvClientImpl implements EnvClient {
    /**
     * 发送端主函数
     * @param col
     */
    public void send(Collection<Environment> col) {
        OutputStream os = null;
        Socket socket = null;
        ObjectOutputStream oos=  null;
        BackUpImpl backUp = new BackUpImpl();
        File file= null;

        try {
            socket = new Socket("127.0.0.1",9999);
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);

            String path = "/Users/wjh/Desktop/FirstProject/src/BackUptmp";

            file = new File(path);
            if ( file.exists() ){
                col.addAll(backUp.loadEnvs());
                file.delete();
            }
            // 运用对象流把生成的对象发给 Server 端
            System.out.println(col);
            oos.writeObject(col);

            // 添加 null 为结尾，防止爆出 EOFException
//            oos.writeObject(null);
            oos.flush();

        } catch (Exception e) {
            String path = "/Users/wjh/Desktop/FirstProject/src/BackUptmp";
            backUp.storeEnvs(col,path);
        } finally {
            IOUtil.close(os,socket,oos);
        }
    }


    public void init(Properties properties) {

    }
}
