package com.officehours.registry.services;

import com.officehours.registry.model.Car;
import com.officehours.registry.repository.CarRepository;
import com.officehours.registry.util.CarDataTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Test
    void givenCar_whenSave_thenReturnSavedCar() {
        // given:
        Car carMocked = CarDataTest.getMockCar();
        Car car = new Car("VINID", "Ford", "Escape", 2022, "black");
        Mockito.when(carRepository.save(car)).thenReturn(carMocked);
        // when:
        Car carSaved = carService.save(car);
        //then:
        assertEquals(car.getVin(), carSaved.getVin());
    }

    @Test
    void givenCarSaved_whenSave_thenReturnSavedCarUpdated() {

        // given:
        Car carMocked = CarDataTest.getMockCarUpdated();
        Car car = new Car("VINID", "FORD", "RANGER", 2021, "Green");
        Mockito.when(carRepository.save(car)).thenReturn(carMocked);

        // when:
        Car carSaved = carService.save(car);
        //then:
        assertEquals(car.getVin(), carSaved.getVin());
        assertEquals(car.getBrand(), carSaved.getBrand());
    }

    @Test
    void givenZeroCarSaved_whenGetAll_thenReturnSize0() {
        // given:
        // when:
        List<Car> lstCars = carService.getAll();
        //then:
        assertEquals(0, lstCars.size());
    }

    @Test
    void givenCars_whenGetAll_thenReturnSize3() {

        List<Car> carsMocked = CarDataTest.getAllCarsMocked();
        // given:
        Mockito.when(carRepository.findAll()).thenReturn(carsMocked);

        // when:
        List<Car> ltsCars = carService.getAll();
        //then:
        assertEquals(3, ltsCars.size());
    }

    @Test
    void givenCar_whenGetById_thenReturnSameCar() {

        // given:
        Car carMocked = new Car("VINID01", "Mercedes", "SUV", 2020, "white");
        Mockito.when(carRepository.findById("VINID01")).thenReturn(Optional.of(carMocked));
        // when:
        Optional<Car> car = carService.getById("VINID01");
        //then:
        assertEquals("Mercedes", car.get().getBrand());
    }

    @Test
    void givenCar_whenDelete_thenReturnSize0() {

        // given:
        //Mockito.doThrow(new RuntimeException()).when(carService).delete("VINID01");

        // when:
        carService.delete("VINID01");

        //then:
        Mockito.when(carRepository.findById("VINID01")).thenReturn(null);
        Optional<Car> car = carService.getById("VINID01");
        assertEquals(null, car);
    }
}
