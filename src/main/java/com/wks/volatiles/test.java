package com.wks.volatiles;

public class test {
    public static void main(String[] args) {
        Demo d= new Demo();
        new Thread(d).start();

        while(true){
            if(d.isFlag()){
                System.out.println("find flag chenge to true");
                break;
            }
        }
    }
}

class Demo implements Runnable {

    private volatile  boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("flag changed ");
        flag = true;

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}