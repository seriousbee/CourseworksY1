package com.ulluna;

import java.util.Objects;

public class Cars implements Comparable
{
	
	private String manufacturer;
	private String model;
	private String colour;
	private String registrationNumber;
	private float price;

    public Cars(String manufacturer, String model, String colour, String registrationNumber, float price) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.colour = colour;
        this.registrationNumber = registrationNumber;
        this.price = price;
    }

    public Cars() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car \n" +
                "\tManufacturer: "  + manufacturer + '\n' +
                "\tModel: " + model + '\n' +
                "\tColour: " + colour + '\n' +
                "\tRegistrationNumber: " + registrationNumber + '\n' +
                "\tPrice ($): " + price;
    }


    @Override
    public int compareTo(Object o) {
        return ((Cars)o).getPrice()>this.getPrice() ? 1: 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return Objects.equals(registrationNumber, cars.registrationNumber);
    }

}