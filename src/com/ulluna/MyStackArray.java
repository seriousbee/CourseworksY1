package com.ulluna;


/**
 * Created by tomaszczernuszenko on 20/10/16.
 */
public class MyStackArray {

    Object[] array;
    int lastElement = -1;

    public MyStackArray(int size) {
        array = new Object[size];
    }

    public int getSize(){
        return lastElement + 1;
    }

    public boolean isEmpty(){
        return getSize()==-1;
    }

    public void push(Object o){
        array[lastElement+1] = o;
        lastElement++;
    }

    public Object pop(){
        return array[lastElement--];

    }

    public Object getValue(){
        return array[lastElement];
    }
}
