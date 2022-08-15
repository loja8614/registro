package com.officehours.registry.controller;

import com.officehours.registry.exception.ModelNotFoundException;
import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.services.PeopleService;
import com.officehours.registry.services.RegistryService;
import com.officehours.registry.util.PeopleDataTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<People> peopleSaved = peopleController.create(people);
        //then:
        assertEquals("Pedro", peopleSaved.getBody().getFirstname());
        assertEquals("Lopez", peopleSaved.getBody().getLastname());
        assertEquals("pedro@email.com", peopleSaved.getBody().getEmail());
        assertEquals("Male", peopleSaved.getBody().getGender());
        assertEquals(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), peopleSaved.getBody().getId());
        assertEquals(200,peopleSaved.getStatusCodeValue());

    }

    @Test
    void givenCars_whenGetAllCars_thenSize3() {
        // given:
        List<People> lstPeopleMocked = PeopleDataTest.getLstPeopleMocked();
        Mockito.when(peopleService.getAll()).thenReturn(lstPeopleMocked);

        // when:
        ResponseEntity<List<People>> allPeople = peopleController.getAll();
        //then:
        assertEquals(3, allPeople.getBody().size());
        assertEquals(200,allPeople.getStatusCodeValue());
    }

    @Test
    void givenId_whenGetPeopleById_thenGetPeople() {

        // given:
        UUID id = UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce");
        People peopleMocked =PeopleDataTest.getPeopleMocked();
        Mockito.when(peopleService.getById(id)).thenReturn(Optional.of(peopleMocked));

        // when:
        ResponseEntity<Optional<People>> people = peopleController.getById(id);
        //then:
        assertEquals("Pedro", people.getBody().get().getFirstname());
        assertEquals("Lopez", people.getBody().get().getLastname());
        assertEquals("pedro@email.com", people.getBody().get().getEmail());
        assertEquals("Male", people.getBody().get().getGender());
        assertEquals(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), people.getBody().get().getId());
        assertEquals(200,people.getStatusCodeValue());
    }
    @Test
    void givenId_whenGetPeopleByIdNotExisting_thenGetExceptionMessage() {

        // given:
        Mockito.when(peopleService.getById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(Optional.of(new People()));

        // when:
        ModelNotFoundException exceptionMessage = Assertions.assertThrows(ModelNotFoundException.class, () -> peopleController.getById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce")));
        //then
        assertEquals("Person not found", exceptionMessage.getMessage());

    }

    @Test
    void givenPeople_whenDelete_thenReturnPersonDeleted() {

        // given:
        List<Car> lstCarMocked = new ArrayList<>();
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);
        People peopleMocked =PeopleDataTest.getPeopleMocked();
        Mockito.when(peopleService.getById(peopleMocked.getId())).thenReturn(Optional.of(peopleMocked));

        // when:
        ResponseEntity<Object> peopleDeleted= peopleController.delete(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));

        //then:
        assertEquals(200, peopleDeleted.getStatusCodeValue());
    }

    @Test
    void givenPeople_whenDeleteWithRegistry_thenNotDeleteSendException() {

        // given:
        Car carMocked = new Car("VINID01", "Mercedes", "SUV", 2020, "white");
        List<Car> lstCarMocked = new ArrayList<>();
        lstCarMocked.add(carMocked);
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);
        Mockito.when(peopleService.getById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(Optional.of(new People()));

        // when:
        ModelNotFoundException exceptionMessage = Assertions.assertThrows(ModelNotFoundException.class, () -> peopleController.delete(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce")));

        //then:
        assertEquals("The person is assigned, it cannot be deleted", exceptionMessage.getMessage());
    }

    @Test
    void givenNotPeople_whenDeleteNotExisting_thenNotDeleteSendException() {

        // given:
        Car carMocked = new Car("VINID01", "Mercedes", "SUV", 2020, "white");
        List<Car> lstCarMocked = new ArrayList<>();
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);
        Mockito.when(peopleService.getById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(Optional.of(new People()));

        // when:
        ModelNotFoundException exceptionMessage = Assertions.assertThrows(ModelNotFoundException.class, () -> peopleController.delete(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce")));

        //then:
        assertEquals("Person not found", exceptionMessage.getMessage());
    }


    @Test
    void givenPeopleId_whenGetCars_thenReturnCars() {

        // given:
        Car carMocked = new Car("VINID01", "Mercedes", "SUV", 2020, "white");
        List<Car> lstCarMocked = new ArrayList<>();
        lstCarMocked.add(carMocked);

        // when:
        Mockito.when(registryService.getCarsByPeople(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(lstCarMocked);
        ResponseEntity<List<Car>> lstCar = peopleController.getCarsById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));

        //then:
        assertEquals(1, lstCar.getBody().size());
        assertEquals("VINID01", lstCar.getBody().get(0).getVin());
        assertEquals("Mercedes", lstCar.getBody().get(0).getBrand());
        assertEquals("SUV", lstCar.getBody().get(0).getModel());
        assertEquals(2020, lstCar.getBody().get(0).getYear());
        assertEquals("white", lstCar.getBody().get(0).getColor());
    }

}