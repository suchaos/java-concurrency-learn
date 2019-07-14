package com.suchaos.concurrency;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 的使用
 *
 * @author suchao
 * @date 2019/7/13
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 5; i++) {
            new SecurityCheckThread("Thread " + i, i, semaphore).start();
        }
    }

    private static class SecurityCheckThread extends Thread {
        private int seq;
        private Semaphore semaphore;

        public SecurityCheckThread(String name, int seq, Semaphore semaphore) {
            super(name);
            this.seq = seq;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                this.semaphore.acquire();
                System.out.println("No." + this.seq + " 乘客，正在查验中");

                // 假设号码是整除 2 的人是身份可疑的人，需要花时间来案件
                if (this.seq % 2 == 0) {
                    Thread.sleep(1000);
                    System.out.println("No." + this.seq + " 乘客，身份可疑，不能出国！");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.semaphore.release();
                System.out.println("No." + this.seq + " 乘客已完成服务");
            }
        }
    }

}
