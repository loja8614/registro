package com.officehours.registry.controller;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.services.CarService;
import com.officehours.registry.services.RegistryService;
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
    public Car create(@RequestBody Car car) {
        return carService.save(car);
    }

    @GetMapping
    public List<Car> getAll(){
        return carService.getAll();
    }

    @DeleteMapping("/{vin}")
    public String delete(@PathVariable("vin") String vin){
        if(registryService.getPeopleByCar(vin).size()==0) {
            carService.delete(vin);
            return "Car deleted";
        }else{
            return "The car is assigned, it cannot be deleted";
        }
    }

    @GetMapping("/{vin}")
    public Optional<Car> getById(@PathVariable("vin") String vin){
        return carService.getById(vin);
    }
    @GetMapping("/{car_vin}/people")
    public List<People> getCarsByPeopleId(@PathVariable("car_vin") String vin){
        return registryService.getPeopleByCar(vin);
    }

}
