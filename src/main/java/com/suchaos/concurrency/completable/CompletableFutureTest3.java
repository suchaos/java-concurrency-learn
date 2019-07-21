package com.suchaos.concurrency.completable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CompletableFuture
 *
 * @author suchao
 * @date 2019/7/18
 */
@Slf4j
public class CompletableFutureTest3 {

    public static void main(String[] args) {
        // 任务1：洗水壶 -> 烧开水
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            log.info("T1：洗水壶");
            sleep(1, TimeUnit.SECONDS);
            log.info("T1：烧开水");
            sleep(15, TimeUnit.SECONDS);
        });
        // 任务2：洗茶壶 -> 洗茶杯 -> 拿茶叶
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            log.info("T2：洗茶壶");
            sleep(1, TimeUnit.SECONDS);
            log.info("T2：洗茶杯");
            sleep(2, TimeUnit.SECONDS);
            log.info("T2：拿茶叶");
            sleep(1, TimeUnit.SECONDS);
            return "龙井茶";
        });
        // 任务3: 任务1和任务2完成后执行：泡茶
        CompletableFuture<String> task3 = task1.thenCombineAsync(task2, (__, tf) -> {
            log.info("T1: 拿到茶叶：" + tf);
            log.info("T1: 泡茶");
            return "上茶：" + tf;
        });
        System.out.println("1111");
        sleep(28, TimeUnit.SECONDS);
        //System.out.println(task3.join());
        System.out.println("end");
    }

    static void sleep(int t, TimeUnit unit) {
        try {
            unit.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
