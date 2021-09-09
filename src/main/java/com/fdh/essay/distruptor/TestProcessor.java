package com.fdh.essay.distruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;


/**
 * @author: fudaohui
 * @date: 2021/08/30 18:10
 */
@Slf4j
@Component
public class TestProcessor implements InitializingBean, DisposableBean {

    /**
     * ringBufferSize 大小，必须是 2 的 N 次方；
     */
    private final static int ringBufferSize = 2;

    @Autowired
    private MyHandler myHandler;

    private Disruptor<Element> disruptor = null;

    private final static MyEventFactory myEventFactory = new MyEventFactory();

    @Override
    public void destroy() throws Exception {
        if (disruptor != null) {
            log.info("close disruptor");
            disruptor.shutdown();
            disruptor = null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 生产者的线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "simpleThread");
            }
        };
        disruptor = new Disruptor<Element>(
                //事件对象工厂
                myEventFactory,
                //buf大小
                ringBufferSize,
                //线程工厂
                threadFactory,
                //生成者类型
                ProducerType.SINGLE,
                //阻塞策略
                new BlockingWaitStrategy());

        log.info("init disruptor ringBufferSize : {}", ringBufferSize);
        disruptor.handleEventsWith(myHandler);
        disruptor.start();
        log.info("disruptor start");
    }


    /**
     * 发布事件
     *
     * @param v
     */
    public void publishEvent(int v) {
        // 发布事件
        if (disruptor != null) {
            RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            try {
                // 返回可用位置的元素
                Element event = ringBuffer.get(sequence);
                // 设置该位置元素的值
                event.set(v);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }
}
