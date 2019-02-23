package com.wks.原子变量;

import java.util.concurrent.atomic.AtomicInteger;

public class test {
    static  int i=0;

    static AtomicInteger l=new AtomicInteger(0);

    public static void main(String[] args) {


        for (int j=0 ;j<10;j++){
            new Thread(()->{

                //为什么要sleep 需要等线程一起
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                System.out.println("i="+i);
                System.out.println("l="+l.getAndIncrement());
            }).start();
        }

    }

}
