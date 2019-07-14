package com.suchaos.concurrency;

/**
 * 开始学习并发课程
 *
 * @author suchao
 * @date 2019/4/24
 */
public class MyTest1 {

    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();

        synchronized (object) {
            object.wait();
        }
    }
}
