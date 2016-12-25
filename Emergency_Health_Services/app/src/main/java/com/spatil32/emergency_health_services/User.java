package com.spatil32.emergency_health_services;

/**
 * Created by Shreyas on 11/27/2016.
 */
public class User {
    private String email;
    private String password;
    private String isDoctor;

    public User() {     }

    public User(String email, String password, String isDoctor)
    {
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getIsDoctor() {
        return isDoctor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsDoctor(String isDoctor) {
        this.isDoctor = isDoctor;
    }

    @Override
    public String toString() {
        return "New User : Username = " + this.email + " ,Password = " + this.password;
    }
}