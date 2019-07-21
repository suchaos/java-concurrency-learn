package com.suchaos.concurrency.completion;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * CompletionService
 *
 * @author suchao
 * @date 2019/7/19
 */
@Slf4j
public class CompletionServiceTest1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(() -> {
            log.info("task1 start");
            sleep(3, TimeUnit.SECONDS);
            log.info("task1 end");
            return 1;
        });

        completionService.submit(() -> {
            log.info("task2 start");
            sleep(2, TimeUnit.SECONDS);
            log.info("task2 end");
            return 2;
        });

        completionService.submit(() -> {
            log.info("task3 start");
            sleep(1, TimeUnit.SECONDS);
            log.info("task3 end");
            return 3;
        });

        for (int i = 0; i < 3; i++) {
            System.out.println(completionService.take().get());
        }

        executorService.shutdown();
    }

    private static void sleep(long s, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
