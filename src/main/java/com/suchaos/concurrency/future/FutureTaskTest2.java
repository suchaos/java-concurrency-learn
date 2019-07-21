package com.suchaos.concurrency.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 实现最优的 “烧水泡茶” 程序
 *
 *
 *  线程T1：洗水壶（1min）  ----->    烧开水（15min）  -------->    泡茶
 *                                                                 ^
 *                                                                 |
 *                                                                 |
 *  线程T2：洗茶壶（1min）--> 洗茶杯（2min）--> 拿茶叶（1min)---------
 *
 *
 * @author suchao
 * @date 2019/7/17
 */
@Slf4j
public class FutureTaskTest2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        ExecutorService executorService = Executors.newFixedThreadPool(2,
                new ThreadFactoryBuilder().setNameFormat("tea-pool-%d").build());

        executorService.submit(ft1);
        executorService.submit(ft2);

        String result = ft1.get();
        executorService.shutdown();
        log.info(result);
    }


    private static class T1Task implements Callable<String> {

        private FutureTask<String> tf2;

        public T1Task(FutureTask<String> tf2) {
            this.tf2 = tf2;
        }

        @Override
        public String call() throws Exception {
            log.info("T1：洗水壶...");
            TimeUnit.SECONDS.sleep(1);
            log.info("T1：烧开水...");
            TimeUnit.SECONDS.sleep(15);
            String tea = this.tf2.get();
            log.info("T1：泡{}中...", tea);
            TimeUnit.SECONDS.sleep(1);
            return "泡好的龙井茶";
        }
    }

    private static class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("T2：洗茶壶...");
            TimeUnit.SECONDS.sleep(1);
            log.info("T2：洗茶杯...");
            TimeUnit.SECONDS.sleep(2);
            log.info("T2：拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "龙井茶";
        }
    }
}
