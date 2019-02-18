package com.huawei;

import com.huawei.domain.Resource;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//condition接口实现生产者消费者
public class ProducerAndConsumer {
   static Resource resource=new Resource();
   static Lock lock=new ReentrantLock();
   static Condition condition=lock.newCondition();
    public static void main(String[] args ){
        resource.setFlag(true);
    new Thread(ProducerAndConsumer::run,"proceder").start();
        new Thread(ProducerAndConsumer::run1,"consumer").start();
    }

    private static void run() {
        lock.lock();
        try {
            for (; ; ) {
                Thread.sleep(1000);
                if(resource.getFlag()){
                    resource.setName("111");
                    resource.setAge(11);
                }
                resource.setFlag(false);
                condition.signalAll();
                condition.await();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    private static void run1() {
        lock.lock();
        try {
            for (; ; ) {
                if(!resource.getFlag()){
                    System.out.println(resource);
                    resource.setFlag(true);
                }
                condition.signalAll();
                condition.await();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
