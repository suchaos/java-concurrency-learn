package com.suchaos.concurrency.completable;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * CompletableFuture
 *
 * @author suchao
 * @date 2019/7/18
 */
@Slf4j
public class CompletableFutureTest2 {

    public static void main(String[] args) {
//        Runnable taskA = () -> {
//            log.info("taskA");
//            throw new RuntimeException();
//        };
//        Runnable taskB = () -> log.info("taskB");
//        Runnable taskC = () -> log.info("taskC");
//
//        CompletableFuture.runAsync(taskA).thenRun(taskB).thenRun(taskC).join();

        AtomicInteger nextId = new AtomicInteger();
        CompletableFuture.supplyAsync(() -> {
            Integer id = nextId.getAndIncrement();
            log.info("first task: " + id);
            return id;
        }).thenCompose(id -> {
            log.info("task 2 get id from first task: " + id);
            return CompletableFuture.runAsync(() -> log.info("hello"));
        }).join();
    }
}
