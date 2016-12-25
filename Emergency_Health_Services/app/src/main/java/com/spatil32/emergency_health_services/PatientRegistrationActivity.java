package com.spatil32.emergency_health_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientRegistrationActivity extends AppCompatActivity {

    private SqlHelper db = new SqlHelper(this);
    EditText evFirstName;
    EditText evLastName;
    EditText evEmail;
    EditText evSSN;
    EditText evContact;
    EditText evEmgContact;
    EditText evPassword;
    EditText evConfirmPassword;
    Button bRegister;
    Button bCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        evFirstName = (EditText) findViewById(R.id.evFirstName);
        evLastName = (EditText) findViewById(R.id.evLastName);
        evEmail = (EditText) findViewById(R.id.evEmail);
        evSSN = (EditText) findViewById(R.id.evSSN);
        evContact = (EditText) findViewById(R.id.evContact);
        evEmgContact = (EditText) findViewById(R.id.evEmgContact);
        evPassword = (EditText) findViewById(R.id.evPassword);
        evConfirmPassword = (EditText) findViewById(R.id.evConfirmPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        bCancel = (Button) findViewById(R.id.bCancel);

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Cancel Event Called", "registration activity");
                finish();
                // setContentView(R.layout.activity_main);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Register Event Called", "registration activity");
                if (!(evPassword.getText().toString().equals(evConfirmPassword.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Passwords not matching.", Toast.LENGTH_SHORT).show();
                }
                else if (evFirstName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "First Name can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (evLastName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Last Name can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (evSSN.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "SSN can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (evEmail.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Email can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (evContact.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Contact can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (evEmgContact.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Emergency Contact can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if(evContact.getText().toString().equals(evEmgContact.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Emergency Contact can not be same as own contact.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Patient newPatient = new Patient(evFirstName.getText().toString(), evLastName.getText().toString(),
                            evEmail.getText().toString(), evSSN.getText().toString(),
                            evContact.getText().toString(), evEmgContact.getText().toString(),
                            evPassword.getText().toString());

                    Patient alreadyRegisteredByPhone = db.getPatientByPhoneNumber(newPatient.getPhoneNo());
                    Patient alreadyRegisteredBySSN = db.getPatientBySSN(newPatient);
                    if(alreadyRegisteredByPhone != null)
                    {
                        Toast.makeText(getApplicationContext(), "Patient with this phone already exists.", Toast.LENGTH_SHORT).show();
                    }
                    else if(alreadyRegisteredBySSN != null)
                    {
                        Toast.makeText(getApplicationContext(), "Patient with this SSN already exists.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User newUser = new User(evEmail.getText().toString(), evPassword.getText().toString(), "No");
                        db.addUser(newUser);
                        db.addPatient(newPatient);
                        Toast.makeText(getApplicationContext(), "Patient Registered.", Toast.LENGTH_SHORT).show();
                        String mailBody = "Greetings " + newPatient.getFirstName() + " !!"
                                + " Welcome to Emergency Health Services. \n"
                                + "Please make a note of your credentials for login purposes : \n"
                                + "Username : " + newPatient.getEmail()
                                + "\nPassword : " + newPatient.getPassword()
                                + "\n\t We hope you like our services.\n Regards,\nEHS Team.";

                        sendEMail(newPatient, mailBody);
                        launchFirstActivity();
                    }
                }
            }
        });
    }

    public void sendEMail(Patient newPatient, String message) {
        SendMail sm = new SendMail(this, newPatient.getEmail(), "Welcome to EHS..!!", message);
        sm.execute();
    }

    // this method is used to launch the second activity and pass username through intent
    private void launchFirstActivity(){
        Intent mainActivity = new Intent(PatientRegistrationActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
