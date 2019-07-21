package com.suchaos.concurrency.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join
 *
 * @author suchao
 * @date 2019/7/20
 */
@Slf4j
public class ForkJoinTest1 {

    public static void main(String[] args) {
        // ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Fibonacci task = new Fibonacci(3);
        Integer result = task.invoke();
        log.info("result: " + result);
    }

    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            log.info("task start");
            log.info("thread is demon: " + Thread.currentThread().isDaemon());
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
            // return f1.join() + f2.compute();
        }
    }
}


