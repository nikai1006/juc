package com.nikai.juc.fork;

import java.util.concurrent.RecursiveAction;

/**
 * juc com.nikai.juc.fork
 *
 * @author: nikai
 * @Description:
 * @Date: Create in 23:23 2019/3/21
 * @Modified By:
 */
public class MyRecursiveAction extends RecursiveAction {

    private int beginValue;
    private int endValue;

    public MyRecursiveAction(int beginValue, int endValue) {
        this.beginValue = beginValue;
        this.endValue = endValue;
    }

    @Override
    protected void compute() {
        System.out.println(Thread.currentThread().getName() + "========================");
        if (endValue - beginValue > 2) {
            int middelNum = (beginValue + endValue) / 2;
            MyRecursiveAction leftAction = new MyRecursiveAction(beginValue, middelNum);
            MyRecursiveAction rightAction = new MyRecursiveAction(middelNum + 1, endValue);
            RecursiveAction.invokeAll(leftAction, rightAction);
        } else {
            System.out.println("打印组合为：" + beginValue + "-" + endValue);
        }
    }
}
