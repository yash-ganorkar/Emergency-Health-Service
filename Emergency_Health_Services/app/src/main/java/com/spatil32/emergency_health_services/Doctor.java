package com.spatil32.emergency_health_services;

/**
 * Created by Shreyas on 11/26/2016.
 */
public class Doctor extends User
{
    private String hospitalName;
    private String hospitalAddress;
    private String doctorName;
    private String email;
    private String password;
    private String SSN;
    private String contact;

    public Doctor(){
        super();
    }

    public Doctor(String hospitalname, String hospitalAddress, String SSN, String doctorName, String email, String password, String contact)
    {
        super(email, password, "Yes");
        this.hospitalName = hospitalname;
        this.hospitalAddress = hospitalAddress;
        this.SSN = SSN;
        this.email = email;
        this.password = password;
        this.doctorName = doctorName;
        this.contact = contact;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSSN() {
        return SSN;
    }

    public String getContact() {
        return contact;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Doctor Info : Hospital Name = " + this.getHospitalName() + " ,Hospital Address = " + this.getHospitalAddress()
                + " ,Doctor Name " + this.getDoctorName() + " ,SSN = " + this.getSSN() + " ,Email = " + this.getEmail()
                + " ,Password = " + this.getPassword() + " ,Contact = " + this.getContact();
    }
}