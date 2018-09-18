package com.briup;

import com.briup.Data.TimerRunClient;
import com.briup.Server.TimerDBWrite;

public class TotalProjectMain {

    /**
     * 两个定时器的入口
     * 采集模块和数据库入库模块的封装入口
     * @param args
     */
    public static void main(String[] args) {
        TimerRunClient.runClientMain();
        TimerDBWrite.DBWriteMain();
    }
}
