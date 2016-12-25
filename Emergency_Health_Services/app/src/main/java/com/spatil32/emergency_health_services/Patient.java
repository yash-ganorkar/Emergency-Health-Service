package com.spatil32.emergency_health_services;

/**
 * Created by Harshal on 11/26/2016.
 */

public class Patient extends User{
    private String firstName;
    private String lastName;
    private String email;
    private String ssn;
    private String phoneNo;
    private String emgPhoneNo;
    private String password;
    private String confirmPassword;

    public Patient(){   }

    public Patient(String firstName, String lastName, String email, String ssn, String phoneNo,
                   String emgPhoneNo, String password) {
        super(email, password, "No");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.ssn = ssn;
        this.phoneNo = phoneNo;
        this.emgPhoneNo = emgPhoneNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmgPhoneNo() {
        return emgPhoneNo;
    }

    public void setEmgPhoneNo(String emgPhoneNo) {
        this.emgPhoneNo = emgPhoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "Patient Data: First Name = " + this.getFirstName() + " ,Last Name = " + this.getLastName()
                + " ,SSN = " + this.getSsn() + " ,Phone No = " + this.getPhoneNo() + " ,Emergency Contact = " +
                this.emgPhoneNo + " ,Email = " + this.getEmail() + " ,Password = " + this.getPassword();
    }
}
