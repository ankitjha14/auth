package com.auth.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String picture;

    public User(String firstname, String lastname, String email, String picture) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.picture = picture;
    }

    public User(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public User(){
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPicture() {
        return picture;
    }

}