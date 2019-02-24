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

    //看起来是个死循环 ，但其实因为i 一直累加 溢出之后 到-1 因为sout 很费时间，所以看起来像是进了死循环，
    public static void main(String[] args) {
        int i=0;
        while(true){
            i++;
            //System.out.println(i);
            if(i==-1){
                break;
            }
        }
     }
}
