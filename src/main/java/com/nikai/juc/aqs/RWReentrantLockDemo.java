package com.nikai.juc.aqs;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * juc com.nikai.juc.aqs 重入读写锁
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 22:28 2019/5/27
 * @Modified By:
 */
public class RWReentrantLockDemo {

    //排他的
//    共享锁 在同一时刻可以有多个线程获得锁
//    读锁 写锁 (读多写少)
    static Map<String, Object> cacheMap = new HashMap<String, Object>();

    static ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    static Lock read = rw.readLock();//读锁


    static Lock write = rw.writeLock();//写锁

    //缓存的更新和读取的时候
    public static final Object get(String key) {
        read.lock();//多锁
        try {
            return cacheMap.get(key);
        } finally {
            read.unlock();
        }
    }

    public static final Object set(String key, Object value) {
        write.lock();//写锁
        try {
            return cacheMap.put(key, value);
        } finally {
            write.unlock();
        }
    }


}
