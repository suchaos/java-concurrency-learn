package com.suchaos.concurrency.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 抛出异常时，不会导致所有后续任务全部取消
 *
 * @author suchao
 * @date 2019/7/18
 */
@Slf4j
public class ScheduledTaskExceptionDemo {
    static class ExceptionTask implements Runnable {
        @Override
        public void run() {
            log.info("running start");
//            try {
//                TimeUnit.SECONDS.sleep(5);
//                throw new RuntimeException();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            throw new RuntimeException();
            // log.info("running finished");
        }
    }

    static class NormalTask implements Runnable {
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
        scheduledExecutorService.schedule(new ExceptionTask(), 100, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new ExceptionTask(), 100, 1000, TimeUnit.MILLISECONDS);
        // scheduledExecutorService.scheduleWithFixedDelay(new TestTask(), 100, 1000, TimeUnit.MILLISECONDS);
    }
}
