package com.officehours.registry.services;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.model.Registry;
import com.officehours.registry.repository.RegistryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RegistryServiceImpl implements RegistryService {

    private RegistryRepository registryRepository;

    public RegistryServiceImpl(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public Registry save(Registry newRegistry) {
        Registry RegistrySaved = new Registry();

        if (registryRepository.findByPeopleIdCarVin(newRegistry.getCar().getVin(), newRegistry.getPeople().getId()).size() == 0
                && registryRepository.findByCarVin(newRegistry.getCar().getVin()).size() == 0) {
            RegistrySaved = registryRepository.save(newRegistry);
        }
        return RegistrySaved;
    }


    public List<Car> getCarsByPeople(UUID peopleId) {
        List<Registry> lstRegistry = registryRepository.findByPeopleId(peopleId);
        List<Car> lstCarsByPeople = new ArrayList<>();
        for (Registry r : lstRegistry) {
            lstCarsByPeople.add(r.getCar());
        }
        return lstCarsByPeople;
    }

    public List<People> getPeopleByCar(String vin) {
        List<Registry> lstRegistry = registryRepository.findByCarVin(vin);
        List<People> lstPeopleByCar = new ArrayList<>();
        for (Registry r : lstRegistry)
            lstPeopleByCar.add(r.getPeople());
        return lstPeopleByCar;
    }


}
