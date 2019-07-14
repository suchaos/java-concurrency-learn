package com.suchaos.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier 的简单使用
 *
 * @author suchao
 * @date 2019/7/14
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;

        new HorseRace(nHorses, pause);
    }
}

class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private CyclicBarrier barrier;

    public Horse(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += ThreadLocalRandom.current().nextInt(10);
                }
                barrier.await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                '}';
    }

    public String tracks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            stringBuilder.append("*");
        }
        stringBuilder.append(id);
        return stringBuilder.toString();
    }
}

class HorseRace {
    static final int FINSH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public HorseRace(int nHorses, final int pause) {
        barrier = new CyclicBarrier(nHorses, () -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < FINSH_LINE; i++) {
                builder.append("=");
            }
            System.out.println(builder);
            for (Horse horse : horses) {
                System.out.println(horse.tracks());
            }
            for (Horse horse : horses) {
                if (horse.getStrides() >= FINSH_LINE) {
                    System.out.println(horse + " won!");
                    executorService.shutdownNow();
                    return;
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException e) {
                System.out.println("barrier-action sleep interrupted");
            }
        });
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }
}
