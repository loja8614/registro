package com.officehours.registry.util;

import com.officehours.registry.model.People;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PeopleDataTest {
    public static People getPeopleMocked() {
        People peopleMocked = new People();
        peopleMocked.setId(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));
        peopleMocked.setFirstname("Pedro");
        peopleMocked.setLastname("Lopez");
        peopleMocked.setEmail("pedro@email.com");
        peopleMocked.setGender("Male");
        return peopleMocked;
    }

    public static People getPeopleUpdatedMocked() {
        People peopleMocked = new People();
        peopleMocked.setId(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"));
        peopleMocked.setFirstname("Pedro");
        peopleMocked.setLastname("Ramirez");
        peopleMocked.setEmail("pedroRam@email.com");
        peopleMocked.setGender("Male");
        return peopleMocked;
    }

    public static List<People> getLstPeopleMocked() {
        List<People> lstPeopleMocked = new ArrayList<>();
        People peopleMocked = new People(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bce"), "Pedro", "Lopez", "pedro@email.com", "Male");
        lstPeopleMocked.add(peopleMocked);
        peopleMocked = new People(UUID.fromString("32611be5-33f6-4e5c-996d-9ad88bcb2bdd"), "Juan", "Perez", "juan@email.com", "Male");
        lstPeopleMocked.add(peopleMocked);
        peopleMocked = new People(UUID.fromString("33611be5-33f6-4e5c-996d-9ad88bcb2bdd"), "Maria", "Vazquez", "mary@email.com", "Female");
        lstPeopleMocked.add(peopleMocked);
        return lstPeopleMocked;
    }


}
