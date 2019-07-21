package com.suchaos.concurrency.completable;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * CompletableFuture
 *
 * @author suchao
 * @date 2019/7/18
 */
@Slf4j
public class CompletableFutureTest1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Supplier<Integer> task = () -> {
            log.info("task start");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer result = ThreadLocalRandom.current().nextInt(20, 2000);
            return result;
        };

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(task).whenCompleteAsync((result, ex) -> {
            if (result != null) {
                log.info(String.valueOf(result));
            }
            if (ex != null) {
                log.error("error: ", ex);
            }
        });

        completableFuture.join();
    }
}
