package com.ulluna;

/**
 * Created by tomaszczernuszenko on 21/10/16.
 */
public class Printing {

    public void printHash(int n){
        System.out.print("#");
        for(int i=1; i<n; i++){
            System.out.print("#");
            for (int j = 0; j < i-i; j++) {
                System.out.print(" ");
            }
            System.out.print("#");
        }
        for (int i = 0; i < n; i++) {
            System.out.print("#");
        }
    }
    public void printStar(int n){
        for (int i = 0; i < n; i++) {
            System.out.print("*");
        }
        for (int i = n -3; i >0 ; i--) {
            System.out.print("*");
            for (int j = 0; j < n; j++) {
                System.out.print(" ");
            }
            System.out.println("*");
        }
    }

    public Printing(){
        printHash(7);
        printStar(7);
    }


    public void print(){ //nE(0,7)
        for (int i = 0; i < 7; i++) {
            System.out.print("*");
        }
        System.out.println();
        int hash = 1, hashspace = 0, star = 2, starspace = 4;
        while (true){
            if(hash == 1)
                System.out.print("#");
            if(hash == 2)
                System.out.println("##");
            if(star == 2) {
                System.out.println("**");
                continue;
            }
            System.out.print("*");
            if(star == 1){
                System.out.println("*");
                break;
            }
        }
    }

}
