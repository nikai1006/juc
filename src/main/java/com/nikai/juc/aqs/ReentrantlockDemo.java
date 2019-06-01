package com.nikai.juc.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * juc com.nikai.juc.aqs
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 22:24 2019/5/27
 * @Modified By:
 */
public class ReentrantlockDemo {

    static Lock lock = new ReentrantLock();

    private static int count = 0;

    public synchronized static void incr() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        lock.lock();
        count++;
        lock.unlock();
    }

}
