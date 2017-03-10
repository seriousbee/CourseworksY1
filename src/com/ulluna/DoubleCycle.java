package com.ulluna;

/**
 * Created by tomaszczernuszenko on 05/02/2017.
 */
public class DoubleCycle {
    Cycle cycle1, cycle2;

    public DoubleCycle(String s) {
        cycle1 = new Cycle(s.substring(0, s.indexOf('(')));
        cycle2 = new Cycle(s.substring(s.indexOf('(')));
    }

    public int shift(int i) {
        if (cycle1.shift(i) == i)
            return cycle2.shift(i);
        return cycle1.shift(i);
    }
}
