package com.suchaos.concurrency;

/**
 * @author suchao
 * @date 2019/7/8
 */
public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    void transfer(Account target, int amt) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.balance > amt) {
            this.balance -= amt;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            target.balance += amt;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        Account accountA = new Account(200);
        Account accountB = new Account(200);
        Account accountC = new Account(200);

        Runnable runnable1 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accountA.transfer(accountB, 100);
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accountB.transfer(accountC, 100);
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("account A " + accountA);
        System.out.println("account B " + accountB);
        System.out.println("account C " + accountC);
    }
}
