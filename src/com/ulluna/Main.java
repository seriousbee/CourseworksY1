package com.ulluna;


public class Main {


    public static void main(String[] args) {
        double sum =0, current = 1;
        for (int i = 0; i < 100; i++) {
            sum+=current;
            current/=2;
            System.out.println(current);
        }
        System.out.println(sum);
    }


}
