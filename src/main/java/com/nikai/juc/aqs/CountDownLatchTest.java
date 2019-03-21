package com.nikai.juc.aqs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     可以让一个线程等待一组事件发生后（不一定要线程结束）继续执行；
 *
 * 以CountDownLatch为例，内部包含一个计数器，一开始初始化为一个整数（事件个数），发生一个事件后，调用countDown方法，计数器减1，await用于等待计数器为0后继续执行当前线程；
 *
 * 此外，FutureTask也可用作闭锁，其get方法会等待任务完成后返回结果，否则一直阻塞直到任务完成；
 * </pre>
 *
 * @author <a href="nikai.net.cn">nikai</a>
 * @version V1.0.0 2019/3/21 23:52
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            es.execute(new TaskTest((i + 1) * 1000, latch));
        }

        try {
            CountDownLatchTest.print(" waiting...");
            //主线程等待其它事件发生
            latch.await();
            //其它事件已发生，继续执行主线程
            CountDownLatchTest.print(" continue。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            es.shutdown();
        }
    }

    public static void print(String str) {
        SimpleDateFormat dfdate = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + dfdate.format(new Date()) + "]" + Thread.currentThread().getName() + str);
    }
}

class TaskTest implements Runnable {

    private CountDownLatch latch;
    private int sleepTime;

    /**
     *
     */
    public TaskTest(int sleepTime, CountDownLatch latch) {
        this.sleepTime = sleepTime;
        this.latch = latch;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            CountDownLatchTest.print(" is running。");
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            CountDownLatchTest.print(" finished。");
            //计数器减减
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
