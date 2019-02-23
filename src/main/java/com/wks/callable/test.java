package com.wks.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class test {
    public static void main(String[] args) {
        FutureTask<Integer> f=new FutureTask(new Demo());
        new Thread(f).start();
        try {
            System.out.println( f.get() );//阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Demo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        return 1;
    }
}
