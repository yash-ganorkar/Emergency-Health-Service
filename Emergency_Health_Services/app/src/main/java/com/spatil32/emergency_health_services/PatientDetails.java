package com.spatil32.emergency_health_services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Shreyas on 11/28/2016.
 */
public class PatientDetails extends AppCompatActivity
{
    private TextView firstName;
    private TextView lastName;
    private TextView SSN;
    private TextView email;
    private TextView phone;
    private TextView emergencyContact;
    private TextView password;
    private Button ptLogoutButton;

    private String FirstName;
    private String LastName;
    private String ssn;
    private String Email;
    private String Phone;
    private String EmergencyContact;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_details);
        firstName = (TextView)findViewById(R.id.ptFirstName);
        lastName = (TextView)findViewById(R.id.ptLastName);
        SSN = (TextView)findViewById(R.id.ptSSN);
        email = (TextView)findViewById(R.id.ptEmail);
        phone = (TextView)findViewById(R.id.ptPhone);
        emergencyContact = (TextView)findViewById(R.id.ptEmergency);
        password = (TextView)findViewById(R.id.ptPassword);

        Intent intent = this.getIntent();
        this.FirstName = intent.getStringExtra("FirstName");
        this.LastName = intent.getStringExtra("LastName");
        this.ssn = intent.getStringExtra("SSN");
        this.Email = intent.getStringExtra("Email");
        this.Phone = intent.getStringExtra("Phone");
        this.EmergencyContact = intent.getStringExtra("EmergencyContact");
        this.Password = intent.getStringExtra("Password");

        Log.d("Firstname" , this.FirstName);
        Log.d("Lastname" , this.LastName);
        Log.d("SSN" , this.ssn);
        Log.d("Email" , this.Email);
        Log.d("Phone" , this.Phone);
        Log.d("EmergencyContact" , this.EmergencyContact);
        Log.d("Password" , this.Password);

        firstName.setText(this.FirstName);
        lastName.setText(this.LastName);
        SSN.setText(this.ssn);
        email.setText(this.Email);
        phone.setText(this.Phone);
        emergencyContact.setText(this.EmergencyContact);
        password.setText(this.Password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_logout was selected
            case R.id.action_logout:
                finish();
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
