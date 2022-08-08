package com.officehours.registry.controller;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.services.CarService;
import com.officehours.registry.services.RegistryService;
import com.officehours.registry.util.CarDataTest;
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
        Car carSaved = carController.create(car);
        //then:
        assertEquals(car.getVin(), carSaved.getVin());
        assertEquals(car.getBrand(), carSaved.getBrand());
        assertEquals(car.getModel(), carSaved.getModel());
        assertEquals(car.getColor(), carSaved.getColor());
        assertEquals(car.getYear(), carSaved.getYear());

    }

    @Test
    void givenCars_whenGetAllCars_thenSize3() {
        // given:
        List<Car> carsMocked = CarDataTest.getAllCarsMocked();
        Mockito.when(carService.getAll()).thenReturn(carsMocked);

        // when:
        List<Car> allCars = carController.getAll();
        //then:
        assertEquals(3, allCars.size());

    }

    @Test
    void givenVin_whenGetCarById_thenGetCar() {

        // given:
        Car carMocked = CarDataTest.getMockCar();
        Mockito.when(carService.getById("VINID")).thenReturn(Optional.of(carMocked));

        // when:
        Optional<Car> car = carController.getById("VINID");
        //then:
        assertEquals("Ford", car.get().getBrand());
    }

    @Test
    void givenCar_whenDelete_thenReturnSize0() {

        // given:

        List<People> lstPeopleMocked = new ArrayList<>();
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);

        // when:
        String status = carController.delete("VINID01");

        //then:
        assertEquals(status, "Car deleted");
    }

    @Test
    void givenCar_whenDeleteWithRegistry_thenNotDelete() {

        // given:
        People peopleMocked = new People(UUID.randomUUID(), "Person1", "LastName1", "person1@gmail.com", "F");
        List<People> lstPeopleMocked = new ArrayList<>();
        lstPeopleMocked.add(peopleMocked);
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);

        // when:
        String status = carController.delete("VINID01");

        //then:
        assertEquals(status, "The car is assigned, it cannot be deleted");
    }

    @Test
    void givenCarVin_whenGetPeople_thenReturnPerson1() {

        // given:

        People peopleMocked = new People(UUID.randomUUID(), "Person1", "LastName1", "person1@gmail.com", "F");
        List<People> lstPeopleMocked = new ArrayList<>();
        lstPeopleMocked.add(peopleMocked);

        // when:
        Mockito.when(registryService.getPeopleByCar("VINID01")).thenReturn(lstPeopleMocked);
        List<People> lstPeople = carController.getCarsByPeopleId("VINID01");

        //then:
        assertEquals(1, lstPeople.size());
        assertEquals("Person1", lstPeople.get(0).getFirstname());
        assertEquals("LastName1", lstPeople.get(0).getLastname());
    }


}