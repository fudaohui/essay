package com.fdh.essay.multithread;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * 线程不安全的SimpleDateFormat演示<br>
 *
 * @author: fudaohui
 * @date: 2021-12-10 15:53
 */
public class SimpleDateFormatNotSafe {


    /**
     * 线程数为1的时候，和rightContrast一致，大于1，将不一致
     */
    public static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(1);

//    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16,
//            0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));

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
                    String date = SimpleDateFormatNotSafe.date(finalI, simpleDateFormat);
                    countDownLatch.countDown();
                    String put = concurrentHashMap.put(finalI1, date);
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPoolExecutor.shutdown();

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
//        Map<Integer, String> integerStringMap = SimpleDateFormatNotSafe.rightContrast(count);
//        for (String value : integerStringMap.values()) {
//            System.out.println(value);
//        }

    }

}
