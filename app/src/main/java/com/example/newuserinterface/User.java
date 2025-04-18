package com.example.newuserinterface;

import com.orm.SugarRecord;

public class User extends SugarRecord<User> {

    private String name;
    private String lastname;
    private String email;
    private String password;

    // Constructor vacío requerido por SugarORM
    public User() {
    }

    // Constructor para crear un usuario
    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    // Métodos getter y setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

