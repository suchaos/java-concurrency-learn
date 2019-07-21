package com.suchaos.concurrency.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 简化的线程池，仅用了说明工作原理
 *
 * @author suchao
 * @date 2019/7/17
 */
public class MyThreadPool {
    // 利用阻塞队列实现 生产者--消费者 模式
    BlockingQueue<Runnable> workQueue;
    // 保存内部工作线程
    List<WorkerThread> threads = new ArrayList<>();

    // 构造方法
    MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        // 创建工作线程
        for (int i = 0; i < poolSize; i++) {
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            this.workQueue.add(workerThread);
        }
    }

    // 提交任务
    void execute(Runnable command) throws InterruptedException {
        this.workQueue.put(command);
    }

    // 工作线程负责消费任务，并执行任务
    private class WorkerThread extends Thread {
        @Override
        public void run() {
            // 循环去任务并执行
            while (true) {
                try {
                    Runnable task = MyThreadPool.this.workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /***************    使用示例         **********************/
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(2);
        MyThreadPool pool = new MyThreadPool(10, workQueue);

        pool.execute(() -> System.out.println("hello"));
    }
}
