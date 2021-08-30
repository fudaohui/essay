package com.fdh.essay.fathersun;


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
        Dog dog = new Dog();
    }
}
