package com.wks.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class test {

    @org.junit.jupiter.api.Test
    public void tes(){ //610

        Instant begin= Instant.now();

        ForkJoinPool pool=new ForkJoinPool();

        ForkJoinTask<Long> task=new ForkJoinSumCalculate(0L,10000000000L);

        Long res= pool.invoke(task);
        Instant end=Instant.now();
        System.out.println(Duration.between(begin,end).toMillis());
    }

    @org.junit.jupiter.api.Test
    public void test1(){ //12565

        Instant begin= Instant.now();
        Long sum=0L;
        for(Long i=0L ;i<10000000000L;i++){
            sum+=i;
        }

        Instant end=Instant.now();
        System.out.println(Duration.between(begin,end).toMillis());
    }

    @org.junit.jupiter.api.Test
    public void test2(){ //272
        Instant begin= Instant.now();

        Long sum= LongStream.rangeClosed(0L,10000000000L)
                .parallel()
                .reduce(0L,Long::sum);

        Instant end=Instant.now();
        System.out.println(Duration.between(begin,end).toMillis());

    }


}


class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private Long start;
    private Long end;

    private static final long THURSHOLD = 1000L; //临界值

    public ForkJoinSumCalculate(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long length = end - start;
        if (length <= THURSHOLD) {

            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else{
            long middle = (start+end)/2;
            ForkJoinSumCalculate left=new ForkJoinSumCalculate(start,middle);
            left.fork(); // 进行拆分同时压入线程队列

            ForkJoinSumCalculate right=new ForkJoinSumCalculate(middle,end);
            right.fork();

            return left.join()+right.join();
        }
    }
}