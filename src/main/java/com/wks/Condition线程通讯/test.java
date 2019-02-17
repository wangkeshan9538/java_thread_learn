package com.wks.Condition线程通讯;

public class test {

    public static void main(String[] args) {
        Clerk c=new Clerk();



        new Thread(new Productor(c),"生产").start();
        new Thread(new Consumer(c),"消费").start();

    }
}

class Clerk {
    private int product = 0;

    //进货
    public synchronized void push() {
        while (product >= 1) {
            System.out.println("产品已满");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println(Thread.currentThread().getName() + "  " + ++product);
        this.notifyAll();
    }

    //出货
    public synchronized void pop() {
        while (product < 1) {
            System.out.println("无产品");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println(Thread.currentThread().getName() + "  " +  --product);
        this.notifyAll();
    }
}


class Productor implements Runnable{
    Clerk c;

    public Productor(Clerk c) {
        this.c = c;
    }

    public void run() {
        for(int i=0;i<20;i++) {

            c.push();
        }
    }
}

class Consumer implements Runnable{
    Clerk c;

    public Consumer(Clerk c) {
        this.c = c;
    }

    public void run() {
        for(int i=0;i<20;i++){
            c.pop();
        }
    }
}




