package com.suchaos.concurrency.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * use FutureTask
 *
 * @author suchao
 * @date 2019/7/17
 */
@Slf4j
public class FutureTaskTest1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            log.info(Thread.currentThread().getName() + " is running");
            Thread.sleep(100000);
            return 1 + 2;
        });

        ExecutorService executorService = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder().setNameFormat("suchao-future-task-pool-%d").build());
        executorService.submit(futureTask);
        System.out.println(futureTask.get());
    }
}
