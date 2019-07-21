package com.suchaos.concurrency.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Fixed-delay
 *
 * @author suchao
 * @date 2019/7/17
 */
@Slf4j
public class TimerFixedDelayAndRateTest {
    static class LongRunningTask extends TimerTask {
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

    static class TestTask extends TimerTask {
        @Override
        public void run() {
            log.info("task finished");
        }
    }

    public static void main(String[] args) {
        log.info("start");
        Timer timer = new Timer();
        timer.schedule(new LongRunningTask(), 10);
        // timer.schedule(new TestTask(), 100, 1000);
        timer.scheduleAtFixedRate(new TestTask(), 100, 1000);
    }
}
