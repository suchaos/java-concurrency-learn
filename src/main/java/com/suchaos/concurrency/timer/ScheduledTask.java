package com.suchaos.concurrency.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * ScheduledTask
 *
 * @author suchao
 * @date 2019/7/18
 */
@Slf4j
public class ScheduledTask {
    static class LongRunningTask implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("long running finished");
        }
    }

    static class TestTask implements Runnable {
        @Override
        public void run() {
            log.info("task start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("task end");
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.schedule(new LongRunningTask(), 100, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new TestTask(), 100, 1000, TimeUnit.MILLISECONDS);
        // scheduledExecutorService.scheduleWithFixedDelay(new TestTask(), 100, 1000, TimeUnit.MILLISECONDS);
    }


}
