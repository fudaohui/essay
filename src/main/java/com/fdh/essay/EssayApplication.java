package com.fdh.essay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;

/**
 * https://www.cnblogs.com/yulinfeng/p/12552786.html
 */
@EnableAsync
@SpringBootApplication
public class EssayApplication {

    @Autowired
    private ExecutorService executorService;

    public static void main(String[] args) {
        SpringApplication.run(EssayApplication.class, args);
//        ExecutorService executorService = SpringContextUtils.getBean("aaThreadPool");
//        for (int i = 0; i < 100; i++) {
//            int finalI = i;
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(150);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(finalI);
//                }
//            });
//        }
//        executorService.shutdown();
//        try {
//            executorService.awaitTermination(5, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("主进程执行完毕。。。。。。。。。");
    }

}
