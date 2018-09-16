package com.briup.Data.Client;

import com.briup.Data.helper.ClientReceiveHelper;


/**
 * 树莓派光照客户端
 * 申请获取光敏检测数据:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Message>
 *         <SrcID>100</SrcID>
 *         <DstID>101</DstID>
 *         <DevID>2</DevID>
 *         <SensorAddress>256</SensorAddress>
 *         <Counter>1</Counter>
 *         <Cmd>3</Cmd>
 *         <Status>1</Status>
 * </Message>
 */
public final class GuangClient {
    public static void guangGetObj(){
        ClientReceiveHelper.ClientGetXml("256",1);
    }
}
