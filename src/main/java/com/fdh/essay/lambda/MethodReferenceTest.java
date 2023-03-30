package com.fdh.essay.lambda;

import com.baomidou.mybatisplus.core.toolkit.support.BiIntFunction;

import java.util.Comparator;
import java.util.function.Function;

/**
 * 方法引用学习测试
 */
public class MethodReferenceTest {


    public static void main(String[] args) {
        //方法引用的出现，使得我们可以将一个方法赋给一个变量或者作为参数传递给另外一个方法。::双冒号作为方法引用的符号
        Function<String, Integer> stringIntegerBiIntFunction = Integer::parseInt;
        Integer apply = stringIntegerBiIntFunction.apply("100");
        System.out.println(apply);

        //2.将方法赋给 BiIntFunction<Integer, Integer>接收
        BiIntFunction<Integer, Integer> compare = Integer::compare;
        Integer apply1 = compare.apply(10, 11);
        System.out.println(apply1);

        //3.将方法赋给  Comparator<Integer>接收
        Comparator<Integer> compare1 = Integer::compare;
        int compare2 = compare1.compare(100, 88);
        System.out.println(compare2);

        //语法糖而已，看靠文档：https://zhuanlan.zhihu.com/p/147719155
    }

}
