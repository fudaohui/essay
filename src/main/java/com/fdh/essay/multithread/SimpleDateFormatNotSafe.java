package com.fdh.essay.multithread;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * 线程不安全的SimpleDateFormat演示<br>
 *
 * note:多次调用theadlocal的get的值不变，不要随便remove，以免大量频繁创建这个副本
 *
 * 使用场景：
 * 1、多个方法传递userid,不用在每个方法的型参上带了
 * 2、SimpleDataFormat线程不安全问题？？？
 * 3、SpringAOP的事务
 *
 * @author: fudaohui
 * @date: 2021-12-10 15:53
 */
public class SimpleDateFormatNotSafe {


    /**
     * 线程数为1的时候，和rightContrast一致，大于1，将不一致
     */
//    public static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(16);

    private static ThreadLocal threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));
    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 16,
            200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new NamedThreadFactory("SimpleDateFormatNotSafe"));

    public static String str = "mm:ss";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);

    public static String date(int seconds, SimpleDateFormat simpleDateFormat) {
        Date date = new Date(seconds * 1000);
        String format = simpleDateFormat.format(date);
        return format;
    }


    /**
     * 在主（单）线程中运行正确的代码
     *
     * @param count
     * @return
     */
    public static Map<Integer, String> rightContrast(int count) {
        SimpleDateFormat simpleDateFormatA = new SimpleDateFormat(str);
        HashMap<Integer, String> objectObjectHashMap = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String date = date(i, simpleDateFormatA);
            objectObjectHashMap.put(i, date);
        }
        return objectObjectHashMap;
    }

    public static void main(String[] args) {


        int count = 1000;

        CountDownLatch countDownLatch = new CountDownLatch(count);
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();


        for (int i = 0; i < count; i++) {
            int finalI = i;
            int finalI1 = i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        //线程不安全的方式
//                        String date = SimpleDateFormatNotSafe.date(finalI, simpleDateFormat);
                        //线程安全的方式
                        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormatNotSafe.threadLocal.get();
                        String date = new SimpleDateFormatNotSafe().date(finalI, simpleDateFormat);
                        countDownLatch.countDown();
                        concurrentHashMap.put(finalI1, date);
                    } finally {
                        //?此处移除后会频繁的在threadLocal创建新的SimpleDateFormat对象
                        SimpleDateFormatNotSafe.threadLocal.remove();
                    }

                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        threadPoolExecutor.shutdown();

        System.out.println(concurrentHashMap.size() == count);
        Map<Integer, String> integerStringMap = rightContrast(count);
        int unEqualsCount = 0;
        for (Map.Entry<Integer, String> integerStringEntry : concurrentHashMap.entrySet()) {
            Integer key = integerStringEntry.getKey();
            if (!integerStringMap.get(key).equals(integerStringEntry.getValue())) {
                System.out.println("不相等,key:" + key + " concurrent:" + integerStringEntry.getValue() + " rightV:" + integerStringMap.get(key));
                unEqualsCount++;
            }
        }
        System.out.println("unEqualsCount:" + unEqualsCount);

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
        System.out.println(threadPoolExecutor.isShutdown());
//        Map<Integer, String> integerStringMap = SimpleDateFormatNotSafe.rightContrast(count);
//        for (String value : integerStringMap.values()) {
//            System.out.println(value);
//        }

    }

}
