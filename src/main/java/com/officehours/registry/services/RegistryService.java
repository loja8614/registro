package com.officehours.registry.services;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.model.Registry;

import java.util.List;
import java.util.UUID;

public interface RegistryService {
    Registry save(Registry newRegistry);
    List<Car> getCarsByPeople(UUID peopleId);
    List<People> getPeopleByCar(String vin);
}
