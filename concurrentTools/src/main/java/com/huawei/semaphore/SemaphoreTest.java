package com.huawei.semaphore;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * semaphore的使用
 * semphore的使用场景：线程池技术（semaphore的核心作用是限流）
 */
public class SemaphoreTest {
    private static int ThreadNumber=30;
    private static Executor executorPool= Executors.newFixedThreadPool(ThreadNumber);
    private static Semaphore semaphore=new Semaphore(10);
    public static void main(String[] args ){
    for (int i=0;i<ThreadNumber;i++){
        executorPool.execute(()->{

           try {
               semaphore.acquire();
               System.out.println(Thread.currentThread().getName()+"---正在执行");
               System.out.println("剩余资源数目："+semaphore.availablePermits());
              Thread.currentThread().sleep(3000);
               semaphore.release();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }


}
        );
    }
    }

}
