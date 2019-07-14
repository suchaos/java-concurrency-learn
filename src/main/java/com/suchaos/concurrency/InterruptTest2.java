package com.suchaos.concurrency;

/**
 * interrupt
 *
 * @author suchao
 * @date 2019/7/14
 */
public class InterruptTest2 {

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

        synchronized void method1() {
            System.out.println("method1 get lock");
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("method1 interrupted");
                    break;
                }
            }
        }

        synchronized void method2() {
            System.out.println("method2 get lock");
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("method2 interrupted");
                    break;
                }
            }
        }
    }

}
