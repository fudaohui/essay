package com.fdh.essay.hashedWheelTimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * HashedWheelTimer 特点：
 * 1、时间轮机制
 * 2、一个thread可以监测大量的定时任务
 * 3、task中不要耗时，不适用精确度高的定时任务
 *
 * @author: fudaohui
 * @date: 2021/09/01 10:38
 */
@Slf4j
public class HashedWheelTimerDemo {


    public static void main(String[] args) {
//模拟课程开课时间，计算时间差
//        long off1 = DateUtils.diff(new Date(), DateUtils.offsetMinute(new Date(), 1), DateUnit.SECOND);
//        long off2 = DateUtils.diff(new Date(), DateUtils.offsetMinute(new Date(), 2), DateUnit.SECOND);
        log.info("----------开始-----------" );
        List<Long> offs = new ArrayList<>();
        offs.add(2L);
        offs.add(5L);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        HashedWheelTimer timer = new HashedWheelTimer();
        for (int i = 0; i < 2; i++) {
            final int ii = i;
            timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    log.info("---提醒发送-----" + ii);
                    countDownLatch.countDown();
                }

            }, offs.get(i), TimeUnit.SECONDS);
        }
        try {
            countDownLatch.await();
            timer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
