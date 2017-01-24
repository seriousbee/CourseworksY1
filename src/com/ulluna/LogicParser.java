package com.ulluna;

import java.util.Random;

/**
 * Created by tomaszczernuszenko on 10/01/17.
 */
public class LogicParser {

    static String[] texts = {"Invalid", "", "Atomic", "Negated", "Binary Connected", "Existential", "Universal"};
    static int numberOfNodes;
    static int[][] connections;
    static int[] XYZ;

    public static void generateTestAndSolve(){
        String input = LogicParser.generateTest();
        System.out.println(input);

        String s = input.substring(0, input.indexOf(' '));
        String variableAssignments = input.substring(input.indexOf(' ') + 1);
        analyseConnections(variableAssignments);
        System.out.println(s);
        System.out.print(texts[validateSentence(s) + 1] + " ");
        System.out.println(evaluate(s, XYZ));
    }

    public static String generateTest(){
        String s= "";
        int nodes = randomInRange(1, 6);
        int edges = randomInRange(1, nodes*nodes);
        s += nodes + " " + edges + " ";
        nodes--;
        for (int i = 0; i < edges; i++) {
            s += randomInRange(0, nodes) + " " + randomInRange(0, nodes) + " ";
        }
        s += randomInRange(0, nodes) + " ";
        s += randomInRange(0, nodes) + " ";
        s += randomInRange(0, nodes) + " ";
        return generateSentence(1) + " " + s;
    }

    public static String generateSentence(int level){
        char[] tab = {'v', '^', '>'};
        char[] tab1 = {'x', 'y', 'z'};

        int random = (int) Math.round(Math.random()*level);
        if(random==0){
            return "(" + generateSentence(level+1) + tab[randomInRange(0,2)] + generateSentence(level+1) + ")";
        }
        double randDouble = Math.random();
        if(randDouble>=0.5){
            return "X[" + tab1[randomInRange(0,2)] + tab1[randomInRange(0,2)] +"]";
        } else if (randDouble>=0.25){
            return "E" + tab1[randomInRange(0,2)] + generateSentence(level+1);
        } else {
            return "A" + tab1[randomInRange(0,2)] + generateSentence(level+1);
        }
    }

    public static int randomInRange(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }


    public static boolean validAtomic(String s){
        if(s.length()!=5 || s.charAt(0)!='X' || s.charAt(1)!='[' || s.charAt(4)!=']')
            return false;
        if(!isXYZ(s.charAt(2)) || !isXYZ(s.charAt(3)))
            return false;
        return true;
    }

    //returns 1 for atomic, 2 for negated, 3 for binary connected, 4 for existential, 5 for universal, -1 for invalid
    public static int validateSentence(String s){
        if (s.length()<5)
            return -1;
        else if(s.charAt(0)=='E'){ //is it existential? 4
            if(isXYZ(s.charAt(1))){
                if(validateSentence(s.substring(2))>0)
                    return 4;
                else return -1;
            } else{
                return -1;
            }
        }

        else if(s.charAt(0)=='A'){ //is it universal? 5
            if(isXYZ(s.charAt(1))){
                if(validateSentence(s.substring(2))>0)
                    return 5;
                else return -1;
            } else {
                return -1;
            }
        }

        else if(s.charAt(0)=='('){ //is it binary connected? 3
            if(s.charAt(s.length()-1)==')'){
                int indentationLvl = 0;
                for (int i = 0; i < s.length(); i++) {
                    if(s.charAt(i)=='(')
                        indentationLvl++;
                    else if (s.charAt(i)==')')
                        indentationLvl--;
                    else if(s.charAt(i)=='v' || s.charAt(i)=='>' || s.charAt(i)=='^')
                        if(indentationLvl==1)
                            if(validateSentence(s.substring(1, i))>0 && validateSentence(s.substring(i+1, s.length()-1))>0)
                                return 3;
                            else
                                return -1;
                }
                return -1;
            } else {
                return -1;
            }
        }

        //TODO: is ---O a correct formula???
        else if(s.charAt(0)=='-'){
            if(validateSentence(s.substring(1))>0)
                return 2;
            else return -1;
        }
        else if(s.length()==5 && validAtomic(s)) //is it an atomic formula??? 1
            return 1;
        return -1;
    }

    public static boolean isXYZ(char c) {
        return c >= 'x' && c <= 'z';
    }

    public static boolean implication(boolean p, boolean q){
        return !p || q;
    }

    public static boolean evaluateAtomic(String s, int[] localXYZ){
        for (int i = 0; i < connections.length; i++) {
            if(connections[i][0]==getXYZ(s.charAt(2),localXYZ) && connections[i][1] == getXYZ(s.charAt(3),localXYZ))
                return true;
        }
        return false;
    }

    public static void analyseConnections(String s){
        String[] ss = s.split(" ");
        int[] analysed = new int[ss.length];
        for (int i = 0; i < analysed.length; i++) {
            analysed[i] = Integer.parseInt(ss[i]);
        }
        XYZ = new int[3];
        XYZ[0] = analysed[analysed.length-3];
        XYZ[1] = analysed[analysed.length-2];
        XYZ[2] = analysed[analysed.length-1];

        numberOfNodes = analysed[0];

        connections= new int[analysed[1]][2];
        for (int i = 0; i < connections.length; i++) {
            connections[i][0]=analysed[i*2+2];
            connections[i][1]=analysed[i*2+3];
        }
    }

    public static boolean evaluate (String s, int[] localXYZ) {
        if(validAtomic(s)){
            return evaluateAtomic(s, localXYZ);
        }
        else if(s.charAt(0)=='A'){
            for (int i = 0; i < numberOfNodes; i++) {
                if (!evaluate(s.substring(2), setXYZ(s.charAt(1), localXYZ, i)))
                    return false;
            }
            return true;
        }
        else if(s.charAt(0)=='E'){
            for (int i = 0; i < numberOfNodes; i++) {
                if (evaluate(s.substring(2), setXYZ(s.charAt(1), localXYZ, i)))
                    return true;
            }
            return false;
        }
        else if(s.charAt(0)=='('){ //TODO: can binary connected formulas look like this: (OvOvO) or only ((OvO)vO)
            int indentationLevel=0;
            for (int i = 0; i < s.length(); i++) {
                if(s.charAt(i)=='(')
                    indentationLevel++;
                else if (s.charAt(i)==')')
                    indentationLevel--;
                else if (indentationLevel == 1)
                    if (s.charAt(i)=='v'){
                        return evaluate(s.substring(1,i), localXYZ) || evaluate(s.substring(i+1, s.length()-1), localXYZ);
                    }
                    else if (s.charAt(i)=='^'){
                        return evaluate(s.substring(1,i), localXYZ) && evaluate(s.substring(i+1, s.length()-1), localXYZ);
                    }
                    else if (s.charAt(i)=='>'){
                        return implication(evaluate(s.substring(1,i), localXYZ), evaluate(s.substring(i+1, s.length()-1), localXYZ));
                    }
            }
        }
        else if(s.charAt(0)=='-'){
            return !evaluate(s.substring(1), localXYZ);
        }

        System.out.println("Error: Invalid formula: " + s);
        return false;
    }

    public static int getXYZ(char c, int[] XYZtab){
        return XYZtab[(c-'x')];
    }

    public static int[] setXYZ(char c, int[] XYZtab, int value){
        int[] localXYZ = XYZtab.clone();
        localXYZ[XYZtoInt(c)] = value;
        return localXYZ;
    }

    public static int XYZtoInt(char c){
        return c-'x';
    }
}
