package com.suchaos.concurrency.thread.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * UserThreadPool
 *
 * @author suchao
 * @date 2019/7/16
 */
public class UserThreadPool {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(2);

        UserThreadFactory factory = new UserThreadFactory("苏超的机房");

        UserRejectHandler handler = new UserRejectHandler();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 3, 10,
                TimeUnit.SECONDS, queue, factory, handler);

        Runnable task = new Task();

        for (int i = 0; i < 4; i++) {
            executor.execute(task);
        }
        executor.shutdown();
    }
}
