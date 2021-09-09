package com.fdh.essay.distruptor;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;

/**
 * 处理器
 *
 * @author: fudaohui
 * @date: 2021/08/30 18:08
 */
@Component
public class MyHandler implements EventHandler<Element> {
    @Override
    public void onEvent(Element element, long l, boolean b) throws Exception {

        System.out.println("Element: " + element.get());
        Thread.sleep(200);
    }
}
