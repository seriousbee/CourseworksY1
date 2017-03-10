package com.ulluna;

import java.util.ArrayList;

/**
 * Created by tomaszczernuszenko on 05/02/2017.
 */
public class Cycle {

    ArrayList<Integer> cycle;

    public Cycle() {
    }

    //format: (1, 2, 3, 4)
    public Cycle(String s) {
        cycle = new ArrayList<>();
        if (s.equals("()"))
            return;
        for (int i = 1; i < s.length(); i++) {
            cycle.add(s.charAt(i) - '0');
        }
    }

    public int shift(int x) {
        if (!cycle.contains(x))
            return x;
        if (cycle.indexOf(x) < cycle.size() - 1)
            return cycle.get(cycle.indexOf(x + 1));
        return cycle.get(0);
    }

}
