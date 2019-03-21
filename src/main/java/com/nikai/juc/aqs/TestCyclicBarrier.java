package com.nikai.juc.aqs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     栅栏用于等待其它线程，且会阻塞自己当前线程；
 *
 * 所有线程必须同时到达栅栏位置后，才能继续执行；
 * </pre>
 *
 * @author <a href="nikai.net.cn">nikai</a>
 * @version V1.0.0 2019/3/21 23:55
 */
class CyclicBarrierTaskTest implements Runnable {

    private CyclicBarrier cyclicBarrier;

    private int timeout;

    public CyclicBarrierTaskTest(CyclicBarrier cyclicBarrier, int timeout) {
        this.cyclicBarrier = cyclicBarrier;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        TestCyclicBarrier.print(" 正在running...");
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
            TestCyclicBarrier.print(" 到达栅栏处，等待其它线程到达");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        TestCyclicBarrier.print(" 所有线程到达栅栏处，继续执行各自线程任务...");
    }
}

public class TestCyclicBarrier {

    public static void print(String str) {
        SimpleDateFormat dfdate = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + dfdate.format(new Date()) + "]"
            + Thread.currentThread().getName() + str);
    }

    public static void main(String[] args) {
        int count = 5;

        ExecutorService es = Executors.newFixedThreadPool(count);

        CyclicBarrier barrier = new CyclicBarrier(count, new Runnable() {

            @Override
            public void run() {
                TestCyclicBarrier.print(" 所有线程到达栅栏处,可以在此做一些处理...");
            }
        });
        for (int i = 0; i < count; i++) {
            es.execute(new CyclicBarrierTaskTest(barrier, (i + 1) * 1000));
        }
    }

}