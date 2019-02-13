package com.huawei.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * countDownLatch表示线程之间的调度
 * 实际运用场景：多线程加载sheet页面，当多个线程加载完sheet页后，才能让主线程进行下一步操作
 * （注意countDownLatch与join的区别）countDownLatch的2个主要方法：await和countDown
 * await表示等待，当countDown=0时线程将会被唤醒（每次调用countDown方法时，countDown将减少1）
 */
public class CountDownLatchTest {
public static void main(String[] args ) throws InterruptedException {
    //countDownLatch代替join方法
    //replaceJoin();
    //countDownLatch比join强的地方
    enhanceJoin();
}

    private static void enhanceJoin() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(3);

        Thread  thread1=new Thread(()->{
            System.out.println("sheet页1加载第一步");
            countDownLatch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sheet页1加载第二步");
        },"thread1");
        Thread  thread2=new Thread(()->{
            System.out.println("sheet页2加载第一步");
            countDownLatch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sheet页2加载第二步");
        },"thread2");
        Thread  thread3=new Thread(()->{
            System.out.println("sheet页3加载第一步");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sheet页3加载第二步");
        },"thread2");
        thread1.start();
        thread2.start();
        thread3.start();
        countDownLatch.await(2, TimeUnit.SECONDS);
        System.out.println("所有sheet页加载完，主线程开始执行下一步操作");

    }

    private static void replaceJoin() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(3);

        Thread  thread1=new Thread(()->{
            countDownLatch.countDown();
            System.out.println("sheet页1加载完成");
        },"thread1");
        Thread  thread2=new Thread(()->{
          countDownLatch.countDown();
            System.out.println("sheet页2加载完成");
        },"thread2");
        Thread  thread3=new Thread(()->{
            countDownLatch.countDown();
            System.out.println("sheet页3加载完成");
        },"thread2");
        thread1.start();
        thread2.start();
        thread3.start();
        countDownLatch.await();
        System.out.println("所有sheet页加载完，主线程开始执行下一步操作");
    }
}
