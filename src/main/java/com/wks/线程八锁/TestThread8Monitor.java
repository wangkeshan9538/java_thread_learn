package com.wks.线程八锁;

/***
 *
 * 两个普通同步方法  两个线程 标准打印 one teo
 * 添加Thread.sleep 方法  one two
 * 添加普通方法 get3  three one two
 * 两个普通同步方法  有两个Number  two one
 * 修改 getOne 为静态同步方法 打印的是 two one
 * 两个静态方法 ，一个对象 one two
 * 两个对象 一个静态同步方法 一个普通同步方法 two one
 * 两个对象，两个都是静态同步方法 ， onw two
 */
public class TestThread8Monitor {
    public static void main(String[] args) {
        Number n = new Number();
        Number n2 = new Number();

        new Thread(() -> {
            n.get();
        }).start();

        new Thread(() -> {
            //n.get2();
            n2.get2();
        }).start();

        /*new Thread(() -> {
            n.get3();
        }).start();*/
    }

}

class Number {
    public static synchronized void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void get2() {
        System.out.println("two");
    }

    public void get3() {
        System.out.println("three");
    }
}

