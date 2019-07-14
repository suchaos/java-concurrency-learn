package com.suchaos.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 等待-通知机制
 *
 * @author suchao
 * @date 2019/7/9
 */
public class WaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        Account src = new Account(10000);
        Account target = new Account(10000);

        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                src.transfer(target, 1);
                target.transfer(src, 1);
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("src: " + src);
        System.out.println("src: " + target);
    }

    static class Account {
        private Integer balance;

        public Account(Integer balance) {
            this.balance = balance;
        }

        synchronized Integer getBalance() {
            return this.balance;
        }

        void transfer(Account target, int amt) {
            Allocator.getInstance().apply(this, target);

            synchronized (this) {
                synchronized (target) {
                    if (this.balance >= amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }

            Allocator.getInstance().free(this, target);
        }

        @Override
        public String toString() {
            return "Account{" +
                    "balance=" + balance +
                    '}';
        }
    }

    static class Allocator {

        private List<Object> als = new ArrayList<>();

        private Allocator() {

        }

        // 一次性申请所有资源
        synchronized void apply(Object from, Object to) {
            while (als.contains(from) || als.contains(to)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            als.add(from);
            als.add(to);
        }

        // 归还资源
        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
            this.notifyAll();
        }

        public static Allocator getInstance() {
            return AllocatorSingle.install;
        }

        static class AllocatorSingle {
            static Allocator install = new Allocator();
        }

    }

}


