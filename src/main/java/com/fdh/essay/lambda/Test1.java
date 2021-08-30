package com.fdh.essay.lambda;

public class Test1 {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类的执行");
            }
        }).start();


        new Thread(() -> {
            System.out.println("lambda表达式执行");
        }).start();
    }
}
