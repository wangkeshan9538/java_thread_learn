package com.wks.线程池;

import java.util.concurrent.*;

public class test {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        //分配任务
        pool.submit((Runnable) new PoolTask());

        pool.shutdown();


        //线程调度
        ScheduledExecutorService p2=Executors.newScheduledThreadPool(5);
        p2.schedule((Runnable) new PoolTask(),2, TimeUnit.SECONDS);
    }
}

class PoolTask implements Runnable ,Callable<Integer>{

    private int i = 0;

    @Override
    public void run() {
        while (i < 5) {
            System.out.println(Thread.currentThread().getName()+"A");
            i++;
        }
    }

    @Override
    public Integer call() throws Exception {
        while (i < 5) {
            System.out.println(Thread.currentThread().getName()+"A");
            i++;
        }
        return 1;
    }
}
