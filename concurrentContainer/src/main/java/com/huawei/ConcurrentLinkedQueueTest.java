package com.huawei;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLinkedQueueTest{
    public static  ConcurrentLinkedQueue<String> queue=new ConcurrentLinkedQueue<>();
public static void main(String[] args ){

    new Thread(ConcurrentLinkedQueueTest::run,"1").start();
    new Thread(ConcurrentLinkedQueueTest::run,"2").start();
    Lock lock=new ReentrantLock();

    System.out.println(queue.poll());
    System.out.println(queue.poll());
    System.out.println(queue.poll());

}

    private static void run() {
        queue.add("1111");
    }
}
