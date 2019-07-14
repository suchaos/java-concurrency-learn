package com.suchaos.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 简单实用
 *
 * @author suchao
 * @date 2019/7/14
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {

    }
}


/**
 * TaskPortion 表示分解的任务，所有任务都完成才可以继续向下走
 */
class TaskPortion implements Runnable {

    private static int count = 0;

    private final int id = count++;

    private static Random random = ThreadLocalRandom.current();

    private final CountDownLatch countDownLatch;

    TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            doWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        System.out.println(this + " completed");
    }

    @Override
    public String toString() {
        return "TaskPortion{" +
                "id=" + id +
                '}';
    }
}

/**
 * 表示系统中必须等待的部分，它要等到问题的初始化完成为止
 */
class WaitingTask implements Runnable {

    private static int count = 0;

    private final int id = count++;

    private static Random random = ThreadLocalRandom.current();

    private final CountDownLatch countDownLatch;

    WaitingTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }

    @Override
    public String toString() {
        return "WaitingTask{" +
                "id=" + id +
                '}';
    }
}
