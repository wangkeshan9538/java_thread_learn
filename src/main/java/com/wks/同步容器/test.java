package com.wks.同步容器;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class test {

    public static void main(String[] args) throws InterruptedException {
        for (int i=0 ;i<=0;i++){
            new Thread(new Demo()).start();
        }

        Thread.sleep(1000);
        System.out.println(Demo.list.size() );
    }

}


class Demo implements  Runnable{
    //private static List<String> list= Collections.synchronizedList(new ArrayList<String>());

    public static CopyOnWriteArrayList<String> list= new CopyOnWriteArrayList<>();

    static {
        list.add("A");
        list.add("B");
        list.add("C");
    }


    public  void run() {
        Iterator i=list.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
            list.add("D");
        }
    }
}