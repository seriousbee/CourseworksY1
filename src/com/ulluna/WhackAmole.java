package com.ulluna;

import java.util.ArrayList;

/**
 * Created by tomaszczernuszenko on 16/11/16.
 */
public class WhackAmole {

    public WhackAmole(String s) {
        ArrayList<Node> toVisit = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        s=translateFromCodex(s);
        Node startingNode = new Node(s);
        toVisit.add(startingNode);
        Node current=null;
        while(true)
        {
            current = toVisit.remove(0);
            if(current.isEmpty())
                break;
            ArrayList<Node> nodes = current.generateImpactList();
            for (int j = 0; j < nodes.size(); j++) {
                if(nodes.get(j).isEmpty()) {
                    emergencyExit(nodes.get(j));
                }
                boolean flag = true;
                for (int k = 0; k < visited.size(); k++) {
                    if (visited.get(k).equals(nodes.get(j))){
                        flag = false;
                        break;
                    }
                }
                if(flag)
                    toVisit.add(nodes.get(j));
            }
            visited.add(current);
            if(visited.size()%10000==0)
                System.out.println(visited.size());
        }
    }

    public void emergencyExit(Node n){
        System.out.println("For Codex: " + translateToCodex(n.getPath()));
        System.exit(0);
    }

    public String translateToCodex(ArrayList<Integer> list){
        int[] tab =  {13,14,15,16,9,10,11,12,5,6,7,8,1,2,3,4};
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result+=tab[list.get(i)];
            result+=" ";
        }
        return result;
    }

    public String translateFromCodex(String s){
        s = s.substring(1,s.length()-1);
        String[] tab = s.split(", +");
        StringBuilder result=new StringBuilder("0000000000000000");
        for (int i = 0; i <tab.length ; i++) {
            result.setCharAt(findIndex(Integer.parseInt(tab[i])), '1');
        }
        return result.toString();
    }

    public int findIndex(int n){
        int[] intTab =  {13,14,15,16,9,10,11,12,5,6,7,8,1,2,3,4};
        for (int i = 0; i < intTab.length; i++) {
            if(intTab[i]==n)
                return i;
        }
        return -1;
    }

}

class Node {

    private final static int[][] impacts = {{1,4},{0,2,5},{1,6,3},{2,7},{0,5,8},{1,4,6,9},{5,10,7,2},{6,3,11},{4,9,12},{8,13,10,5},{9,14,6,11},{10,15,7},{13,8},{12,14,9},{13,10,15},{14,11}};
    private int[] tab;
    private ArrayList<Integer> path;

    public Node(String values){
        tab = new int[16];
        toTab(values);
        path = new ArrayList<>();
    }

    public Node(Node n){
        this.tab = n.getTab().clone();
        path = (ArrayList<Integer>) n.getPath().clone();
    }

    public int[] getTab() {
        return tab;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    private void toTab(String values) {
        for (int i = 0; i < values.length(); i++) {
            tab[i]=values.charAt(i)-'0';
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < tab.length; i++) {
            s+=tab[i];
            if(i<tab.length-1)
                s+=", ";
            if((i+1)%4==0)
                s+="\n";
        }
        s+="\n";
        s+=path;
        return s;
    }

    private void whackAMole(int number){
        for (int i = 0; i < impacts[number].length; i++) {
            if(this.tab[impacts[number][i]]==0)
                this.tab[impacts[number][i]]=1;
            else
                this.tab[impacts[number][i]] = 0;
            this.tab[number]=0;
        }
    }

    public ArrayList<Node> generateImpactList(){
        ArrayList<Node> list = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            if(tab[i]==1){
                Node newNode = new Node(this);
                newNode.whackAMole(i);
                newNode.getPath().add(i);
                list.add(newNode);
            }
        }
        return list;
    }

    public boolean isEmpty(){
        for (int i = 0; i < tab.length; i++) {
            if(tab[i]!=0)
                return false;
        }
        return true;
    }

    public boolean equals (Node other){
        for (int i = 0; i < tab.length; i++) {
            if(this.getTab()[i] != other.getTab()[i])
                return false;
        }
        return true;
    }
}