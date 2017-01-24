package com.ulluna;

import java.util.ArrayList;

/**
 * Created by tomaszczernuszenko on 14/10/16.
 */
public class GreastDivisor {

    ArrayList<Integer> arrayList;

    public GreastDivisor(String s){  //accepts format: [112, 35]
        arrayList = new ArrayList<>();



        String[] sArray = s.substring(1, s.length()-1).split(", ");

        for (int i = 0; i < sArray.length; i++) {
            arrayList.add(Integer.parseInt(sArray[i]));
        }

        int tab[] = new int[arrayList.size()];

        for (int i = 0; i < tab.length; i++) {
            tab[i] = arrayList.get(i);
        }

        int g = greastDivisor(arrayList), sum = 0;

        for (int i = 0; i < tab.length; i++) {
            sum+=tab[i]/g;
        }

        System.out.println(g + " " + sum);


    }

    public int greastDivisor(ArrayList<Integer> array){

        if(array.size()<2){
            return array.get(0);
        }

        int m, n, e;
        m = array.get(0);
        n = array.get(1);
        while (m > 0){
            e = n;
            n = m;
            m = e % m;
        }
        array.set(0, n);
        array.remove(1);

        return greastDivisor(array);
    }

    void main(){
        String s = "[112, 35] [1836, 675] [1274, 2002, 598] [202608, 364882, 174468] [228864, 54272, 55808, 253696, 156416]";

        int b=s.indexOf("]", 1);
        while (b>-1){
            if(b+2>s.length())
                b=s.length()-1;
            GreastDivisor divisor = new GreastDivisor(s.substring(0, b+1));
            if(b==s.length()-1)
                break;
            s = s.substring(b+2);
            b=s.indexOf("]", 1);
        }
    }

}
