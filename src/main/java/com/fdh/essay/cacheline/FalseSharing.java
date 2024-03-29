package com.fdh.essay.cacheline;

/**
 * 使用了共享机制比没有使用共享机制，速度快了4倍左右。
 * <p>
 * 如上图所示，当生产者线程put一个元素到ArrayBlockingQueue时，putIndex会修改，从而导致消费者线程的缓存中的缓存行无效，需要从主存中重新读取。
 * <p>
 * 这种无法充分使用缓存行特性的现象，称为伪共享。
 * <p>
 * 对于伪共享，一般的解决方案是，增大数组元素的间隔使得由不同线程存取的元素位于不同的缓存行上，以空间换时间。
 *
 * 备注：在jdk1.8中，有专门的注解@Contended来避免伪共享，更优雅地解决问题。
 * 参考：https://tech.meituan.com/2016/11/18/disruptor.html
 * @author: fudaohui
 * @date: 2021/08/28 17:45
 */
public class FalseSharing implements Runnable {

    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    //    private static ValuePadding[] longs;
    private static ValueNoPadding[] longs;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            System.gc();
            final long start = System.currentTimeMillis();
            runTest(i);
            System.out.println("Thread num " + i + " duration = " + (System.currentTimeMillis() - start));
        }

    }

    private static void runTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
//        longs = new ValuePadding[NUM_THREADS];
//        for (int i = 0; i < longs.length; i++) {
//            longs[i] = new ValuePadding();
//        }

        longs = new ValueNoPadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new ValueNoPadding();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = 0L;
        }
    }

    public final static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14;
        protected long p15;
    }

    public final static class ValueNoPadding {
        // protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        // protected long p9, p10, p11, p12, p13, p14, p15;
    }
}
