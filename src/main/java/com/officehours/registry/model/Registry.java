package com.officehours.registry.model;

import javax.persistence.*;

@Entity
@Table (schema = "registry", name ="rel_registry")
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;


    @ManyToOne
    @JoinColumn(name = "people_id")
    People people;

    @ManyToOne
    @JoinColumn(name = "car_vin")
    Car car;

    public Registry() {
    }

    public Registry(Integer id) {
        this.id = id;
    }

    public Registry(Integer id, People people, Car car) {
        this.id = id;
        this.people = people;
        this.car = car;
    }
    public Registry(People people, Car car) {
        this.people = people;
        this.car = car;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
