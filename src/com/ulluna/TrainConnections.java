package com.ulluna;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by tomaszczernuszenko on 14/10/16.
 */
public class TrainConnections {

    public void main(String[] args) {

        Scanner scan = new Scanner(System.in);  // Reading from System.in

        int numberOfCities = scan.nextInt();
        City[] cityArray = new City[numberOfCities];
        scan.nextLine();

        for (int i = 0; i < numberOfCities; i++) {
            String current = scan.nextLine();
            System.out.println(current);
            cityArray[i] = new City(current);
        }

        //int numberOfConnections = scan.nextInt();

        int numberOfStops = scan.nextInt();
        scan.nextLine();




    }

    class ConnectionChainElement{
        TrainTime time;
        String name;



    }


    class City{
        String name;
        ArrayList<Connection> connections;

        public City (String name){
            this.name = name;
            connections = new ArrayList<>();
        }


        //Returns true if succeeded with adding the connection
        public boolean validateAndAdd (Connection connection){
            if(this.name.equals(connection.from)) {
                connections.add(connection);
                return true;
            }
            return false;
        }
    }

    class Connection{
        String from;
        String to;
        TrainTime departureTime;
        TrainTime arrivalTime;

        public Connection(String from, String to, String departureTime, String arrivalTime){
            this.from = from;
            this.to = to;
            this.departureTime = new TrainTime(departureTime);
            this.arrivalTime = new TrainTime(arrivalTime);
        }

        public boolean canChange(Connection other){
            if(this.to.equals(other.from)){
                if(this.arrivalTime.compareTo(other.departureTime)>-1)
                    return true;
            }
            return false;
        }

        public String toString(){
            return from + " (" + departureTime.text + ") -> " + to + " (" + arrivalTime.text + ")";
        }

    }

    class TrainTime{
        String text;
        int value;

        public TrainTime(String text){
            this.text = text;
            value = 0;
            for (int i = 0; i < text.length(); i++) {
                int j=0;
                switch (i) {
                    case 0:
                        j = 600;
                        break;
                    case 1:
                        j = 60;
                        break;
                    case 2:
                        j = 10;
                        break;
                    case 3:
                        j = 1;
                        break;
                }
                value+= j*Integer.parseInt(String.valueOf(text.charAt(i)));
            }
        }

        public String toString(){
            return "TrainTime:" + text + " " + value;
        }

        //Returns 1 if the other time is bigger (later)
        //Returns 0 if both times are equal
        //Returns -1 if the other time is smaller(earlier)
        public int compareTo(TrainTime other){
            if(this.value < other.value)
                return 1;
            if(this.value == other.value)
                return 0;
            return -1;
        }
    }
}
