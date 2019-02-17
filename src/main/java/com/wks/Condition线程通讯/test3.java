package com.wks.Condition线程通讯;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用同步锁来做生产消费
 */
public class test3 {

    public static void main(String[] args) {
        Clerk3 c=new Clerk3();

        new Thread(new Productor3(c),"生产").start();
        new Thread(new Consumer3(c),"消费").start();
    }

}

class Clerk3 {
    private int product = 0;

    private Lock lock = new ReentrantLock();

    private Condition c = lock.newCondition();

    //进货
    public void push() {

        lock.lock();

        try {
            while (product >= 1) {
                System.out.println("产品已满");
                c.await();
            }

            System.out.println(Thread.currentThread().getName() + "  " + ++product);
            c.signalAll();
        } catch (Exception e) {
            lock.unlock();
        }finally {
            lock.unlock();

        }
    }

    //出货
    public   void pop() {

        lock.lock();

        try {
            while (product < 1) {
                System.out.println("无产品");

                c.await();

            }

            System.out.println(Thread.currentThread().getName() + "  " + --product);
            c.signalAll();
        } catch (Exception e) {
            lock.unlock();
        }finally {
            lock.unlock();

        }
    }
}


class Productor3 implements Runnable {
    Clerk3 c;

    public Productor3(Clerk3 c) {
        this.c = c;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {

            c.push();
        }
    }
}

class Consumer3 implements Runnable {
    Clerk3 c;

    public Consumer3(Clerk3 c) {
        this.c = c;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            c.pop();
        }
    }
}