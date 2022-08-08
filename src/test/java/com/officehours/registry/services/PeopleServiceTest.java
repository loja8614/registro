package com.officehours.registry.services;

import com.officehours.registry.model.People;
import com.officehours.registry.repository.PeopleRepository;
import com.officehours.registry.util.PeopleDataTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PeopleServiceTest {
    @InjectMocks
    PeopleServiceImpl peopleService;

    @Mock
    PeopleRepository peopleRepository;

    @Test
    void givenPeople_whenSave_thenReturnPeopleSaved() {
        // given:
        People peopleMocked = PeopleDataTest.getPeopleMocked();
        People people = new People(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), "Pedro", "Lopez", "pedro@email.com", "Male");
        Mockito.when(peopleRepository.save(people)).thenReturn(peopleMocked);
        // when:
        People peopleSaved = peopleService.save(people);
        //then:
        assertEquals(people.getId(), peopleSaved.getId());
    }

    @Test
    void givenLstPeople_whenGetAll_thenReturnSize3() {
        //given:
        List<People> lstPeopleMocked = PeopleDataTest.getLstPeopleMocked();
        Mockito.when(peopleRepository.findAll()).thenReturn(lstPeopleMocked);
        // when:
        List<People> ltsPeopleSaved = peopleService.getAll();
        //then:
        assertEquals(3, ltsPeopleSaved.size());
    }

    @Test
    void givenPeople_whenDelete_thenReturnNull() {
        // given:
        People peopleMocked = PeopleDataTest.getPeopleMocked();
        //Mockito.doThrow(new RuntimeException()).when(peopleService).delete("peopleMocked.getId());

        // when:
        peopleService.delete(peopleMocked.getId());

        //then:
        Mockito.when(peopleRepository.findById(peopleMocked.getId())).thenReturn(null);
        Optional<People> people = peopleRepository.findById(peopleMocked.getId());
        assertEquals(null, people);
    }

    @Test
    void givenPeople_whenGetById_thenGetPeople() {
        // given:
        People peopleMocked = PeopleDataTest.getPeopleMocked();
        Mockito.when(peopleRepository.findById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"))).thenReturn(Optional.of(peopleMocked));

        // when:
        Optional<People> people = peopleService.getById(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));

        //then:
        assertEquals("Pedro", people.get().getFirstname());
        assertEquals("Lopez", people.get().getLastname());
        assertEquals("pedro@email.com", people.get().getEmail());
        assertEquals("Male", people.get().getGender());
        assertEquals(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), people.get().getId());
    }
}