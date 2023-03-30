package com.fdh.essay.lambda;

public class AnonymousInnerClassTest {


    private void testAnonymousInnerClass(int number, DemoFunctionalInterface demoFunctionalInterface) {
        boolean result = demoFunctionalInterface.test(number);
        System.out.println(result);
//        System.out.println(demoFunctionalInterface);
    }

    public static void main(String[] args) {
        AnonymousInnerClassTest anonymousInnerClassTest = new AnonymousInnerClassTest();

        anonymousInnerClassTest.testAnonymousInnerClass(2,new DemoFunctionalInterface() {
            @Override
            public boolean test(Integer x) {
                if (x > 1) {
                    return true;
                }
                return false;
            }
        });


        /**
         * interface接口是不可以直接new的，此处实际上是new了一个类名的接口实现类，因为这里面
         * 有{}括号，代表实现了里面的test方法
         */
//        anonymousInnerClassTest.testAnonymousInnerClass(2, new DemoFunctionalInterface() {
//            @Override
//            public boolean test(Integer x) {
//                if (x > 1) {
//                    return true;
//                }
//                return false;
//            }
//        });
        /**
         * 参数2传递实际上看着一个方法，{}里面是函数式接口的唯一方法的调用的内容，即test里面的执行内容
         */
//        anonymousInnerClassTest.testAnonymousInnerClass(2,number -> {
//            if (number > 1) {
//                return true;
//            }
//            return false;
//        });
    }
}
