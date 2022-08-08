package com.officehours.registry.services;

import com.officehours.registry.model.People;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeopleService {
    People save(People people);
    List<People> getAll();
    void delete (UUID id);
    Optional<People> getById(UUID id);
}
