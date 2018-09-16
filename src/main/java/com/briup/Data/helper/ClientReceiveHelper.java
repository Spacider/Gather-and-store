package com.briup.Data.helper;

import com.briup.util.Impl.IOUtil;

import java.io.*;
import java.net.Socket;

public final class ClientReceiveHelper {

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

    public final static void ClientGetXml(String SensorAddress,int counter){
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        BufferedReader br = null;
        ByteArrayInputStream bais = null;
        try {
            Socket socket = new Socket("127.0.0.1",8888);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            String str = XmlToSend(SensorAddress,counter+"");
            pw.write(str.toCharArray());
            pw.flush();


            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String Backstr = null;
            // 拼接读取返回的 XML 文件
            StringBuilder sb = new StringBuilder();
            while ( (Backstr=br.readLine()) != null) {
                sb.append(Backstr+"\r\n");
//                System.out.println(Backstr);
                if (Backstr.equalsIgnoreCase("</Message>")){
                    break;
                }
            }

            // 根据 XML 文件解析，并将其写入文件中
            SAXReaderHelper.InLogFile(sb.toString(),SensorAddress,counter+"");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(br,is,pw,os,bais);
        }
    }
}
