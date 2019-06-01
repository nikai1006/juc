package com.nikai.juc.threadpool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * juc com.nikai.juc.threadpool
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 15:49 2019/6/1
 * @Modified By:
 */
public class MyExecutors extends ThreadPoolExecutor {

    private Map<String, Date> startTime = new HashMap<String, Date>();

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyExecutors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void shutdown() {
        System.out.println("已经完成的线程数量" + this.getCompletedTaskCount());
        System.out.println("活跃的线程数" + this.getActiveCount());
        System.out.println("当前排队的线程数" + this.getQueue().size());
        super.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTime.put(String.valueOf(r.hashCode()), new Date());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Date start = startTime.remove(String.valueOf(r.hashCode()));
        Date finish = new Date();
        long dif = finish.getTime() - start.getTime();
        System.out.println("任务耗时：" + dif);
        System.out.println("最大允许的线程数：" + this.getMaximumPoolSize());
        System.out.println("线程的空闲时间：" + this.getKeepAliveTime(TimeUnit.MILLISECONDS));
        System.out.println("任务总数：" + this.getTaskCount());
        super.afterExecute(r, t);
    }
}
