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
        log.info("----------开始-----------");
        List<Long> offs = new ArrayList<>();
        offs.add(10L);
        offs.add(20L);
//        CountDownLatch countDownLatch = new CountDownLatch(2);
        HashedWheelTimer timer = new HashedWheelTimer();
        for (int i = 0; i < 2; i++) {
            final int ii = i;
            Timeout timeout = timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    log.info("---提醒发送-----" + ii);
//                    countDownLatch.countDown();
                }

            }, offs.get(i), TimeUnit.SECONDS);
//            timeout.cancel();
        }
//        try {
//            countDownLatch.await();
//            timer.stop();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        System.out.println(0 & 10);
//        System.out.println(1 & 10);
//        System.out.println(9 & 10);
//        System.out.println(1 & 10);
//        System.out.println(19 & 10);
//        System.out.println(20 & 10);
//        System.out.println(10 & 10);
    }
}
