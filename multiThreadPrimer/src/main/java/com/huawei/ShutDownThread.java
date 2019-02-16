package com.huawei;

import sun.jvm.hotspot.runtime.VM;

import javax.xml.ws.soap.MTOM;

/**
 * 中断线程的2个常用方法：
 * 1.定义一个volatile型变量，在一个线程中改变这个变量的值，就可以关闭另外的线程
 * 2.使用jdk自带的isInterrupt和interrupt
 * 不要使用stop应为这个方法无法释放资源，容易导致死锁
 */

public class ShutDownThread {
    //用volatile保证可见性
    private volatile static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
       // method1();
        method2();
    }
    private static void method2() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
               //业务逻辑
                System.out.println(1111);
            }
            return;
        }, "Thread1");
        thread1.start();
        Thread.currentThread().sleep(2000);
        thread1.interrupt();

    }
    private static void method1() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (flag) {
                //业务逻辑
                System.out.println(1111);
            }

        }, "Thread1");
        thread1.start();
        Thread.currentThread().sleep(1000);
        flag=false;
    }

}
