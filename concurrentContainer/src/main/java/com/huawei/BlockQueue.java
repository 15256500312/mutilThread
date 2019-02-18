package com.huawei;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Queue;
import java.util.concurrent.*;

//阻塞队列实现生产者消费者模型（使用arrBlockqueue实现）
public class BlockQueue {
    public static   BlockingQueue<String> queue=new ArrayBlockingQueue<>(10);
    public static   BlockingQueue<String> squeue=new SynchronousQueue<>();
    public static   BlockingQueue<String> pqueue=new PriorityBlockingQueue<>();
    public static void main(String[] args ) {
       // Arr();
        //Syn();
        prority();

    }
    public static void Arr(){
        new Thread(()->{
            for(;;){

                try {
                   // Thread.currentThread().sleep(1000);
                    queue.offer("111", 2, TimeUnit.SECONDS);
                    System.out.println("队列中剩余的空间为："+queue.remainingCapacity());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"procedure").start();
        new Thread(()->{
            for(;;){
                try {
                    String s = queue.poll(2, TimeUnit.SECONDS);
                    System.out.println(s+"队列中剩余的空间为："+queue.remainingCapacity());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"procedure");
    }
    public static  void Syn(){
        new Thread(()->{
            for(;;){
                try {
                    Thread.currentThread().sleep(1000);
                    squeue.offer("111", 1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"procedure").start();
        new Thread(()->{
            try {
                for(;;){
                    String poll = squeue.poll(1, TimeUnit.SECONDS);
                    System.out.println(poll);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();
    }
    public static void prority(){
new Thread(()->{for (;;){
    pqueue.offer("1");
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    pqueue.offer("2");
    pqueue.offer("3");
}

}).start();
        new Thread(()->{
            try {
                for(;;)
                System.out.println(pqueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
