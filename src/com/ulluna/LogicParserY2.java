package com.ulluna;

import java.util.Random;

/**
 * Created by tomaszczernuszenko on 10/01/17.
 */
public class LogicParserY2 {

    public static void main(String args[]) {
        String s = "";
        int result;
        for (int i = 0; i < 50; i++) {
            s = "";
            while (s.length() > 50 || s.isEmpty())
                s = generateSentence(1);
            System.out.println(s);

            result = validateSentence(s);
            printResult(result, s);

        }
        //Special wrong cases:
        String s1 = s.substring(1);
        System.out.println(s1);
        result = validateSentence(s1);
        printResult(result, s1);

        String s2 = s.substring(0, s.length() - 2);
        System.out.println(s2);
        result = validateSentence(s2);
        printResult(result, s2);

        result = validateSentence("p");
        System.out.println("p");
        printResult(result, "p");

        result = validateSentence("-p");
        System.out.println("-p");
        printResult(result, "-p");
    }

    private static void printResult(int result, String s) {
        switch (result) {
            case -1:
                System.out.println(s + " is not a formula. Type: " + result);
                break;
            case 7:
                System.out.println(s + " is a preposition. Type: " + result);
                break;
            default:
                System.out.println(s + " is another formula. Type: " + result);
        }
    }

    private static String generateSentence(int level) {
        char[] tab = {'v', '^', '>'};
        char[] tab1 = {'p', 'q', 'r'};
        String output = "";
        while (Math.random() > 0.9)
            output += "-";

        double random = Math.random() * level;
        if (random < 1) {
            output += "(" + generateSentence(level + 1) + tab[randomInRange(0, 2)] + generateSentence(level + 1) + ")";
            return output;
        }
        output += String.valueOf(tab1[randomInRange(0, 2)]);
        return output;
    }

    private static int randomInRange(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    //returns 1 for disjunction, 2 for negated, 3 for implication, 4 for conjunction, 7 for preposition, 8 for negated preposition -1 for invalid
    private static int validateSentence(String s) {
        if (s.length() == 1)
            return isPQR(s.charAt(0)) ? 7 : -1; //valid if inside of a formula, invalid if by itself
        else if (s.charAt(0) == '-') {
            int i = 0;
            while (s.charAt(i) == '-') {
                i++;
            }
            if (i == s.length() - 1)
                return 8;
            if (validateSentence(s.substring(1)) > 0)
                return 2;
            else return -1;
        } else if (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')') {
            int i = findMain(s);
            if (i == -1)
                return -1;
            if (validateSentence(s.substring(1, i)) > 0 && validateSentence(s.substring(i + 1, s.length() - 1)) > 0) {
                if (s.charAt(i) == 'v')
                    return 1;
                if (s.charAt(i) == '>')
                    return 3;
                if (s.charAt(i) == '^')
                    return 4;
            } else
                return -1;
        }
        return -1;
    }

    private static int findMain(String s) {
        int indentationLvl = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                indentationLvl++;
            else if (s.charAt(i) == ')')
                indentationLvl--;
            else if (s.charAt(i) == 'v' || s.charAt(i) == '>' || s.charAt(i) == '^')
                if (indentationLvl == 1)
                    return i;
        }
        return -1;
    }

    private static boolean closed(Tableau t) {
        return true;
    }

    private static void complete(Tableau t) {
        int i = 0;
        while (t.root.charAt(i) == '-') {
            i++;
        }
        t.root = t.root.substring(i % 2);

        int type = validateSentence(t.root);

        if (type == 7 || type == 8) {
            return;
        } else if (type == 2) {
            //do some magic here to get rid of the '-'
        } else if (type == 1) {

        }
    }

    public static void verifySatisfiable(String s) {
        Tableau t = new Tableau();
        t.root = s;
        if (validateSentence(t.root) != -1) {
            complete(t);
            if (closed(t)) {
                System.out.println("Not satisfiable");
            } else {
                System.out.println("Satisfiable!");
            }
        } else {
            System.out.println("Not a formula");
        }
    }

    private static boolean isPQR(char c) {
        return c >= 'p' && c <= 'r';
    }

    static class Tableau {
        String root;
        Tableau left;
        Tableau right;
        Tableau parent;
    }

}