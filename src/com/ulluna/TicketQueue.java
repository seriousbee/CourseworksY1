package com.ulluna;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tomaszczernuszenko on 01/11/16.
 */
public class TicketQueue {

    ArrayList<Integer> array;
    ArrayList<Long> clientQueue;
    int count, result;

    public TicketQueue(String s){
        int i=1, start;
        array = new ArrayList<>();
        while(i < s.length()-1){
            if(s.charAt(i)<='9' && s.charAt(i)>='0'){
                start = i;
                while (s.charAt(i)<='9' && s.charAt(i)>='0'){
                    i++;
                }
                array.add(Integer.parseInt(s.substring(start, i)));
            }
            i++;
        }
        count = array.get(1);
        clientQueue = new ArrayList<>();
        clientQueue.add((long) array.get(0));
        putIntheQueue(count);
    }

    void putIntheQueue(int numberOfPeople){
        long last=0;
        for (int i = 0; i < numberOfPeople-1; i++) {
            last = getNext(clientQueue.get(i));
            clientQueue.add(last);
        }
        Collections.sort(clientQueue);
        result = binarySearch(last);
    }

    long getNext(long current){
        return (current*31334)%31337;
    }

    int binarySearch(long key){
        int l=0, r=clientQueue.size()-1, m=0;
        while(l!=r){
            m=(l+r)/2;
            if(clientQueue.get(m)>key)
                r = (l+r)/2;
            else if(clientQueue.get(m)<key)
                l=(l+r)/2;
            else
                break;
        }
        while(m<clientQueue.size() && clientQueue.get(m+1)==key)
            m++;
        return m;
    }

    public int getOrder(){
        return result+1;
    }



}
