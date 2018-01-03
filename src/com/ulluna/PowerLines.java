package com.ulluna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tomaszczernuszenko on 24/10/16.
 */
public class PowerLines {

    ArrayList<Integer> array;
    ArrayList<City> cities;
    ArrayList<Connection> connections;
    int graphWeight;

    public PowerLines(String s) {  //input in format: "[1, 2, 3, 4, 5, 6]"
        int i=1, start;
        array = new ArrayList<>();
        while(i < s.length()-1){
            if(s.charAt(i)<='9' && s.charAt(i)>='0'){
                start = i;
                while (s.charAt(i)<='9' && s.charAt(i)>='0'){
                    i++;
                }
                array.add(Integer.parseInt(s.substring(start, i)));
            }
            i++;
        }
        generateCityList();
        generateConnectionList();
        runKruskall();
    }

    private void generateCityList(){
        cities = new ArrayList<>();
        for (int i = 1; i < array.size(); i++) {
            if (i%2!=0){
                cities.add(new City(array.get(i-1), array.get(i)));
            }
        }
    }

    private void generateConnectionList(){
        connections = new ArrayList<>();
        for (int i = 0; i < cities.size()-1; i++) {
            for (int j = i+1; j < cities.size(); j++) {
                connections.add(new Connection(cities.get(i), cities.get(j)));
            }
        }
        Collections.sort(connections, new Comparator<Connection>() {
            @Override
            public int compare(Connection o1, Connection o2) {
                return o1.compareTo(o2);
            }
        });
    }

    private void runKruskall(){
        KruskallArray kArray = new KruskallArray();
        for (int i = 0; !kArray.isFull(cities.size()); i++) {
            kArray.addConnection(connections.get(i));
        }
        graphWeight = kArray.getWeight();
    }

    public int getGraphWeight() {
        return graphWeight;
    }
}

class City{
    int x, y;
    public City(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        return ((City) obj).getX() == this.getX() && ((City) obj).getY() == this.getY();
    }

    @Override
    public String toString() {
        return "City{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Connection implements Comparable{
    City city1, city2;
    int distance;

    public Connection(int[] array){
        city1 = new City(array[0], array[1]);
        city2 = new City(array[2], array[3]);
        distance = calculateDistance();
    }

    public Connection(City city1, City city2){
        this.city1 = city1;
        this.city2 = city2;
        distance = calculateDistance();
    }

    private int calculateDistance(){
        int dx = Math.abs(city1.getX() - city2.getX());
        int dy = Math.abs(city1.getY() - city2.getY());
        return dx+dy;
    }

    public int getDistance() {
        return distance;
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.distance, ((Connection) o).getDistance());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCity1().equals(((Connection)obj).getCity1()) && this.getCity2().equals(((Connection)obj).getCity2());
    }

    @Override
    public String toString() {
        return "Connection{" +
                "city1=" + city1 +
                ", city2=" + city2 +
                ", distance=" + distance +
                '}';
    }
}

class KruskallSubarray{
    private ArrayList<City> connectedCities;

    public KruskallSubarray(Connection c) {
        connectedCities = new ArrayList<>();
        connectedCities.add(c.getCity1());
        connectedCities.add(c.getCity2());
    }

    public int makesCycle(Connection c){ //return 0 - none of the cities are in the subarray, return 1 - the first one is in the subarray, return 2 - the second one is in the subarray, return 3 - both are in the subarray - a cycle is created
        boolean is1 = false, is2 = false;
        for (int i = 0; i < connectedCities.size(); i++) {
            if(connectedCities.get(i).equals(c.getCity1()))
                is1 = true;
            else if(connectedCities.get(i).equals(c.getCity2()))
                is2 = true;
            if (is1 && is2)
                break;
        }
        if(is1 && is2)
            return 3;
        if (is1)
            return 1;
        if (is2)
            return 2;
        return 0;
    }

    public ArrayList<City> getConnectedCities() {
        return connectedCities;
    }

    public void concatinateSubarrays(KruskallSubarray other){
        connectedCities.addAll(other.getConnectedCities());
    }

    public void addConnection(Connection c){
        int r = makesCycle(c);
        if(r == 1)
            connectedCities.add(c.getCity2());
        else if (r == 2)
            connectedCities.add(c.getCity1());
        else if (r == 0) {
            connectedCities.add(c.getCity1());
            connectedCities.add(c.getCity2());
        }
    }
    public int getLength() {
        return connectedCities.size();
    }
}

class KruskallArray{
    int weight;
    private ArrayList<KruskallSubarray> subarrays;

    public KruskallArray(){
        subarrays = new ArrayList<>();
        weight = 0;
    }

    public void addConnection(Connection c){
        boolean toAdd = true;
        int one = -1, two = -1,r;
        for (int i = 0; i < subarrays.size(); i++) {
            r = subarrays.get(i).makesCycle(c);
            if(r == 3){
                toAdd = false;
                break;
            }
            else if (r == 1)
                one = i;
            else if (r == 2)
               two = i;
        }
        if(toAdd){
            weight += c.getDistance();
            if(one == -1 && two == -1)
                subarrays.add(new KruskallSubarray(c));
            else if(one != -1 && two != -1) {
                subarrays.get(one).concatinateSubarrays(subarrays.get(two));
                subarrays.remove(two);
            }
            else if(one != -1)
                subarrays.get(one).addConnection(c);
            else if(two != -1)
                subarrays.get(two).addConnection(c);
        }
    }

    public boolean isFull(int desiredLength){
        return !subarrays.isEmpty() && subarrays.get(0).getLength() == desiredLength;
    }

    public int getWeight() {
        return weight;
    }
}
