package com.huawei.countDownLatch;

/**
 * 此处使用join完成多线程之间的调度
 */
public class JoinTest {
    public static void main(String[] args ) throws InterruptedException {
        Thread  thread1=new Thread(()->{
            System.out.println("sheet页1加载完成");
        },"thread1");
        Thread  thread2=new Thread(()->{
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sheet页2加载完成");
        },"thread2");
        Thread  thread3=new Thread(()->{
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sheet页3加载完成");
        },"thread2");
        thread1.start();
        thread2.start();
        thread3.start();
        thread3.join();
        System.out.println("所有sheet页加载完，主线程开始执行下一步操作");
    }
}
