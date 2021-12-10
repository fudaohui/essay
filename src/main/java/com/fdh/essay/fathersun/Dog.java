package com.fdh.essay.fathersun;


import java.util.concurrent.ConcurrentHashMap;

/**
 * 父类构造中调用的方法被子类覆盖
 */
public class Dog extends Animal {

    public Dog() {
    }

    @Override
    protected void eat() {
        System.out.println("eat shit!");
    }

    public static void main(String[] args) {
//        Dog dog = new Dog();

        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        objectObjectConcurrentHashMap.put(1, 1);
        System.out.println(".........");
    }
}
