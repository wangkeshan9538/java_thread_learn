package com.wks.Condition线程通讯;


/**
  当 product 运行之后  为什么会 有线程wait了 ，根本原因是 一个线程已经执行结束了，但另一个线程却还有两次执行机会 ，因为只有一个产品，所以一定会被阻塞 且无人再次唤醒
  解决步骤：
  把else去掉
  if (product >= 1) {
 System.out.println("产品已满");

 try {
 this.wait();
 } catch (InterruptedException e) {
 e.printStackTrace();
 }
 System.out.println(Thread.currentThread().getName() + "  " + ++product);
 this.notifyAll();
这样如果有多个生产消费 会有虚假唤醒情况
 然后再改成while循环 来避免虚假唤醒


 想了一下，这里出现wait的根本原因 是wait本身就 不是很对，一旦生产消费的循环数不一致 ，那么必定会wait
 * */



public class test2 {


    public static void main(String[] args) {
        Clerk2 c=new Clerk2();

        new Thread(new Productor2(c),"生产").start();
        new Thread(new Consumer2(c),"消费").start();
    }


}


class Clerk2 {
    private int product = 0;

    //进货
    public synchronized void push() {
        if (product >= 1) {
            System.out.println("产品已满");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }//else{

        System.out.println(Thread.currentThread().getName() + "  " + ++product);
        this.notifyAll();
        //}
    }

    //出货
    public synchronized void pop() {
        if (product < 1) {
            System.out.println("无产品");

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }//else{

        System.out.println(Thread.currentThread().getName() + "  " +  --product);
        this.notifyAll();
        //}
    }
}


class Productor2 implements Runnable{
    Clerk2 c;

    public Productor2(Clerk2 c) {
        this.c = c;
    }

    public void run() {

        for(int i=0;i<20;i++) {

            c.push();
        }
    }
}

class Consumer2 implements Runnable{
    Clerk2 c;

    public Consumer2(Clerk2 c) {
        this.c = c;
    }

    public void run() {

        for(int i=0;i<21;i++){
            c.pop();
        }
    }
}




