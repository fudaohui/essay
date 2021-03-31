package com.fdh.essay.reflect;

public class A {


    public static void main(String[] args) {

        String a = "a";
        a += "b";

        String b = "ab";
        String d = "ab";
//        System.out.println(a == b);
        System.out.println(b == d);

    }
}
