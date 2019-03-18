package com.example.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//基于队列同步器实现可重入式锁
public class MyReentryLock implements Lock{
    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return  new MyReentryLock.InnerLock().tryAcquire(0);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
      new MyReentryLock.InnerLock().tryRelease(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static class  InnerLock extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
            //AQS中的int值，当没有线程获得锁时为0
            int state = getState();
            Thread t = Thread.currentThread();
            //第一个线程进入
            if (state == 0) {
                //由于可能有多个线程同时进入这里，所以需要使用CAS操作保证原子性，这里不会出现线程安全性问题
                if (compareAndSetState(0, 1)) {
                    //设置获得独占锁的线程
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            } else if (getExclusiveOwnerThread() == t) {
                //已经获得锁的线程和当前线程是同一个，那么state加一，由于不会有多个线程同时进入这段代码块，所以没有线程安全性问题，可以直接使用setState方法
                setState(state + 1);
                return true;
            }
            //其他情况均无法获得锁
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {

            //锁的获取和释放使一一对应的，那么调用此方法的一定是当前线程，如果不是，抛出异常
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new RuntimeException();
            }

            int state = getState() - arg;

            boolean flag = false;

            //如果state减一后的值为0了，那么表示线程重入次数已经降低为0，可以释放锁了。
            if (state == 0) {
                setExclusiveOwnerThread(null);
                flag = true;
            }

            //无论是否释放锁，都需要更改state的值
            setState(state);

            //只有state的值为0了，才真正释放了锁，返回true
            return flag;
        }
    }
}
