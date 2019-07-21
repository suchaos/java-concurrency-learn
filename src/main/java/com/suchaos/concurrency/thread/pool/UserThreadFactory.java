package com.suchaos.concurrency.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 自定义的 Thread Factory
 *
 * @author suchao
 * @date 2019/7/16
 */
public class UserThreadFactory implements ThreadFactory {
    private final String namePrefix;

    private final AtomicInteger nextId = new AtomicInteger();

    public UserThreadFactory(String whatFeatureOfGroup) {
        this.namePrefix = "UserThreadFactory's " + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = this.namePrefix + nextId.getAndIncrement();
        return new Thread(task, name);
    }
}

class Task implements Runnable {

    private final AtomicLong count = new AtomicLong(0);

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running, count = " + count.getAndIncrement());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {

        }
    }
}

class UserRejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        System.out.println("task rejected. " + executor.toString());
    }
}
