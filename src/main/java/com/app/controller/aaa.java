package com.app.controller;

import java.util.concurrent.atomic.AtomicLong;
import java.lang.Long;

public class aaa {
    public static void main(String[] args) {

        AtomicLong sum = new AtomicLong(0);
        long min = 0;
        long max = 0;


        for(int i=0;i<10000;i++){
            long startTime=System.currentTimeMillis();

            long a = 1111111111111111111L;
            String b = String.valueOf(a);
            System.out.println(b);
            long c = Long.parseLong(b);
            System.out.println(c);

            long endTime=System.currentTimeMillis();
            long time = endTime-startTime;
            sum.addAndGet(time);
            System.out.println("sum:"+sum);
            if(max<time){
                max = time;
            }
            if(min>time){
                min = time;
            }
        }

        float n = Float.valueOf(String.valueOf(sum));
        System.out.println(n);
        float m = 10000;
        System.out.println("avg: " + (n/m));
        System.out.println("min: "+min);
        System.out.println("max: "+max);
    }
}
