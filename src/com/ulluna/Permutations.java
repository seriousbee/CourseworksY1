package com.ulluna;

/**
 * Created by tomaszczernuszenko on 11/11/16.
 */
public class Permutations {


    public Permutations(int length, int sum){
        long answer = 0;
        for (int i = 0; i < 9; i++) {
            if((sum-i)>=0) {
                answer += recursion(length-1, sum-i);
            }
        }
        System.out.println(answer);
    }

    private long recursion(int length, int sum){
        long answer =0;
        //System.out.println("How many " +length + " digit numbers that add up to " + sum);
        if(length==0){
            if(sum==0) {
                return 1;
            } else {
                return 0;
            }
        }

        for (int i = 0; i < 9; i++) {
            if(sum-1>=0)
                answer += recursion(length-1, sum-i);
        }
        return answer;
    }


}
