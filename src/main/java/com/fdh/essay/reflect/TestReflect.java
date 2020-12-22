package com.fdh.essay.reflect;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射和new 的性能对比
 * https://juejin.cn/post/6844904098207105038
 *
 * 到现在为止，我们已经把反射生成实例的所有流程都搞清楚了。回到文章开头的问题，我们现在反思下，反射性能低么？为什么？
 *
 * 反射调用过程中会产生大量的临时对象，这些对象会占用内存，可能会导致频繁 gc，从而影响性能。
 * 反射调用方法时会从方法数组中遍历查找，并且会检查可见性等操作会耗时。
 * 反射在达到一定次数时，会动态编写字节码并加载到内存中，这个字节码没有经过编译器优化，也不能享受JIT优化。
 * 反射一般会涉及自动装箱/拆箱和类型转换，都会带来一定的资源开销。
 *
 * 在Android中，我们可以在某些情况下对反射进行优化。举个例子，EventBus 2.x 会在 register 方法运行时，遍历所有方法找到回调方法；而EventBus 3.x 则在编译期间，将所有回调方法的信息保存的自己定义的 SubscriberMethodInfo 中，这样可以减少对运行时的性能影响。
 * 本文的结论如下：
 *
 * 不要在性能敏感的应用中，频繁调用反射。
 * 如果反射执行的次数小于1000这个数量级，反射的耗时实际上与正常调用无太大差异。
 * 反射对内存占用还有一定影响的，在内存敏感的场景下，谨慎使用反射。
 *
 * 作者：orzangleli
 * 链接：https://juejin.cn/post/6844904098207105038
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
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
