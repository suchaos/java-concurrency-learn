package com.suchaos.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * interrupt
 *
 * @author suchao
 * @date 2019/7/14
 */
public class InterruptTest1 {

    public static void main(String[] args) throws InterruptedException {
        TestInterrupt testInterrupt = new TestInterrupt();

        Thread thread1 = new Thread(testInterrupt::method1, "Thread Method1");


        Thread thread2 = new Thread(testInterrupt::method2, "Thread Method2");

        thread1.start();
        Thread.sleep(100);
        thread2.start();

        Thread.sleep(1000);
        thread2.interrupt();

//        Thread.sleep(1000);
//        thread2.interrupt();
    }

    static class TestInterrupt {

        private final Lock lock = new ReentrantLock();

        void method1() {
            lock.lock();
            try {
                System.out.println("method1 get lock");
                while (true) {
                    if (Thread.interrupted()) {
                        System.out.println("method1 interrupted");
                        break;
                    }
                }

            } finally {
                lock.unlock();
            }
        }

        void method2() {
            try {
                lock.lockInterruptibly();
                try {
                    System.out.println("method2 get lock");
                    while (true) {
                        if (Thread.interrupted()) {
                            System.out.println("method2 interrupted");
                            break;
                        }
                    }

                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("method2 interrupted");
                e.printStackTrace();
            }
        }
    }

}
