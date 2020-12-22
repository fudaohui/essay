package com.fdh.essay.reflect;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射和new 的性能对比
 * https://juejin.cn/post/6844904098207105038
 */
public class TestReflect {

    public static void main(String[] args) {
        Class<Student> studentClass = Student.class;
        List<Student> aa = new ArrayList<>();
        int count = 10000;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Constructor<Student> constructor = studentClass.getConstructor();
            for (int i = 0; i < count; i++) {
                Student student = constructor.newInstance();
                student.setAge(i);
                student.setName("mm:" + i);
                aa.add(student);
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            System.out.println("反射耗时：" + (currentTimeMillis2 - currentTimeMillis) + "ms");
            while (true) {
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
