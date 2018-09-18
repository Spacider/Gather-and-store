package com.briup.Client.Impl;

import com.briup.Bean.Environment;
import com.briup.Client.EnvClient;
import com.briup.util.Impl.IOUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
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

        try {
            socket = new Socket("127.0.0.1",9999);
            os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            // 运用对象流把生成的对象发给 Server 端
            for (Environment environment : col) {
                oos.writeObject(environment);
            }
            col.clear();
            // 添加 null 为结尾，防止爆出 EOFException
            oos.writeObject(null);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(os,socket);
        }
    }


    public void init(Properties properties) {

    }
}
