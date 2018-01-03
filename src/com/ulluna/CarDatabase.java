package com.ulluna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CarDatabase {
    private static List<Cars> listOfCars;

	public static void main(String [] args) {
		listOfCars = new ArrayList<>();
		Cars c1 = new Cars("Fiat", "126p", "Dark Orange", "WZ 12SC2", (float)1231.11);
		listOfCars.add(c1);
		
		char response;
		
		while(true) {
            printMenu();
            response = getUserChoice();
            switch (response) {
                //Quit
                case 'q':
                    System.out.println("\n\tGoodbye");
                    System.exit(0);
                    break;

                //Print out the whole DB
                case 's':
                    printCarDB();
                    break;

                //Add a new element to the DB
                case 'e':
                    addCar(scanCarDetails());
                    sortDB();
                    break;

                //Modify Car
                case 'm':
                    findCarAndEdit();
                    sortDB();
                    break;

                //Find car and print its details
                case 'l':
                    scanDataAndFindCarByRN();
                    break;
            }
        }
			
	}

	public static void addCar(Cars car){
        listOfCars.add(car);
        System.out.println("\tCar Has Been Added to Database");
    }

    public static Cars findCarByRegistrationNumber(String registrationNumber){
        for (int i = 0; i < listOfCars.size(); i++) {
            if(listOfCars.get(i).getRegistrationNumber().equals(registrationNumber))
                return listOfCars.get(i);
        }
        return null;
    }

    public static void printCarDB(){
        System.out.println("\n Total Number of Cars: " + listOfCars.size());
        for (int i = 0; i < listOfCars.size(); i++)
        {
            System.out.println("\n Car Number: " + (i+1) + "\n");
            System.out.println(listOfCars.get(i));
        }
    }

    public static Cars scanCarDetails(){
        Cars newCar = new Cars();

        System.out.println("\n\tEnter Manufacturer:");
        newCar.setManufacturer(scanLine());

        System.out.println("\tEnter Model:");
        newCar.setModel(scanLine());

        System.out.println("\tEnter Colour:");
        newCar.setColour(scanLine());

        System.out.println("\tEnter Registration Number:");
        newCar.setRegistrationNumber(scanLine());

        System.out.println("\tEnter Price:");
        newCar.setPrice(scanFloat());

        return newCar;
    }

    public static String scanLine(){
        Scanner scan = new Scanner(System.in);
        String result = "";
        while(result.replace(" ", "").isEmpty()) {
            result = scan.nextLine();
            result = result.replace("\n", "");
        }
        return result;
    }

    public static float scanFloat(){
        float result;
        while(true){
            try {
                result = new Scanner(System.in).nextFloat();
                break;
            } catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Put in a correct number");
            }
        }
        return result;
    }

    public static Cars scanDataAndFindCarByRN(){
        System.out.println("\tEnter Registration Number");
        Cars foundCar = findCarByRegistrationNumber(scanLine());
        if (foundCar!=null) {
            System.out.println(foundCar);
        } else {
            System.out.println("Car Does Not Exist");
        }
        return foundCar;
    }

    public static void findCarAndEdit(){
        Cars oldCar = scanDataAndFindCarByRN();
        if(oldCar == null)
            return;
        Cars newCar = scanCarDetails();
        copyCarData(newCar, oldCar);
        System.out.println("\tCar Has Been Updated");
    }

    public static void printMenu(){
        System.out.println();
        System.out.println("\tCar Dealership Database");
        System.out.println();
        System.out.println("\t s - Show Database");
        System.out.println("\t e - Enter New Car");
        System.out.println("\t m - Modify Existing Car");
        System.out.println("\t l - Search Registration Number");
        System.out.println("\t q - Quit");
        System.out.println();
        System.out.print("\tChoose One Option ");
    }

    public static char getUserChoice(){
        return  new Scanner(System.in).next().toLowerCase().charAt(0);
    }

    public static void copyCarData(Cars from, Cars to){
        to.setPrice(from.getPrice());
        to.setRegistrationNumber(from.getRegistrationNumber());
        to.setColour(from.getColour());
        to.setModel(from.getModel());
        to.setManufacturer(from.getManufacturer());
    }

    public static void sortDB(){
        Collections.sort(listOfCars, (o1, o2) -> o1.getPrice()>o2.getPrice() ? 1 : -1);
    }
}