package com.wks.testReadwriteLock;


//readwriteLock    乐观锁
//写 需要互斥  读不用互斥
//维护一对锁

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 可以多个读线程 并发持有 ，写锁独占
public class test {
    public static void main(String[] args) {

        ReadWriteLockDemo d = new ReadWriteLockDemo();

        CyclicBarrier c=new CyclicBarrier(101);

        new Thread(() -> {

            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            d.set(9);
        }).start();


        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                d.get();
            }).start();
        }
    }


}

class ReadWriteLockDemo {

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get() {
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + ":" + number);
        lock.readLock().unlock();
    }

    //写
    public void set(int num) {
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName());
        this.number = num;
        lock.writeLock().unlock();


    }
}