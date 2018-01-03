package com.ulluna;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tomaszczernuszenko on 19/01/17.
 */
public class LabSheeet1{
    //bbb/mmm/ttt/uuu
    //999,999,999,999

    String[] levels = {"", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion"};
    String[] lows = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    String[] tens ={"", "", "twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    public static void main() {
        LabSheeet1 ls = new LabSheeet1();
        System.out.println(ls.verbaliseInt(1829344277));
    }

    public String verbaliseInt (long x) {
        if(x==0)
            return "zero";
        String s="", temp;
        if(x<0){
            s+="minus ";
            x*=-1;
        }
        ArrayList<String> number = new ArrayList<>();
        for (int i = 0; x>0; i++) {
            temp = verbaliseUnits((int)(x%1000));
            if (!temp.isEmpty()) {
                number.add(levels[i]);
                number.add(temp);
            }
            x/=1000;
        }
        Collections.reverse(number);
        for (int i = 0; i < number.size(); i++) {
            s+=number.get(i) + " ";
        }
        return s.replace("  ", " ");
    }

    //precondition x<1000
    public String verbaliseUnits(int x){
        if (x==0)
            return "";
        String s ="", temp="";
        if(lengthOfInt(x)==3){
            s+=lows[x/100] + " hundred ";
            x=x-(x/100)*100;
        }

        if(x>20){
            s+=lows[x/100];
            temp += lows[x%10];
            x/=10;
            s+=tens[x] + " " +temp;
        } else if(x<100){
            s+=lows[x];
        }

        return s;
    }

    public int lengthOfInt(int x){
        int len=0;
        while(x>0){
            len++;
            x/=10;
        }
        return len;
    }

}
