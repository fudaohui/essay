package com.fdh.essay.delayedqueue;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * @author: fdh
 */
@Slf4j
public class DelayedQueueTest {

    public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        RBlockingQueue<String> blockingQueue = redisson.getBlockingQueue("dest_queue1");
        RDelayedQueue<String> delayedQueue = redisson.getDelayedQueue(blockingQueue);
        new Thread() {
            public void run() {
                while(true) {
                    try {
                        //阻塞队列有数据就返回，否则wait
                        log.info("data:{}",blockingQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
        log.info("开始。。。。");
        for(int i=1;i<=5;i++) {
            // 向阻塞队列放入数据
            delayedQueue.offer("fffffffff"+i, 5, TimeUnit.SECONDS);
        }
    }

}
