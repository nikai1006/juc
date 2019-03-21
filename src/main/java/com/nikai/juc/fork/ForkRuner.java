package com.nikai.juc.fork;

import java.util.concurrent.ForkJoinPool;

/**
 * juc com.nikai.juc.fork
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 23:27 2019/3/21
 * @Modified By:
 */
public class ForkRuner {

    public static void main(String[] args) throws Exception{
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new MyRecursiveAction(1,100));
        Thread.sleep(5000);
    }

}
