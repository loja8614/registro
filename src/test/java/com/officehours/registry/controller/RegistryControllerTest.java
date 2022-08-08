package com.officehours.registry.controller;

import com.officehours.registry.model.Car;
import com.officehours.registry.model.People;
import com.officehours.registry.model.Registry;
import com.officehours.registry.services.RegistryService;
import com.officehours.registry.util.CarDataTest;
import com.officehours.registry.util.PeopleDataTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RegistryControllerTest {
    @InjectMocks
    RegistryController registryController;

    @Mock
    RegistryService registryService;


    @Test
    void givenNewCarAndNewPeople_whenCreateRelation_thenReturnRegistry(){
        //given:
        Car car = CarDataTest.getMockCar();
        People people= PeopleDataTest.getPeopleMocked();
        Registry registryMock = new Registry(1,people,car);
        Mockito.when(registryService.save(registryMock)).thenReturn(registryMock);
        //when:
        Registry registrySaved = registryController.create(registryMock);
        //then:
        assertEquals(1,registrySaved.getId());
    }

}