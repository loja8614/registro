package com.officehours.registry.controller;

import com.officehours.registry.exception.ModelNotFoundException;
import com.officehours.registry.model.People;
import com.officehours.registry.model.Car;
import com.officehours.registry.services.PeopleService;
import com.officehours.registry.services.RegistryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<People> create(@RequestBody People people) {
        return new ResponseEntity(peopleService.save(people), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<People>> getAll() {
        return new ResponseEntity(peopleService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
        Optional<People> people = peopleService.getById(id);
        if (registryService.getCarsByPeople(id).size() != 0) {
            throw new ModelNotFoundException("The person is assigned, it cannot be deleted");
        }
        if (!people.isPresent() || people.get().getId() == null)
            throw new ModelNotFoundException("Person not found");
        peopleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<People>> getById(@PathVariable("id") UUID id) {
        Optional<People> people = peopleService.getById(id);
        if (!people.isPresent() || people.get().getId() == null)
            throw new ModelNotFoundException("Person not found");
        return new ResponseEntity(people, HttpStatus.OK);
    }

    @GetMapping("/{people_id}/cars")
    public ResponseEntity<List<Car>> getCarsById(@PathVariable("people_id") UUID id) {
        return  new ResponseEntity(registryService.getCarsByPeople(id),HttpStatus.OK);
    }
}
