package com.fdh.essay.distruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author: fudaohui
 * @date: 2021/08/30 18:13
 */
public class MyEventFactory implements EventFactory<Element> {
    @Override
    public Element newInstance() {
        return new Element();
    }
}
