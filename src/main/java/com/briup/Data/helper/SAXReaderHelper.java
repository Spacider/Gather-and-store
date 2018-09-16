package com.briup.Data.helper;

import com.briup.Bean.Environment;
import com.briup.util.Impl.IOUtil;
import oracle.sql.NUMBER;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class SAXReaderHelper {
    /**
     * 将获取到数据写入到日志文件中
     * @param Message
     * @param name
     * @param counter
     * @return
     */
    public static void InLogFile(String str,String SensorAddress,String counter){
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        // 把客户端传过来的 xml 转化为字符串存储
        String TotalStr = str.toString();
        byte[] TotalBytes = TotalStr.getBytes();
        bais = new ByteArrayInputStream(TotalBytes);
        SAXReader reader = new SAXReader();
        Document document = null;
        String BackStr = null;
        // 获取根节点

        try {
            document = reader.read(bais,"utf-8");
            Element Message = document.getRootElement();
            StringBuilder sb = new StringBuilder();
            // 拼接字符串
            sb.append(Message.element("SrcID").getText()+"|");

            sb.append(Message.element("DstID").getText()+"|");

            sb.append(Message.element("DevID").getText()+"|");

            sb.append(SensorAddress+"|");

            sb.append(counter+"|");

            sb.append(Message.element("Cmd").getText()+"|");

            sb.append(Message.element("Data").getText()+"|");

            sb.append(Message.element("Status").getText()+"|");

            sb.append(new Timestamp(System.currentTimeMillis()));

            sb.append("\r\n");

            System.out.println(sb);

            // 运用文件输出流将文件输出
            File file = new File("/Users/wjh/Desktop/FirstProject/src/radwtmp");
            if (!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file,true);
            fos.write(sb.toString().getBytes());
            fos.flush();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(bais,fos);
        }

    }



    /**
     * 根据不同的 SensorAddress 返回不同的XML
     * @param bris
     */
    public static String BackXml(String sb){
        ByteArrayInputStream bais = null;
        // 把客户端传过来的 xml 转化为字符串存储
        String TotalStr = sb.toString();
        byte[] TotalBytes = TotalStr.getBytes();
        bais = new ByteArrayInputStream(TotalBytes);
        SAXReader reader = new SAXReader();
        Document document = null;
        String BackStr = null;
        // 获取根节点
        try {
            document = reader.read(bais,"utf-8");
            Element Message = document.getRootElement();
            String str = null;
            // 获取结点 SensorAddress 的值
            String SensorAddress =  Message.element("SensorAddress").getText();
            if (SensorAddress.equals("16")){
                str = "5d606f7802";
                BackStr = getBakXml(str);
                System.out.println("温度湿度");
            }else if (SensorAddress.equals("256")){
                str = "5d6002";
                BackStr = getBakXml(str);
                System.out.println("光照");
            }else if (SensorAddress.equals("1280")){
                str = "5d6002";
                BackStr = getBakXml(str);
                System.out.println("二氧化碳");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(bais);
        }
        return BackStr;
    }

    /**
     * 凭借返回的 XML
     * @param DataStr
     * @return
     */
    public static String getBakXml(String DataStr){
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"+
                "<Message>\r\n"+
                    "<SrcID>100</SrcID>\r\n"+
                    "<DstID>101</DstID>\r\n"+
                    "<DevID>2</DevID>\r\n"+
                    "<SensorAddress>0</SensorAddress>\r\n"+
                    "<Counter>0</Counter>\r\n"+
                    "<Cmd>3</Cmd>\r\n"+
                    "<Data>"+DataStr+"</Data>\r\n"+
                    "<Status>1</Status>\r\n"+
                "</Message>\r\n";
        return str;
    }



}
