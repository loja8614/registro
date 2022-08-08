package com.officehours.registry.controller;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.services.PeopleService;
import com.officehours.registry.services.RegistryService;
import com.officehours.registry.util.PeopleDataTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class PeopleControllerTest {
    @InjectMocks
    PeopleController peopleController;

    @Mock
    PeopleService peopleService;

    @Mock
    RegistryService registryService;

    @Test
    void givenPeople_whenCreatePeople_thenPeopleCreated() {
        // given:
        People peopleMocked = PeopleDataTest.getPeopleMocked();
        People people = new People(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), "Pedro", "Lopez", "pedro@email.com", "Male");
        Mockito.when(peopleService.save(people)).thenReturn(peopleMocked);

        // when:
        People peopleSaved = peopleController.create(people);
        //then:
        assertEquals("Pedro", peopleSaved.getFirstname());
        assertEquals("Lopez", peopleSaved.getLastname());
        assertEquals("pedro@email.com", peopleSaved.getEmail());
        assertEquals("Male", peopleSaved.getGender());
        assertEquals(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), peopleSaved.getId());

    }

    @Test
    void givenCars_whenGetAllCars_thenSize3() {
        // given:
        List<People> lstPeopleMocked = PeopleDataTest.getLstPeopleMocked();
        Mockito.when(peopleService.getAll()).thenReturn(lstPeopleMocked);

        // when:
        List<People> allPeople = peopleController.getAll();
        //then:
        assertEquals(3, allPeople.size());

    }

    @Test
    void givenId_whenGetPeopleById_thenGetPeople() {

        // given:
        UUID id = UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce");
        People peopleMocked =PeopleDataTest.getPeopleMocked();
        Mockito.when(peopleService.getById(id)).thenReturn(Optional.of(peopleMocked));

        // when:
        Optional<People> people = peopleController.getById(id);
        //then:
        assertEquals("Pedro", people.get().getFirstname());
        assertEquals("Lopez", people.get().getLastname());
        assertEquals("pedro@email.com", people.get().getEmail());
        assertEquals("Male", people.get().getGender());
        assertEquals(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), people.get().getId());
    }

    @Test
    void givenPeople_whenDelete_thenReturnPersonDeleted() {

        // given:
        List<Car> lstCarMocked = new ArrayList<>();
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);

        // when:
        String status = peopleController.delete(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));

        //then:
        assertEquals(status, "Person deleted");
    }

    @Test
    void givenPeople_whenDeleteWithRegistry_thenNotDelete() {

        // given:
        Car carMocked = new Car("VINID01", "Mercedes", "SUV", 2020, "white");
        List<Car> lstCarMocked = new ArrayList<>();
        lstCarMocked.add(carMocked);
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);

        // when:
        String status = peopleController.delete(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));

        //then:
        assertEquals(status, "The person is assigned, it cannot be deleted");
    }


    @Test
    void givenPeopleId_whenGetCars_thenReturnCars() {

        // given:
        Car carMocked = new Car("VINID01", "Mercedes", "SUV", 2020, "white");
        List<Car> lstCarMocked = new ArrayList<>();
        lstCarMocked.add(carMocked);

        // when:
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);
        List<Car> lstCar = peopleController.getCarsById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));

        //then:
        assertEquals(1, lstCar.size());
        assertEquals("VINID01", lstCar.get(0).getVin());
        assertEquals("Mercedes", lstCar.get(0).getBrand());
        assertEquals("SUV", lstCar.get(0).getModel());
        assertEquals(2020, lstCar.get(0).getYear());
        assertEquals("white", lstCar.get(0).getColor());
    }

}