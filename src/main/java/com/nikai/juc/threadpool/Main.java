package com.nikai.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * juc com.nikai.juc.threadpool
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 16:01 2019/6/1
 * @Modified By:
 */
public class Main {

    public static ExecutorService executorService = new MyExecutors(1, 1, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) {
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("--------------------");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });

    }

}
