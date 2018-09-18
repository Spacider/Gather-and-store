package com.briup.Data;

import com.briup.Data.Client.CarbonDioxideClient;
import com.briup.Data.Client.DataClient;
import com.briup.Data.Client.GuangClient;

import java.util.Timer;
import java.util.TimerTask;

public final class TimerRunClient {

    public static void runClientMain() {
        // 构建定时器
        Timer timer = new Timer(false);

        /**
         * 第一个参数表示定时器执行的函数,第二个参数表示第一次定时器生效的时间,第三个参数表示每隔多长事件调用第一个参数所指向的方法
         * 第二个参数和第三个参数单位是毫秒
         */

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 定时器调用的代码
                DataClient.DataGetObj();
                GuangClient.guangGetObj();
                CarbonDioxideClient.CarbonDioxideGetObj();
            }
        }, 0, 5000);

    }
}
