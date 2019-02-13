package com.huawei.Exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * exchanger的原理以及运用：exchanger主要用于2个线程交换数据，当一个线程执行到exchanger方法之后
 * 会等待另一个线程也执行exchanger方法，exchanger常用于数据的校验工作
 */
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<String>();
    private static ExecutorService executorServicethreadPool= Executors.newFixedThreadPool(2);
    public static void main(String[] args ){
        executorServicethreadPool.execute(()->{
            String A = "统计的总数A";// A录入银行流水数据
            try {
                String B = exgr.exchange(A);
                System.out.println("B线程："+B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorServicethreadPool.execute(()->{

            String B = "统计的总数B";// A录入银行流水数据
            try {

                String A = exgr.exchange(B);
                System.out.println("A线程："+A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
