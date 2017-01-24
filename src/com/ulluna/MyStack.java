package com.ulluna;

import java.util.ArrayList;

/**
 * Created by tomaszczernuszenko on 20/10/16.
 */
public class MyStack {

    ArrayList<Object> arrayList;

    public MyStack(){
        arrayList = new ArrayList<>();
    }

    public int getSize(){
        return arrayList.size();
    }

    public boolean isEmpty(){
        return arrayList.size()==0;
    }

    public void push(Object o){
        arrayList.add(o);
    }

    public Object pop(){
        return arrayList.remove(getSize()-1);
    }

    public Object getValue(){
        return arrayList.get(getSize()-1);
    }
}
