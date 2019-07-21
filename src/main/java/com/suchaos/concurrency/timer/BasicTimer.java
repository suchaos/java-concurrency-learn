package com.suchaos.concurrency.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer
 *
 * @author suchao
 * @date 2019/7/17
 */
@Slf4j
public class BasicTimer {
    static class DelayTask extends TimerTask {

        @Override
        public void run() {
            log.info("delayed task");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        log.info("start");
        timer.schedule(new DelayTask(), 1000);
        Thread.sleep(2000);
        timer.cancel();
    }
}
