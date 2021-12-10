package com.fdh.essay.threadpool;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;

/**
 * 打印多少个线程<br>
 *
 * @author: fudaohui
 * @date: 2021-12-07 9:48
 */
public class ThreadsTest {

    public static void main(String[] args) {
//        // 获取 Java 线程管理 MXBean
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
//        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
//        // 遍历线程信息，仅打印线程 ID 和线程名称信息
//        for (ThreadInfo threadInfo : threadInfos) {
//            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
//        }

        int a =100;

        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            integers.add(i);
        }
    }


}
