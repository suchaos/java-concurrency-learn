package com.suchaos.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 等待-通知机制
 *
 * @author suchao
 * @date 2019/7/9
 */
public class WaitNotifyTest3 {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.addOne();
            }
        }, "thread-add");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter.minusOne();
            }
        }, "thread-minus");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    static class Counter {
        private int counter;

        private final Lock lock = new ReentrantLock();

        private final Condition lessThenOne = lock.newCondition();

        private final Condition greaterThenZero = lock.newCondition();

        Counter(int counter) {
            this.counter = counter;
        }

        void addOne() {
            lock.lock();
            try {
                while (this.counter > 0) {
                    try {
                        greaterThenZero.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.counter++);
                lessThenOne.signalAll();
            } finally {
                lock.unlock();
            }
        }

        void minusOne() {
            lock.lock();
            try {
                while (this.counter <= 0) {
                    try {
                        lessThenOne.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.counter--);
                greaterThenZero.signalAll();
            } finally {
                lock.unlock();
            }

        }
    }

}


