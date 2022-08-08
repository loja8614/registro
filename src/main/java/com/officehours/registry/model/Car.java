package com.officehours.registry.model;
import javax.persistence.*;

@Entity
@Table(schema = "registry", name ="car")
public class Car {
    @Id
    private String vin;

    @Column(name="brand")
    private String brand;

    @Column(name="model")
    private String model;

    @Column(name="year")
    private int year;

    @Column(name="color")
    private String color;

    public Car() {
    }

    public Car(String vin) {
        this.vin = vin;
    }

    public Car(String vin, String brand, String model, int year, String color) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
