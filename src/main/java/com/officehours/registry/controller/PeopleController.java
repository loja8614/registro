package com.officehours.registry.controller;

import com.officehours.registry.model.People;
import com.officehours.registry.model.Car;
import com.officehours.registry.services.PeopleService;
import com.officehours.registry.services.RegistryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    private PeopleService peopleService;
    private RegistryService registryService;

    public PeopleController(PeopleService peopleService, RegistryService registryService) {
        this.peopleService = peopleService;
        this.registryService = registryService;
    }

    @PostMapping
    public People create(@RequestBody People people) {
        return peopleService.save(people);
    }

    @GetMapping
    public List<People> getAll() {
        return peopleService.getAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        if (registryService.getCarsByPeople(id).size() == 0) {
            peopleService.delete(id);
            return "Person deleted";
        } else {
            return "The person is assigned, it cannot be deleted";
        }

    }

    @GetMapping("/{id}")
    public Optional<People> getById(@PathVariable("id") UUID id) {
        return peopleService.getById(id);
    }

    @GetMapping("/{people_id}/cars")
    public List<Car> getCarsById(@PathVariable("people_id") UUID id) {
        return registryService.getCarsByPeople(id);
    }
}
