package com.ulluna;

/**
 * Created by tomaszczernuszenko on 20/10/16.
 */
public class InsertionSort {

    public int[] array = {5,2,4,6,1,3};

    public void sort(){
        int key, i;
        for (int j = 1; j < array.length; j++) {
            key = array[j];
            i = j-1;
            while (i>=0 && array[i]<key){
                array[i+1]=array[i];
                i--;
            }
            array[i+1] = key;
        }
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < array.length; i++) {
            s+=array[i] + "\n";
        }
        return s;
    }
}
