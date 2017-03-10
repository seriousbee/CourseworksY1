package com.ulluna;

/**
 * Created by tomaszczernuszenko on 28/02/2017.
 */
public class Huffman {
    public static void main(String args[]) {
        String s = "How is common sense a sense?";
        int tab[] = new int[256];
        for (int i = 0; i < s.length(); i++) {
            tab[s.charAt(i)]++;
        }
        for (int i = 0; i < 256; i++) {
            if (tab[i] != 0)
                System.out.println("'" + (char) i + "'" + ": " + tab[i]);

        }
    }
}
