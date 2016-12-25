package com.spatil32.emergency_health_services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Shreyas on 11/29/2016.
 */
public class DoctorActivity extends AppCompatActivity {
    private EditText patientSearch;
    private Button btnSearchPatient;
    private ListView patientDiseaseDetails;
    private ListAdapter listAdapter;
    private Button btnEmergencyCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        final SqlHelper db = new SqlHelper(this);

        Intent intent = this.getIntent();
        String doctorEmail = intent.getStringExtra("doctorEmail");
        final Doctor loggedInDoctor = db.getDoctorByEmail(doctorEmail);
        //search bar to find patient by phone number
        patientSearch = (EditText) findViewById(R.id.searchPatient);
        //button to search patient
        btnSearchPatient = (Button)findViewById(R.id.btnSearchPatient);
        //ListView to show diseases and treatments of patient
        patientDiseaseDetails = (ListView)findViewById(R.id.lstdiseaseList);

        //doctor logout button
        //btnDoctorLogout = (Button)findViewById(R.id.btnDoctorLogout);
        //onclick action for search patient history button
        btnSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientPhoneNumber = patientSearch.getText().toString();
                if (patientPhoneNumber.equals(""))
                    Toast.makeText(getApplicationContext(), "Please Enter Contact", Toast.LENGTH_SHORT).show();
                else {
                    //Toast.makeText(getApplicationContext(), "Phone = " + patientPhoneNumber, Toast.LENGTH_SHORT).show();

                    //First get patient by input phone number
                    Patient patientByPhone = db.getPatientByPhoneNumber(patientPhoneNumber);
                    if(patientByPhone == null)
                    {
                        Toast.makeText(getApplicationContext(), "Patient with this number doesn't exist." , Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Then find patientdetails by SSN
                        final Patient patientBySSN = db.getPatientBySSN(patientByPhone);
                        //Toast.makeText(getApplicationContext(), "Patient By SSN = " + patientBySSN.toString(), Toast.LENGTH_SHORT).show();

                        //List of all the treatments taken by patient from TREATMENT table
                        List<Treatments> allTreatmentsBySSN = db.getAllTratmentsBySSN(patientBySSN);

                        //String array for disease name and treatment to show in ListView
                        final String[] populatedDiseaseNames = new String[allTreatmentsBySSN.size()];
                        final String[] populatedTreatmentsNames = new String[allTreatmentsBySSN.size()];

                        //Populating string arrays for list view
                        for (int i = 0; i < populatedDiseaseNames.length; i++) {
                            populatedDiseaseNames[i] = allTreatmentsBySSN.get(i).getDiseaseName();
                        }

                        for (int i = 0; i < populatedDiseaseNames.length; i++) {
                            populatedTreatmentsNames[i] = allTreatmentsBySSN.get(i).getTreatment();
                        }

                        //custom list adapter object
                        listAdapter = new CustomListAdapter(DoctorActivity.this, populatedDiseaseNames, populatedTreatmentsNames);
                        //After populating listview, display emergency contact button to send message
                        btnEmergencyCase = (Button) findViewById(R.id.btnInformEmgContacts);
                        btnEmergencyCase.setVisibility(View.VISIBLE);

                        patientDiseaseDetails.setAdapter(listAdapter);
                        btnEmergencyCase.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String emergencyContact = patientBySSN.getEmgPhoneNo();
                                String message = "Alert!!, This is 911 Emergency Services. Your relative " + patientBySSN.getFirstName()
                                        + " is in need of hospitalization and we are taking him/her to the " +
                                        loggedInDoctor.getHospitalName() + " Hospital located at "
                                        + loggedInDoctor.getHospitalAddress() + ". Please arrive soon.";
                                Uri uri = Uri.parse("smsto:" + emergencyContact);
                                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                                smsIntent.putExtra("sms_body", message);
                                startActivity(smsIntent);
                                Toast.makeText(getApplicationContext(), "Message sent to " + emergencyContact, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
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
