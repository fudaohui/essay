package com.fdh.essay;

import com.fdh.essay.distruptor.TestProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestDistruptor {


    @Autowired
    private TestProcessor testProcessor;


    @Test
    void testme() {

        /**
         * 经过测试，生产者发布数据的速度取决于队列大小和消费者消费的速度，
         * 也就是说生产者可能会阻塞（阻塞目的是不丢你数据）
         */
        long l1 = System.currentTimeMillis();
        for (int l = 0; l < 100; l++) {

            testProcessor.publishEvent(l);
        }
        long l2 = System.currentTimeMillis();
        System.out.println("入队:" + (l2 - l1));


        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
