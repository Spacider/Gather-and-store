package com.briup;

import com.briup.Data.TimerRunClient;
import com.briup.Server.TimerDBWrite;

public class TotalProjectMain {
    public static void main(String[] args) {
        TimerRunClient.runClientMain();
        TimerDBWrite.DBWriteMain();
    }
}
