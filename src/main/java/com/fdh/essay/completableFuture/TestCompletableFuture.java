package com.fdh.essay.completableFuture;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class TestCompletableFuture {

    public static void main(String[] args) throws Exception {
//        syncTest();
//        asyncTest();
//        taskArrange1();
//        taskArrange2();
        taskArrange3();
    }

    /**
     * CompletableFuture本身可以不承载可执行的任务,和Future类似，get()函数也是同步阻塞的，
     * 调用get函数后线程会阻塞直到调用complete方法标记任务已经执行成功
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void syncTest() throws ExecutionException, InterruptedException {
//        CompletableFuture<String> completableFuture = new CompletableFuture();
//        completableFuture.complete("success");
        //同上等价
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("success");
        System.out.println(completableFuture.get());
    }

    public static void asyncTest() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //supplyAsync 使用Supplier可以提供结果的
//        log.info("开始");
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("do something by thread" + Thread.currentThread().getName());
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return "success";
//        }, Executors.newSingleThreadExecutor());
//        System.out.println(completableFuture.get());
//        log.info("结束");

        //也可以不返回结果的
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("do something by thread" + Thread.currentThread().getName());
        }, executorService);

    }

    /**
     * 任务编排：任务2依赖任务1执行完成
     */
    public static void taskArrange1() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "任务1执行结果";
        }, executorService);

        CompletableFuture<String> future = future1.thenApply(result -> {
            System.out.println(result);
            return "任务2执行结果";
        });
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 1，2同时执行，步骤3需要等步骤1和2执行完之后才能执行
     */
    public static void taskArrange2() {

        ExecutorService executor = Executors.newFixedThreadPool(4);
        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行【步骤1】");
            return "【步骤1的执行结果】";
        }, executor);

        CompletableFuture<String> step2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行【步骤2】");
            return "【步骤2的执行结果】";
        }, executor);

        CompletableFuture<String> step3 = step1.thenCombine(step2, (result1, result2) -> {
            System.out.println("前两步操作结果分别为：" + result1 + result2);
            return "【步骤3的执行结果】";
        });

        try {
            System.out.println("步骤3的执行结果：" + step3.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 多元依赖，1，2，3同时执行，M依赖于上述都执行完成后在执行，这里有join方法注意
     */
    public static void taskArrange3() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CompletableFuture<String> step1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行【步骤1】");
            return "【步骤1的执行结果】";
        }, executor);
        CompletableFuture<String> step2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行【步骤2】");
            return "【步骤2的执行结果】";
        }, executor);
        CompletableFuture<String> step3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行【步骤3】");
            return "【步骤3的执行结果】";
        }, executor);

        CompletableFuture<Void> stepM = CompletableFuture.allOf(step1, step2, step3);
        CompletableFuture<String> stepMResult = stepM.thenApply(res -> {
            // 通过join函数获取返回值
            String result1 = step1.join();
            String result2 = step2.join();
            String result3 = step3.join();

            return result1 + result2 + result3;
        });
        try {
            System.out.println("步骤M的结果：" + stepMResult.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
