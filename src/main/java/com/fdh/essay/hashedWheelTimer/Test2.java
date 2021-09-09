package com.fdh.essay.hashedWheelTimer;

import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * @author: fudaohui
 * @date: 2021/09/08 21:00
 */
public class Test2 {


    public static void main(String[] args) {
        HashedWheelTimer timer = new HashedWheelTimer();
        timer.newTimeout(new Test(),1, TimeUnit.SECONDS);
    }
}
