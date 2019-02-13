package com.huawei.cyclicBarrier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * cyclicBarrier在项目中的运用场景：
 * 多个线程到达同一个屏障，然后将每个线程中的数据统计出来，做进一步的处理
 * 应用场景：每个sheet页开启一个线程（例如每个sheet页代表一个网源，每个sheet页中的一行代表一个子架，
 * 其中有表示单板个数的字段，需要统计每个sheet页中的单板个数，然后将所有单板个数相加，
 * 统计所有网源上面的单板总个数）
 */
public class CycliBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new CountAllBoardNumber());
    /**
     * 此处假设有3个sheet页，所以选用固定线程池，开启3个线程
     */
    private static Executor executor = Executors.newFixedThreadPool(3);

    private static ConcurrentHashMap<String, Integer> boardNumberPerNet = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        countBoardNumberPerNet();
    }

    private static void countBoardNumberPerNet() {
        for (int i = 0; i < 3; i++) {
            executor.execute(() -> {
                boardNumberPerNet.put(Thread.currentThread().getName(), 100);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static class CountAllBoardNumber implements Runnable {
        @Override
        public void run() {
            int result = 0;
            for (Map.Entry<String, Integer> sheet : boardNumberPerNet.entrySet()
                    ) {
                System.out.println(sheet.getKey() + sheet.getValue());
                result += sheet.getValue();
            }
            System.out.println(result);
        }
    }
}
