package com.spatil32.emergency_health_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shreyas on 11/27/2016.
 */
public class RegistrationActivity extends AppCompatActivity
{
    private TextView hospitalName;
    private TextView hospitalAddress;
    private TextView doctorName;
    private TextView SSN;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private TextView contact;
    private Button registerDoctorButton;
    private Button cancelRegistrationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final SqlHelper db = new SqlHelper(this);

        hospitalName = (TextView) findViewById(R.id.txtHospitalName);
        hospitalAddress = (TextView) findViewById(R.id.txtHospitalAddress);
        doctorName = (TextView) findViewById(R.id.txtDoctorName);
        SSN = (TextView) findViewById(R.id.txtSSN);
        email = (TextView) findViewById(R.id.txtEmail);
        password = (TextView) findViewById(R.id.txtPassword);
        confirmPassword = (TextView) findViewById(R.id.txtConfirmPassword);
        contact = (TextView) findViewById(R.id.txtContact);
        registerDoctorButton = (Button) findViewById(R.id.btnRegister);
        cancelRegistrationButton = (Button) findViewById(R.id.btnCancel);

        cancelRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Cancel Event Called", "registration activity");
                setContentView(R.layout.activity_main);
            }
        });

        registerDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Rigister Event Called", "registration activity");
                if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Passwords not matching.", Toast.LENGTH_SHORT).show();
                }
                else if (hospitalName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Hospital Name can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (hospitalAddress.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Hospital Address can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (doctorName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Doctor Name can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (SSN.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "SSN can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Email can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Password can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if (contact.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Contact can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Doctor newDoctor = new Doctor(hospitalName.getText().toString(), hospitalAddress.getText().toString(),
                            SSN.getText().toString(), doctorName.getText().toString(), email.getText().toString(),
                            password.getText().toString(), contact.getText().toString());

                    User newUser = new User(email.getText().toString(), password.getText().toString(), "Yes");
                    db.addDoctor(newDoctor);
                    Toast.makeText(getApplicationContext(), "Doctor Registered.", Toast.LENGTH_SHORT).show();
                    db.addUser(newUser);
                    Toast.makeText(getApplicationContext(), "User Added.", Toast.LENGTH_SHORT).show();
                    String mailBody = "Greetings " + newDoctor.getDoctorName() + " !!\n"
                            + " Welcome to Emergency Health Services Doctor's Portal. \n\n"
                            + "We are glad to have you from " + newDoctor.getHospitalName() + "\n"
                            + "Please make a note of your credentials for login purposes : \n"
                            + "Username : " + newDoctor.getEmail()
                            + "\nPassword : " + newDoctor.getPassword()
                            + "\n\n\t We hope you like our services.\n Regards,\nEHS Team.";

                    sendEMail(newDoctor, mailBody);
                    launchFirstActivity();
                }
            }
        });
    }

    public void sendEMail(Doctor newDoctor, String message) {
        SendMail sm = new SendMail(this, newDoctor.getEmail(), "Welcome to EHS..!!", message);
        sm.execute();
    }

    // this method is used to launch the second activity and pass username through intent
    private void launchFirstActivity(){
        Intent mainActivity = new Intent(RegistrationActivity.this, MainActivity.class);
        //secondActivity.putExtra("userName", userName);
        startActivity(mainActivity);
        //finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
