package com.suchaos.concurrency;

/**
 * interrupt
 *
 * @author suchao
 * @date 2019/7/14
 */
public class InterruptTest3 {

    public static void main(String[] args) throws InterruptedException {
        TestInterrupt testInterrupt = new TestInterrupt();
        TestInterrupt testInterrupt2 = new TestInterrupt();

        Thread thread1 = new Thread(() -> testInterrupt.method1(testInterrupt2), "Thread Method1");


        Thread thread2 = new Thread(() -> testInterrupt.method2(testInterrupt2), "Thread Method2");

        thread1.start();
        Thread.sleep(100);
        thread2.start();

        Thread.sleep(1000);
        thread1.interrupt();

        Thread.sleep(1000);
        thread2.interrupt();
    }

    static class TestInterrupt {

        void method1(TestInterrupt other) {
            synchronized (this) {
                System.out.println("method1 get lockA, trying to get LockB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (other) {
                    System.out.println("method1 get lockB");
                    while (true) {
                        if (Thread.interrupted()) {
                            System.out.println("method1 interrupted");
                            break;
                        }
                    }
                }
            }
        }

        void method2(TestInterrupt other) {
            synchronized (other) {
                System.out.println("method2 get lockB, trying to get LockA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    while (true) {
                        System.out.println("method2 get lockA");
                        if (Thread.interrupted()) {
                            System.out.println("method2 interrupted");
                            break;
                        }
                    }
                }
            }
        }
    }

}
