package com.officehours.registry.model;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "registry", name ="people")
public class People {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "fisrtname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;


    public People() {
    }

    public People(UUID id) {
        this.id = id;
    }

    public People(UUID id, String firstname, String lastname, String email, String gender) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
