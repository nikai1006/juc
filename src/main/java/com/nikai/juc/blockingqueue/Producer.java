package com.nikai.juc.blockingqueue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

//生产者
public class Producer implements Runnable {
    private final BlockingQueue<String> fileQueue;

    public Producer(BlockingQueue<String> queue) {
        this.fileQueue = queue;

    }

    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(1000);
                String produce = this.produce();
                System.out.println(Thread.currentThread() + "生产：" + produce);
                fileQueue.put(produce);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String produce() {
        SimpleDateFormat dfdate = new SimpleDateFormat("HH:mm:ss");
        return dfdate.format(new Date());
    }

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

        for (int i = 0; i < 1; i++) {
            new Thread(new Producer(queue)).start();
        }
        for (int i = 0; i < 6; i++) {
            new Thread(new Consumer(queue)).start();
        }
    }
}

// 消费者
class Consumer implements Runnable {
    private final BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread() + "prepare 消费");
                System.out.println(Thread.currentThread() + "starting："
                        + queue.take());
                System.out.println(Thread.currentThread() + "end 消费");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}