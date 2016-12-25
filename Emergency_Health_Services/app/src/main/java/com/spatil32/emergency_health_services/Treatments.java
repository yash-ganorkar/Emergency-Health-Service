package com.spatil32.emergency_health_services;

/**
 * Created by Shreyas on 11/29/2016.
 */
public class Treatments
{
    private String id;
    private String SSN;
    private String diseaseName;
    private String treatment;

    public Treatments()
    {
    }

    public Treatments(String SSN, String diseaseName, String treatment)
    {
        super();
        this.SSN = SSN;
        this.diseaseName = diseaseName;
        this.treatment = treatment;
    }

    public String getId() {
        return id;
    }

    public String getSSN() {
        return SSN;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    @Override
    public String toString() {
        return "Treatment details : Id = " + this.id + "SSN = "
                + this.getSSN() + "Disease Name = " + this.diseaseName + " ,Medicines = " + this.treatment + "\n" ;
    }
}
