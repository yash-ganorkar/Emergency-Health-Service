package com.spatil32.emergency_health_services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private Button loginButton;
    private Button registerButton;
    private Switch switchButton;
    private SqlHelper db = new SqlHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {

        }
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.SEND_SMS},1);


        //display registered patients
        //will return NULL if application is installed for first time
        db.getAllPatients();
        //display registered doctors
        //will return NULL if application is installed for first time
        db.getAllDoctors();

        //Since, this is prototype, we have pre-populated TREATMENTS table with few patients data
        //At launch of application delete pre-populated data to avoid duplicates in table
        db.deleteAllTreatments();
        //pre-populate TREATMENTS table on application start up
        db.addTreatments();
        //display all pre-populated treatments
        db.getAllTreatments();

        email = (TextView)findViewById(R.id.txtLoginEmail);
        password = (TextView)findViewById(R.id.txtLoginPassword);
        loginButton = (Button)findViewById(R.id.btnLoginDoctor);
        registerButton = (Button)findViewById(R.id.btnRegisterDoctor);
        //switch used to login/register as Patient or Doctor
        switchButton = (Switch) findViewById(R.id.switchButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Email cannot be blank.", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Password cannot be blank.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //show all users table data
                    db.getAllUsers();
                    //if logged in as DOCTOR
                    if(switchButton.isChecked())
                    {
                        //doctor log in checked here
                        Log.i("email",email.getText().toString());
                        Log.i("pwd: ",password.getText().toString());
                        User loginUser = db.getUser(email.getText().toString(), password.getText().toString());
                        if(loginUser == null)
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (loginUser.getIsDoctor().equals("No")) {
                                Toast.makeText(getApplicationContext(), "No such doctor exists.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), loginUser.toString() + " logged in.", Toast.LENGTH_SHORT).show();
                                //this funtion opens activity of doctor to search patient treatment history
                                launchDoctorActivity(loginUser);
                            }
                        }
                    }
                    else    //If logged in as PATIENT
                    {
                        //User log in checked here
                        Log.i("email",email.getText().toString());
                        Log.i("pwd: ",password.getText().toString());
                        User loginUser = db.getUser(email.getText().toString(), password.getText().toString());
                        if(loginUser == null)
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (loginUser.getIsDoctor().equals("Yes")) {
                                Toast.makeText(getApplicationContext(), "No such user exists.", Toast.LENGTH_SHORT).show();
                            } else {
                                //User code goes here
                                Toast.makeText(getApplicationContext(), loginUser.toString() + " logged in.", Toast.LENGTH_SHORT).show();
                                //This function opens layout to show patient profile and logout
                                launchUserViewActivity(db, loginUser);
                            }
                        }
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open registration layout according to switch position
                launchSecondActivity();
            }
        });
    }

    // this method is used to launch the second activity and pass username through intent
    private void launchSecondActivity()
    {
        if(switchButton.isChecked())
        {
            Intent secondActivity = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(secondActivity);
        }
        else {
            Intent secondActivity = new Intent(MainActivity.this, PatientRegistrationActivity.class);
            startActivity(secondActivity);
        }
    }

    //patient details
    private void launchUserViewActivity(SqlHelper db, User loginUser)
    {
        Patient findPatient = db.getPatientByEmail(loginUser.getEmail());
        Intent intent = new Intent(this, PatientDetails.class);
        intent.putExtra("SSN", findPatient.getSsn());
        intent.putExtra("FirstName", findPatient.getFirstName());
        intent.putExtra("LastName", findPatient.getLastName());
        intent.putExtra("Email", findPatient.getEmail());
        intent.putExtra("Phone", findPatient.getPhoneNo());
        intent.putExtra("EmergencyContact", findPatient.getEmgPhoneNo());
        intent.putExtra("Password", findPatient.getPassword());

        this.startActivity(intent);
    }

    //search patient history
    private void launchDoctorActivity(User loginUser)
    {
        Intent secondActivity = new Intent(MainActivity.this, DoctorActivity.class);
        secondActivity.putExtra("doctorEmail",loginUser.getEmail());
        startActivity(secondActivity);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
