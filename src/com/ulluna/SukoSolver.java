package com.ulluna;

/**
 * Created by tomaszczernuszenko on 08/11/16.
 */
public class SukoSolver {
    int tab[];
    int currentIndex;

    public SukoSolver(){
        tab = new int[9];
        currentIndex=0;
        while(true){
            nextCombination();
            printTab();
        }
    }

    private void nextCombination(){
        if(tab[currentIndex]<=9){
            tab[currentIndex]++;
            return;
        }
        if(currentIndex==8){
            System.out.print("Unsolvable");
            System.exit(0);
        }
        for (int i = 0; i < currentIndex; i++) {
            tab[i]=0;
        }
        tab[currentIndex+1]++;
        currentIndex=0;
    }

    public void printTab(){
        for(int i=0; i<tab.length; i++){
            System.out.print(tab[i]);
        }
        System.out.println();
    }

}
