package com.officehours.registry.controller;

import com.officehours.registry.exception.ModelNotFoundException;
import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.services.CarService;
import com.officehours.registry.services.RegistryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private CarService carService;
    private RegistryService registryService;

    public CarController(CarService carService, RegistryService registryService) {
        this.carService = carService;
        this.registryService = registryService;
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        return new ResponseEntity<>(carService.save(car), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        return new ResponseEntity<>(carService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{vin}")
    public ResponseEntity<Object> delete(@PathVariable("vin") String vin) {
        Optional<Car> car = carService.getById(vin);
        if (registryService.getPeopleByCar(vin).size() > 0)
            throw new ModelNotFoundException("The car is assigned, it cannot be deleted");
        if (!car.isPresent() || car.get().getVin() == null)
            throw new ModelNotFoundException("Car not found");
        carService.delete(vin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{vin}")
    public ResponseEntity<Optional<Car>> getById(@PathVariable("vin") String vin) {
        Optional<Car> car = carService.getById(vin);
        if (!car.isPresent() || car.get().getVin() == null)
            throw new ModelNotFoundException("Car not found");
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/{car_vin}/people")
    public ResponseEntity<List<People>> getCarsByPeopleId(@PathVariable("car_vin") String vin) {
        return new ResponseEntity<>(registryService.getPeopleByCar(vin), HttpStatus.OK);
    }

}
