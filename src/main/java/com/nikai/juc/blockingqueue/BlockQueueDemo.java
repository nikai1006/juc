package com.nikai.juc.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * juc com.nikai.juc.blockingqueue
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 23:42 2019/3/21
 * @Modified By:
 */
public class BlockQueueDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> integers = new ArrayBlockingQueue<Integer>(100);
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }

}
