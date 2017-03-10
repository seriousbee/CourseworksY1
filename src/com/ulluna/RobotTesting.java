package com.ulluna;

/**
 * Created by tomaszczernuszenko on 09/03/2017.
 */
public class RobotTesting {

    public RobotTesting() {
        int[] tab = {4, 6, 7, 2, 1, 5, 3};
        int cup, i = 0, j, k, flag = 0;
        double previousPos = -0.5, newPos = -0.5;

        while (i < tab.length) {
            j = i;
            k = i + 1;
            flag = 0;
            while (k < tab.length) {
                if (tab[j] > tab[k]) {
                    System.out.print("Positions to swap: " + j + " " + k + ". Array after swap: ");
                    cup = tab[k];
                    tab[k] = tab[j];
                    tab[j] = cup;
                    flag = 1;
                    for (int l = 0; l < tab.length; l++) {
                        System.out.print(tab[l] + " ");
                    }
                    newPos = (k + j) / 2.0;
                    System.out.println("Move robot to: " + newPos + "(" + (newPos - previousPos) + " * x cm)");
                }
                k++;
                j++;
            }
            if (flag == 0)
                i++;
        }
    }
}
