package com.huawei;


import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriteReadLockDemo {
   static  ReadWriteLock lock = new ReentrantReadWriteLock();
  static   HashMap<String, String> map = new HashMap<>();
    static Lock readLock = lock.readLock();
  static  Lock writeLock = lock.writeLock();

    public static void  put(String key, String value) {
        writeLock.lock();
        try {
            map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public static String get(String key) {
        readLock.lock();
        String value = null;
        try {
            value = map.get(key);
        } finally {
            readLock.unlock();
        }
        return value;
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            put("yl1","hhhh1");
            System.out.println(get("yl1"));
        }, "Thread1");
        Thread thread2 = new Thread(() -> {
            put("yl2","hhhh2");
            System.out.println(get("yl2"));
        }, "Thread2");
        thread1.start();
        thread2.start();
    }
}
