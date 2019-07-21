package com.suchaos.concurrency.completable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture
 *
 * @author suchao
 * @date 2019/7/18
 */
@Slf4j
public class CompletableFutureTest4 {

    public static void main(String[] args) {

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            log.info("task1");
            sleep(3, TimeUnit.SECONDS);
            return "task1";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            log.info("task2");
            sleep(2, TimeUnit.SECONDS);
            return "task2";
        });

        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            log.info("task3");
            sleep(2, TimeUnit.SECONDS);
            return "task3";
        });

        CompletableFuture<Void> task4 = CompletableFuture.runAsync(() -> {
            log.info("task3");
            sleep(1, TimeUnit.SECONDS);
        });


        // CompletableFuture.allOf(task1, task2, task3).join();
        // CompletableFuture.anyOf(task1, task2, task3).join();

        String s1, s2, s3;
        //task1.thenCombine(task2, (a, b) -> {}).thenCombine()
    }

    static void sleep(int t, TimeUnit unit) {
        try {
            unit.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
