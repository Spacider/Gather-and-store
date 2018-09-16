package com.briup.Data.Server;

import com.briup.Bean.Environment;
import com.briup.Data.helper.SAXReaderHelper;
import com.briup.util.Impl.IOUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 相当于树莓派系统:
 * 模拟树莓派温度湿度产生服务器
 * 职责：
 * 1. 接收客户端发出的指令，import com.briup.Bean.Environment;基于指令调用的传感器采集数据
 * 2. 把采集的数据封装成指令再发给客户端
 * 3. (多线程) 不同的指令返回不同的数据
 */
public class DataServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8888);
            while (true){
                Socket socket = server.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerThread extends Thread{


    private Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        synchronized (this) {
            InputStream is = null;
            BufferedReader br = null;
            ByteArrayInputStream bris = null;
            PrintWriter pw = null;
            OutputStream os = null;
            try {
                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                // 接收客户端传来的 str
                String str = null;
                StringBuilder sb = new StringBuilder();
                while ((str = br.readLine()) != null) {
                    sb.append(str + "\r\n");
                    // 如果读取到最后一行，则退出
                    if (str.equalsIgnoreCase("</Message>")) {
                        break;
                    }
                }

                // 根据不同的 SensorAddress 返回不同的XML
                String BackStr = SAXReaderHelper.BackXml(sb.toString());

                System.out.println(BackStr);

                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                pw.write(BackStr.toCharArray());
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(os,pw,bris,is,br);
            }
        }
    }
}
