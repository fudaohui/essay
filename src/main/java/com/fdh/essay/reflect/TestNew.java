package com.fdh.essay.reflect;

import java.util.ArrayList;
import java.util.List;

public class TestNew {

    public static void main(String[] args) {
        int count = 10000;
        List<Student> aa = new ArrayList<>();
        long currentTimeMillis2 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setName("mm:" + i);
            aa.add(student);
        }
        long currentTimeMillis3 = System.currentTimeMillis();

        System.out.println("new 耗时：" + (currentTimeMillis3 - currentTimeMillis2) + "ms");
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
