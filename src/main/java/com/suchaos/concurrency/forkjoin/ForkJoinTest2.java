package com.suchaos.concurrency.forkjoin;

import lombok.extern.slf4j.Slf4j;
import sun.awt.geom.AreaOp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join
 *
 * @author suchao
 * @date 2019/7/20
 */
@Slf4j
public class ForkJoinTest2 {

    public static void main(String[] args) {
        // ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        String[] fc = {"hello world", "hello me", "hello fork", "hello join"};

        MR mr = new MR(fc, 0, fc.length);
        Map<String, Long> result = forkJoinPool.invoke(mr);
        System.out.println(result);
    }

    static class MR extends RecursiveTask<Map<String, Long>> {

        private String[] fc;
        private int start, end;

        public MR(String[] fc, int start, int end) {
            this.fc = fc;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Map<String, Long> compute() {
            if (end - start == 1) {
                return calc(fc[start]);
            } else {
                int mid = (start + end) / 2;
                MR mr1 = new MR(fc, start, mid);
                mr1.fork();
                MR mr2 = new MR(fc, mid, end);
                return merge(mr2.compute(), mr1.join());
            }
        }

        // 合并结果
        private Map<String, Long> merge(Map<String, Long> r1, Map<String, Long> r2) {
            Map<String, Long> result = new HashMap<>(r1);
            r2.forEach((key, value) -> {
//                Long c = result.get(key);
//                if (c != null) {
//                    result.put(key, c + value);
//                } else {
//                    result.put(key, value);
//                }
                result.merge(key, value, Long::sum);
            });
            return result;
        }

        // 统计单词数量
        private Map<String, Long> calc(String line) {
            Map<String, Long> result = new HashMap<>();
            // 分割单词
            String[] words = line.split("\\s+");
            // 统计单词数量
            for (String w : words) {
                Long v = result.get(w);
                if (v != null) {
                    result.put(w, v + 1);
                } else {
                    result.put(w, 1L);
                }
            }
            return result;
        }
    }
}


