package com.suchaos.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 等待-通知机制
 *
 * @author suchao
 * @date 2019/7/9
 */
public class WaitNotifyTest2 {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);

        Runnable addOne = () -> {
            for (int i = 0; i < 30; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.addOne();
            }
        };

        Runnable minusOne = () -> {
            for (int i = 0; i < 30; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.minusOne();
            }
        };

        Thread thread1 = new Thread(addOne);
        Thread thread2 = new Thread(minusOne);
        Thread thread3 = new Thread(minusOne);
        Thread thread4 = new Thread(addOne);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    static class Counter {
        private int counter;

        Counter(int counter) {
            this.counter = counter;
        }

        synchronized void addOne() {
            while (this.counter > 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(this.counter++);
            this.notifyAll();
        }

        synchronized void minusOne() {
            while (this.counter <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(this.counter--);
            this.notifyAll();
        }
    }

}


