package com.wks.交替打印;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class test {

    public static void main(String[] args) {
        Lock printLock = new ReentrantLock();

        Condition afinish = printLock.newCondition();
        Condition bfinish = printLock.newCondition();
        Condition cfinish = printLock.newCondition();


        //关键在于让三条线程 都拿到锁之后 卡在wait方法 ，不然 就会出现 afinish 之后 ，b还没拿到锁，并不能被唤醒
        new Thread(() -> {
            while (!printLock.tryLock()) {

            }
            for (int i = 0; i < 10; i++) {
                try {
                    cfinish.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A");
                afinish.signalAll();
            }
            printLock.unlock();


        }).start();


        new Thread(() -> {
            while (!printLock.tryLock()) {

            }

            for (int i = 0; i < 10; i++) {
                try {
                    afinish.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B");
                bfinish.signalAll();
            }

            printLock.unlock();

        }).start();


        new Thread(() -> {


            while (!printLock.tryLock()) {

            }
            for (int i = 0; i < 10; i++) {
                try {
                    bfinish.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C");
                cfinish.signalAll();
            }
            printLock.unlock();

        }).start();


        printLock.lock();
        cfinish.signalAll();

        printLock.unlock();

    }

}



