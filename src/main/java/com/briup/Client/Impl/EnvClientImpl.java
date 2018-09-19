package com.briup.Client.Impl;

import com.briup.Bean.Environment;
import com.briup.Client.EnvClient;
import com.briup.util.BackUp;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationAware;
import com.briup.util.Impl.BackUpImpl;
import com.briup.util.Impl.ConfigurationImpl;
import com.briup.util.Impl.IOUtil;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

/**
 * 客户端模块实现
 */
public class EnvClientImpl implements EnvClient , ConfigurationAware {

    private String host;
    private int port;
    private String path;
    private Configuration configuration;

    @Override
    public void init(Properties properties) {
        host = properties.getProperty("host");
        port = Integer.parseInt(properties.getProperty("port"));
        path = properties.getProperty("path");
    }

    @Override
    public void SetConfiguration(Configuration conf) {
        this.configuration = conf;
    }

    /**
     * 发送端主函数
     * @param col
     */
    @Override
    public void send(Collection<Environment> col) {
        OutputStream os = null;
        Socket socket = null;
        ObjectOutputStream oos=  null;
        File file= null;

        try {
            file = new File(path);
            if ( file.exists() ){
                Collection<Environment> collection=configuration.getBackUp().loadEnvs();
                if (collection != null) {
                    col.addAll(collection);
                }
                file.delete();
            }

            socket = new Socket(host,port);
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);

            // 运用对象流把生成的对象发给 Server 端
            System.out.println(col);
            oos.writeObject(col);

            // 添加 null 为结尾，防止爆出 EOFException
//            oos.writeObject(null);
            oos.flush();

        } catch (Exception e) {
            e.printStackTrace();
            configuration.getBackUp().storeEnvs(col);
        } finally {
            IOUtil.close(os,socket,oos);
        }
    }


}
