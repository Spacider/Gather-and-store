package com.briup.Data.helper;

import com.briup.util.Impl.IOUtil;
import com.briup.util.Impl.LogImpl;

import java.io.*;
import java.net.Socket;

/**
 * 封装了从树莓派取出数据的一些操作方法
 */
public final class ClientReceiveHelper {

    private final static LogImpl logger = new LogImpl();
    /**
     * 拼装发送的 XML 文件
     * @param SensorAddress
     * @param counter
     * @return
     */
    private final static String XmlToSend(String SensorAddress,String counter){
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"+
                "<Message>\r\n"+
                "<SrcID>100</SrcID>\r\n"+
                "<DstID>101</DstID>\r\n"+
                "<DevID>2</DevID>\r\n"+
                "<SensorAddress>"+SensorAddress+"</SensorAddress>\r\n"+
                "<Counter>"+counter+"</Counter>\r\n"+
                "<Cmd>3</Cmd>\r\n"+
                "<Status>1</Status>\r\n"+
                "</Message>\r\n";
        return str;
    }

    /**
     * 客户端通过 TCP/IP 来获得到树莓派所传递的数据
      * @param SensorAddress
     * @param counter
     */
    public final static void ClientGetXml(String SensorAddress,int counter){
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        BufferedReader br = null;
        Socket socket = null;
        try {
            // 填入树莓派的 host 和 port
//            socket = new Socket("192.168.1.7", 10000);
            socket = new Socket("127.0.0.1", 8888);
            logger.info(socket.toString()+"正在接收树莓派传输的数据");
//            System.out.println(socket);
            synchronized (socket) {
                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                String str = XmlToSend(SensorAddress, counter + "");
                pw.write(str.toCharArray());
                pw.flush();

                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String Backstr = null;
                // 拼接读取返回的 XML 文件
                StringBuilder sb = new StringBuilder();
                while ((Backstr = br.readLine()) != null) {
                    // 在末尾加入 \r\n 方便观察，也方便读取
                    sb.append(Backstr + "\r\n");
                    if (Backstr.trim().equals("</Message>")) {
                        break;
                    }
                }

                // 根据 XML 文件解析，并将其写入文件中
                SAXReaderHelper.InLogFile(sb.toString(), SensorAddress, counter + "");
            }
        } catch (IOException e) {
            logger.error("断开连接,无法接收树莓派传输的数据" + e);
        } finally {
            IOUtil.close(br,is,pw,os,socket);
        }
    }
}
