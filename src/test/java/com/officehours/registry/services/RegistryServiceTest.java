package com.officehours.registry.services;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.model.Registry;
import com.officehours.registry.repository.RegistryRepository;
import com.officehours.registry.util.CarDataTest;
import com.officehours.registry.util.PeopleDataTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RegistryServiceTest {

    @InjectMocks
    RegistryServiceImpl registryService;

    @Mock
    RegistryRepository registryRepository;

    @Test
    void givenRegistry_whenSave_thenReturnRegistrySaved() {
        //given:
        Car carMocked = CarDataTest.getMockCar();
        People peopleMocked = PeopleDataTest.getPeopleMocked();
        Registry registryMocked = new Registry();
        registryMocked.setId(1);
        registryMocked.setPeople(peopleMocked);
        registryMocked.setCar(carMocked);
        Mockito.when(registryRepository.save(registryMocked)).thenReturn(registryMocked);
        //when
        Registry registrySaved = registryService.save(registryMocked);
        //then
        assertEquals(1, registrySaved.getId());

    }

    @Test
    void givenPeopleId_whenGetCars_thenGetTwoCars() {
        //given:
        UUID idPeople = UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce");
        List<Registry> lstRegistryMocked = new ArrayList<>();
        People peopleMocked = PeopleDataTest.getPeopleMocked();
        List<Car> lstCarMocked = CarDataTest.getAllCarsMocked();
        int i = 1;
        for (Car car : lstCarMocked
        ) {
            Registry registryMock=new Registry(i,peopleMocked,car);
            lstRegistryMocked.add(registryMock);
            i++;
        }

        Mockito.when(registryRepository.findByPeopleId(idPeople)).thenReturn(lstRegistryMocked);

        //when:
        List<Car> lstCars = registryService.getCarsByPeople(idPeople);

        //then:
        assertEquals(3,lstCars.size());

    }

    @Test
    void givenCarVin_whenGetPeopleByCar_thenPeopleRegistry() {
        //given:
        String vin = "VINID";
        List<Registry> lstRegistryMocked = new ArrayList<>();
        List<People> lstPeopleMocked = new ArrayList<>();
        lstPeopleMocked.add(PeopleDataTest.getPeopleMocked());
        Car carMocked = CarDataTest.getMockCar();
        int i = 1;
        for (People people : lstPeopleMocked
        ) {
            Registry registryMock=new Registry(i,people,carMocked);
            lstRegistryMocked.add(registryMock);
            i++;
        }

        Mockito.when(registryRepository.findByCarVin(vin)).thenReturn(lstRegistryMocked);

        //when:
        List<People> lstPeopleByCar = registryService.getPeopleByCar(vin);

        //then:
        assertEquals(1,lstPeopleByCar.size());

    }
}