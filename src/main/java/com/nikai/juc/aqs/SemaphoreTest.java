package com.nikai.juc.aqs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 控制同时执行某个指定操作的数量，常用于实现资源池，如数据库连接池，线程池...
 * 以Semaphore为例，其内部维护一组资源，可以通过构造函数指定数目，其它线程在执行的时候，可以通过acquire方法获取资源，有的话，继续执行（使用结束后释放资源），没有资源的话将阻塞直到有其它线程调用release方法释放资源；
 *
 * 举个例子，如下代码，十个线程竞争三个资源，一开始有三个线程可以直接运行，剩下的七个线程只能阻塞等到其它线程使用资源完毕才能执行；
 * </pre>
 *
 * @author <a href="nikai.net.cn">nikai</a>
 * @version V1.0.0 2019/3/21 23:54
 */
public class SemaphoreTest {

    public static void print(String str) {
        SimpleDateFormat dfdate = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + dfdate.format(new Date()) + "]" + Thread.currentThread().getName() + str);
    }

    public static void main(String[] args) {
        // 线程数目
        int threadCount = 10;
        // 资源数目
        Semaphore semaphore = new Semaphore(3);

        ExecutorService es = Executors.newFixedThreadPool(threadCount);

        // 启动若干线程
        for (int i = 0; i < threadCount; i++) {
            es.execute(new ConsumeResourceTask((i + 1) * 1000, semaphore));
        }
    }
}

class ConsumeResourceTask implements Runnable {

    private Semaphore semaphore;
    private int sleepTime;

    /**
     *
     */
    public ConsumeResourceTask(int sleepTime, Semaphore semaphore) {
        this.sleepTime = sleepTime;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            //获取资源
            semaphore.acquire();
            SemaphoreTest.print(" 占用一个资源...");
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            SemaphoreTest.print(" 资源使用结束，释放资源");
            //释放资源
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}