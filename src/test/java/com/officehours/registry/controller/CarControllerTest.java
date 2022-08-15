package com.officehours.registry.controller;

import com.officehours.registry.exception.ModelNotFoundException;
import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.services.CarService;
import com.officehours.registry.services.RegistryService;
import com.officehours.registry.util.CarDataTest;
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
class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private
    CarService carService;

    @Mock
    RegistryService registryService;


    @Test
    void givenCar_whenCreateCar_thenCarCreated() {
        // given:
        Car carMocked = CarDataTest.getMockCar();
        Car car = new Car("VINID", "Ford", "Escape", 2022, "Black");
        Mockito.when(carService.save(car)).thenReturn(carMocked);

        // when:
        ResponseEntity<Car> carSaved = carController.create(car);
        //then:
        assertEquals(car.getVin(), carSaved.getBody().getVin());
        assertEquals(car.getBrand(), carSaved.getBody().getBrand());
        assertEquals(car.getModel(), carSaved.getBody().getModel());
        assertEquals(car.getColor(), carSaved.getBody().getColor());
        assertEquals(car.getYear(), carSaved.getBody().getYear());
        assertEquals(200, carSaved.getStatusCode().value());

    }

    @Test
    void givenCars_whenGetAllCars_thenSize3() {
        // given:
        List<Car> carsMocked = CarDataTest.getAllCarsMocked();
        Mockito.when(carService.getAll()).thenReturn(carsMocked);

        // when:
        ResponseEntity<List<Car>> allCars = carController.getAll();
        //then:
        assertEquals(3, allCars.getBody().size());
        assertEquals(200, allCars.getStatusCode().value());

    }

    @Test
    void givenVin_whenGetCarById_thenGetCar() {

        // given:
        Car carMocked = CarDataTest.getMockCar();
        Mockito.when(carService.getById("VINID")).thenReturn(Optional.of(carMocked));
        // when:
        ResponseEntity<Optional<Car>> car = carController.getById("VINID");
        //then:
        assertEquals("Ford", car.getBody().get().getBrand());
        assertEquals(200, car.getStatusCode().value());
    }

    @Test
    void givenCar_whenDelete_thenReturnCodeResponse200() {

        // given:
        List<People> lstPeopleMocked = new ArrayList<>();
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);
        Mockito.when(carService.getById("VINID01")).thenReturn(Optional.of(CarDataTest.getMockCar()));

        // when:
        ResponseEntity<Object> carDeleted = carController.delete("VINID01");

        //then:
        assertEquals(200, carDeleted.getStatusCodeValue());
    }

    @Test()
    void givenCar_whenDeleteWithRegistry_thenExceptionNotDelete() {

        // given:
        People peopleMocked = new People(UUID.randomUUID(), "Person1", "LastName1", "person1@gmail.com", "F");
        List<People> lstPeopleMocked = new ArrayList<>();
        lstPeopleMocked.add(peopleMocked);
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);
        Mockito.when(carService.getById("VINID01")).thenReturn(Optional.of(CarDataTest.getMockCar()));
        // when:
        ModelNotFoundException exceptionMessage = Assertions.assertThrows(ModelNotFoundException.class, () -> carController.delete("VINID01"));
        //then
        assertEquals("The car is assigned, it cannot be deleted", exceptionMessage.getMessage());

    }

    @Test()
    void givenCar_whenDeleteNotExisting_thenExceptionCarNotFound() {

        // given:
        List<People> lstPeopleMocked = new ArrayList<>();
        Optional<Car> carMocked = Optional.of(new Car());
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);
        Mockito.when(carService.getById("VINID01")).thenReturn(carMocked);
        // when:
        ModelNotFoundException exceptionMessage = Assertions.assertThrows(ModelNotFoundException.class, () -> carController.delete("VINID01"));
        //then
        assertEquals("Car not found", exceptionMessage.getMessage());

    }

    @Test()
    void givenCar_whengetByIdNotExisting_thenExceptionCarNotFound() {

        // given:
        Optional<Car> carMocked = Optional.of(new Car());
        Mockito.when(carService.getById("VINID01")).thenReturn(carMocked);
        // when:
        ModelNotFoundException exceptionMessage = Assertions.assertThrows(ModelNotFoundException.class, () -> carController.getById("VINID01"));
        //then
        assertEquals("Car not found", exceptionMessage.getMessage());

    }

    @Test
    void givenCarVin_whenGetPeople_thenReturnPerson1() {

        // given:
        People peopleMocked = new People(UUID.randomUUID(), "Person1", "LastName1", "person1@gmail.com", "F");
        List<People> lstPeopleMocked = new ArrayList<>();
        lstPeopleMocked.add(peopleMocked);

        // when:
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);
        ResponseEntity<List<People>> lstPeople = carController.getCarsByPeopleId("VINID01");

        //then:
        assertEquals(1, lstPeople.getBody().size());
        assertEquals("Person1", lstPeople.getBody().get(0).getFirstname());
        assertEquals("LastName1", lstPeople.getBody().get(0).getLastname());
    }


}