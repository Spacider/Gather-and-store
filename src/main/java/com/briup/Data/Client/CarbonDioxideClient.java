package com.briup.Data.Client;

import com.briup.Bean.Environment;
import com.briup.Data.helper.ClientReceiveHelper;
import com.briup.Data.helper.SAXReaderHelper;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * 二氧化碳客户端
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Message>
 *         <SrcID>100</SrcID>
 *         <DstID>101</DstID>
 *         <DevID>2</DevID>
 *         <SensorAddress>1280</SensorAddress>
 *         <Counter>1</Counter>
 *         <Cmd>3</Cmd>
 *         <Status>1</Status>
 * </Message>
 */
public final class CarbonDioxideClient {
    public static void CarbonDioxideGetObj(){
        ClientReceiveHelper.ClientGetXml("1280",1);
    }
}
