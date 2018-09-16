package com.briup.Data.Client;

import com.briup.Bean.Environment;
import com.briup.Data.helper.ClientReceiveHelper;
import com.briup.Data.helper.SAXReaderHelper;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * 每五秒定时菜鸡温度湿度指令的客户端
 * 职责：
 * 1. 发送指令给树莓派系统
 * 2. 接收树莓派写回的指令
 * 3. 把发送和接收的指令整合成一条日志文件
 *
 *
 * 温度湿度客户端
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Message>
 *         <SrcID>100</SrcID>
 *         <DstID>101</DstID>
 *         <DevID>2</DevID>
 *         <SensorAddress>16</SensorAddress>
 *         <Counter>1</Counter>
 *         <Cmd>3</Cmd>
 *         <Status>1</Status>
 * </Message>
 */
public final class DataClient {
    public static void DataGetObj(){
        ClientReceiveHelper.ClientGetXml("16",1);
    }
}
