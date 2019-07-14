package com.suchaos.concurrency;

/**
 * SynchronizedTest1
 *
 * @author suchao
 * @date 2019/7/11
 */
public class SynchronizedTest1 {

    public static void main(String[] args) throws InterruptedException {
        HelloWorld helloWorld = new HelloWorld();
        Thread hello = new HelloThread(helloWorld, "helloThread");
        Thread world = new WorldThread(helloWorld, "worldThread");

        hello.start();

        Thread.sleep(7000);

        world.start();
    }
}

class HelloWorld {
    public synchronized void hello() {
        try {
            Thread.sleep(14000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
    }

    public synchronized void world() {
        System.out.println("world");
    }
}

class HelloThread extends Thread {
    private HelloWorld hello;

    public HelloThread(HelloWorld hello) {
        this.hello = hello;
    }

    public HelloThread(HelloWorld hello, String name) {
        super(name);
        this.hello = hello;
    }

    @Override
    public void run() {
        hello.hello();
    }
}

class WorldThread extends Thread {
    private HelloWorld world;

    public WorldThread(HelloWorld world) {
        this.world = world;
    }

    public WorldThread(HelloWorld world, String name) {
        super(name);
        this.world = world;
    }

    @Override
    public void run() {
        world.world();
    }
}
