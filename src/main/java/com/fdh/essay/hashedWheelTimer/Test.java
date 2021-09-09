package com.fdh.essay.hashedWheelTimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author: fudaohui
 * @date: 2021/09/08 20:49
 */
@Slf4j
public class Test implements TimerTask {

    private static HashedWheelTimer timer = new HashedWheelTimer();

    static int count = 5;

    @Override
    public void run(Timeout timeout) throws Exception {

        try {
            timeout = timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    log.info("---提醒发送-----");
                }

            }, count, TimeUnit.SECONDS);
        } finally {
            count--;
            if (count <= 1) {
                count = 1;
            }
            timeout.timer().newTimeout(this, count, TimeUnit.SECONDS);
        }
    }
}
