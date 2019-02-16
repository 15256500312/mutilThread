package com.huawei;

public class DeadLock {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (lock1){
                //睡眠2秒保证另外线程取到锁
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println(111);
                }
            }
        }, "Thread1");
        Thread thread2 = new Thread(() -> {
            synchronized (lock2){
                //睡眠2秒保证另外线程取到锁
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println(222);
                }
            }
        }, "Thread1");
        thread1.start();
        thread2.start();
    }
}
