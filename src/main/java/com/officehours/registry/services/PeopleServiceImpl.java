package com.officehours.registry.services;

import com.officehours.registry.model.People;
import com.officehours.registry.repository.PeopleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PeopleServiceImpl implements PeopleService {
    private PeopleRepository personRepository;

    public PeopleServiceImpl(PeopleRepository personRepository) {
        this.personRepository = personRepository;
    }

    public People save(People people){
        return personRepository.save(people);
    }

    public List<People> getAll(){
        return personRepository.findAll();
    }
    public void delete (UUID id){
        personRepository.deleteById(id);
    }

    public Optional<People> getById(UUID id) {
        return personRepository.findById(id);
    }

}
