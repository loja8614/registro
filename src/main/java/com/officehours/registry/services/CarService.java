package com.officehours.registry.services;

import com.officehours.registry.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Car save(Car car);
    List<Car> getAll();
    void delete(String vin);
    Optional<Car> getById(String vin);
}
