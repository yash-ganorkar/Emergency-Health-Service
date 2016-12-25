package com.spatil32.emergency_health_services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shreyas on 11/26/2016.
 */
public class SqlHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "ehsDB";
    // Doctor table name
    private static final String TABLE_DOCTOR = "DOCTOR";
    // Doctor table name
    private static final String TABLE_USER = "USER";
    // Patient table name
    private static final String TABLE_PATIENT = "PATIENT";
    // Patient table name
    private static final String TABLE_TREATMENTS = "TREATMENTS";

    // DOCTOR Table Columns names
    private static final String KEY_SSN = "SSN";
    private static final String KEY_HOSPITALNAME = "hospitalName";
    private static final String KEY_HOSPITALADDRESS = "hospitalAddress";
    private static final String KEY_DOCTORNAME = "doctorName";
    private static final String KEY_ISDOCTOR = "isDoctor";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_CONTACT = "Contact";

    // Patient Table Columns names
    private static final String KEY_FIRSTNAME = "firstName";
    private static final String KEY_LASTNAME = "lastName";
    private static final String KEY_PHONE_NO = "phoneNo";
    private static final String KEY_EMG_PHONE_NO = "emgPhoneNo";

    // Treatment Table Columns names
    private static final String KEY_DISEASENAME = "diseaseName";
    private static final String KEY_TREATMENT = "treatment";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DOCTOR_TABLE = "CREATE TABLE DOCTOR ( " + "SSN Integer PRIMARY KEY, " +
                "hospitalName TEXT, " + "hospitalAddress TEXT, " + "doctorName TEXT, " + "Email TEXT, " +
                "Password TEXT, " + "Contact TEXT )";

        String CREATE_USER_TABLE = "CREATE TABLE USER ( " + "id Integer PRIMARY KEY AUTOINCREMENT, " +
                "Email TEXT, " + "Password TEXT," + "isDoctor TEXT )";

        String CREATE_PATIENT_TABLE = "CREATE TABLE PATIENT (" +
                "SSN INTEGER PRIMARY KEY, " +
                "firstName TEXT," +
                "lastName TEXT, " +
                "phoneNo TEXT,"+
                "Email TEXT,"+
                "Password TEXT,"+
                "emgPhoneNo TEXT)";

        String CREATE_TREATMENTS_TABLE = "CREATE TABLE TREATMENTS ( " + "id Integer PRIMARY KEY AUTOINCREMENT, " +
                "SSN TEXT, " +
                "diseaseName TEXT, " + "treatment TEXT )";


        db.execSQL(CREATE_DOCTOR_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PATIENT_TABLE);
        db.execSQL(CREATE_TREATMENTS_TABLE);
        //addTreatments();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exists
        db.execSQL("DROP TABLE IF EXISTS DOCTOR");
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS PATIENT");
        db.execSQL("DROP TABLE IF EXISTS TREATMENTS");
        // create fresh tables
        this.onCreate(db);
    }

    public void addDoctor(Doctor doctor)
    {
        Log.d("Add New Doctor", doctor.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_SSN, doctor.getSSN()); // get SSN
        values.put(KEY_HOSPITALNAME, doctor.getHospitalName()); // get hospital name
        values.put(KEY_HOSPITALADDRESS, doctor.getHospitalAddress()); // get hospital address
        values.put(KEY_DOCTORNAME, doctor.getDoctorName()); // get doctor name
        values.put(KEY_EMAIL, doctor.getEmail()); // get email
        values.put(KEY_PASSWORD, doctor.getPassword()); // get password
        values.put(KEY_CONTACT, doctor.getContact()); // get contact
        // 3. insert
        db.insert(TABLE_DOCTOR, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values
        // 4. Close dbase
        db.close();
    }

    public void addUser(User user)
    {
        Log.d("Add New User", user.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail()); // get email
        values.put(KEY_PASSWORD, user.getPassword()); // get password
        values.put(KEY_ISDOCTOR, user.getIsDoctor()); // get isDoctor
        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values
        // 4. Close dbase
        db.close();
    }

    public User getUser(String email, String password)
    {
        Log.d("User Email:",email);
        Log.d("User Pwd:",password);
        // 1. build the query
        String query = "SELECT Email, Password, isDoctor FROM "+ TABLE_USER + " WHERE Email = ? and Password = ?";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {email, password});
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                // user.setId(cursor.getInt(0));
                user.setEmail(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setIsDoctor(cursor.getString(2));
                Log.d("User Retrieved ->", user.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user; // return user
    }

    /*CRUD operations (create "add", read "get", update, delete) */
    void addPatient(Patient newPatient){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_SSN, newPatient.getSsn()); // get rating
        values.put(KEY_FIRSTNAME, newPatient.getFirstName()); // get title
        values.put(KEY_LASTNAME, newPatient.getLastName()); // get author
        values.put(KEY_PHONE_NO, newPatient.getPhoneNo()); // get rating
        values.put(KEY_EMAIL, newPatient.getEmail()); // get rating
        values.put(KEY_PASSWORD, newPatient.getPassword()); // get password
        values.put(KEY_EMG_PHONE_NO, newPatient.getEmgPhoneNo()); // get rating
        // 3. insert
        db.insert(TABLE_PATIENT, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values
        // 4. Close dbase
        db.close();
    }

    public void addTreatments()
    {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        //Adding 5 treatments
        ContentValues[] values = new ContentValues[10];

        //1st treatment data
        values[0] = new ContentValues();
        values[0].put(KEY_SSN, "123454321");
        values[0].put(KEY_DISEASENAME, "Cold");
        values[0].put(KEY_TREATMENT, "Benadryl: Diphenhydramine: Benadryl: Chlorpheniramine");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[0]); // key/value -> keys = column names/values

        //2nd treatment data
        values[1] = new ContentValues();
        values[1].put(KEY_SSN, "123454321");
        values[1].put(KEY_DISEASENAME, "Anxiety");
        values[1].put(KEY_TREATMENT, "Xanax: Ativan: Valium");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[1]); // key/value -> keys = column names/values

        //3rd treatment data
        values[2] = new ContentValues();
        values[2].put(KEY_SSN, "1234554321");
        values[2].put(KEY_DISEASENAME, "Asthma");
        values[2].put(KEY_TREATMENT, "Singulair: Aerobid: Dulera");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[2]); // key/value -> keys = column names/values

        //4th treatment data
        values[3] = new ContentValues();
        values[3].put(KEY_SSN, "123454321");
        values[3].put(KEY_DISEASENAME, "Bronchitis");
        values[3].put(KEY_TREATMENT, "Augmentin: Zithromax: Doxycycline: Amoxil");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[3]); // key/value -> keys = column names/values

        //5th treatment data
        values[4] = new ContentValues();
        values[4].put(KEY_SSN, "123454321");
        values[4].put(KEY_DISEASENAME, "Cancer");
        values[4].put(KEY_TREATMENT, "Cytoxan: Adrucil: Ethyol: Leukeran");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[4]); // key/value -> keys = column names/values


        //6th treatment data
        values[5] = new ContentValues();
        values[5].put(KEY_SSN, "987656789");
        values[5].put(KEY_DISEASENAME, "Hepatitis A");
        values[5].put(KEY_TREATMENT, "GamaSTAN: Globulin Intramuscular");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[5]); // key/value -> keys = column names/values

        //7th treatment data
        values[6] = new ContentValues();
        values[6].put(KEY_SSN, "987656789");
        values[6].put(KEY_DISEASENAME, "Thyroid");
        values[6].put(KEY_TREATMENT, "Synthroid: Tirosint: Novothyrox");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[6]); // key/value -> keys = column names/values

        //8th treatment data
        values[7] = new ContentValues();
        values[7].put(KEY_SSN, "987656789");
        values[7].put(KEY_DISEASENAME, "Schizophrenia");
        values[7].put(KEY_TREATMENT, "Abilify: Zyprexa: Clozaril: Invega");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[7]); // key/value -> keys = column names/values

        //9th treatment data
        values[8] = new ContentValues();
        values[8].put(KEY_SSN, "987656789");
        values[8].put(KEY_DISEASENAME, "Psychiatric Disorders");
        values[8].put(KEY_TREATMENT, "Zyprexa Zydis: Risperidone: Zyprexa");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[8]); // key/value -> keys = column names/values

        //10th treatment data
        values[9] = new ContentValues();
        values[9].put(KEY_SSN, "987656789");
        values[9].put(KEY_DISEASENAME, "Ischemic Stroke");
        values[9].put(KEY_TREATMENT, "Plavix: Aspir 81: Easprin");
        db.insert(TABLE_TREATMENTS, // table
                null, //nullColumnHack
                values[9]); // key/value -> keys = column names/values

        // 4. Close dbase
        db.close();
    }

    public List<Treatments> getAllTratmentsBySSN(Patient patient)
    {
        List<Treatments> treatments = new ArrayList<>();
        // 1. build the query
        String query = "SELECT *  FROM "+ TABLE_TREATMENTS + " WHERE SSN = ?";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {patient.getSsn()});
        // 3. go over each row, build user and add it to list
        Treatments treatment = null;
        if (cursor.moveToFirst()) {
            do {
                treatment = new Treatments();
                treatment.setId(cursor.getString(0));
                treatment.setSSN(cursor.getString(1));
                treatment.setDiseaseName(cursor.getString(2));
                treatment.setTreatment(cursor.getString(3));
                // Add treatment to treatments
                treatments.add(treatment);
            } while (cursor.moveToNext());
        }
        Log.d("getAllTreatmentsBySSN()", treatments.toString());
        return treatments; // return treatments
    }

    public Patient getPatientByEmail(String email)
    {
        Patient findPatient = null;
        // 1. build the query
        String query = "SELECT * FROM "+ TABLE_PATIENT + " WHERE Email = ?";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {email});
        if (cursor.moveToFirst()) {
            do {
                findPatient = new Patient();
                findPatient.setSsn(cursor.getString(0));
                findPatient.setFirstName(cursor.getString(1));
                findPatient.setLastName(cursor.getString(2));
                findPatient.setPhoneNo(cursor.getString(3));
                findPatient.setEmail(cursor.getString(4));
                findPatient.setPassword(cursor.getString(5));
                findPatient.setEmgPhoneNo(cursor.getString(6));
                Log.d("Patient Retrieved ->", findPatient.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return findPatient; // return user
    }

    public Doctor getDoctorByEmail(String email)
    {
        Doctor findDoctor = null;
        // 1. build the query
        String query = "SELECT * FROM "+ TABLE_DOCTOR + " WHERE Email = ?";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {email});
        if (cursor.moveToFirst()) {
            do {
                findDoctor = new Doctor();
                findDoctor.setSSN(cursor.getString(0));
                findDoctor.setHospitalName(cursor.getString(1));
                findDoctor.setHospitalAddress(cursor.getString(2));
                findDoctor.setDoctorName(cursor.getString(3));
                findDoctor.setEmail(cursor.getString(4));
                findDoctor.setPassword(cursor.getString(5));
                findDoctor.setContact(cursor.getString(6));
                Log.d("Doctor Retrieved ->", findDoctor.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return findDoctor; // return doctor
    }

    public Patient getPatientByPhoneNumber(String phone)
    {
        Patient findPatient = null;
        // 1. build the query
        String query = "SELECT * FROM "+ TABLE_PATIENT + " WHERE phoneNo = ?";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {phone});
        if (cursor.moveToFirst()) {
            do {
                findPatient = new Patient();
                findPatient.setSsn(cursor.getString(0));
                findPatient.setFirstName(cursor.getString(1));
                findPatient.setLastName(cursor.getString(2));
                findPatient.setPhoneNo(cursor.getString(3));
                findPatient.setEmail(cursor.getString(4));
                findPatient.setPassword(cursor.getString(5));
                findPatient.setEmgPhoneNo(cursor.getString(6));
                Log.d("Patient by Phone ->", findPatient.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return findPatient; // return user
    }

    public Patient getPatientBySSN(Patient patient)
    {
        Patient findPatient = null;
        // 1. build the query
        String query = "SELECT * FROM "+ TABLE_PATIENT + " WHERE SSN = ?";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {patient.getSsn()});
        if (cursor.moveToFirst()) {
            do {
                findPatient = new Patient();
                findPatient.setSsn(cursor.getString(0));
                findPatient.setFirstName(cursor.getString(1));
                findPatient.setLastName(cursor.getString(2));
                findPatient.setPhoneNo(cursor.getString(3));
                findPatient.setEmail(cursor.getString(4));
                findPatient.setPassword(cursor.getString(5));
                findPatient.setEmgPhoneNo(cursor.getString(6));
                Log.d("Patient By SSN ->", findPatient.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return findPatient; // return user
    }


    // Updating single book
    int updatePatient(Patient updatePatient){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("ssn", updatePatient.getSsn()); // get author
        values.put("firstName", updatePatient.getFirstName()); // get title
        values.put("lastName", updatePatient.getLastName()); // get author
        values.put("phoneNo", updatePatient.getPhoneNo()); // get author
        values.put("Email", updatePatient.getEmail()); // get title
        values.put("Password", updatePatient.getPassword()); // get password
        values.put("emgPhoneNo", updatePatient.getEmgPhoneNo()); // get author
        // 3. updating row
        int i = db.update(TABLE_PATIENT, //table
                values, // column/value
                KEY_SSN+" = ?", // selections
                new String[] { String.valueOf(updatePatient.getSsn()) }); //selection args
        // 4. close dbase
        db.close();
        Log.d("UpdatePatient", updatePatient.toString());
        return i;
    }

    public List<User> getAllUsers()
    {
        List<User> users = new LinkedList<>();
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_USER;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setEmail(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setIsDoctor(cursor.getString(2));
                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }
        Log.d("getAllUser()", users.toString());
        return users; // return users
    }

    public List<Treatments> getAllTreatments()
    {
        List<Treatments> treatments = new LinkedList<>();
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_TREATMENTS;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build user and add it to list
        Treatments treatment = null;
        if (cursor.moveToFirst()) {
            do {
                treatment = new Treatments();
                treatment.setSSN(cursor.getString(1));
                treatment.setDiseaseName(cursor.getString(2));
                treatment.setTreatment(cursor.getString(3));
                // Add treatment to treatments
                treatments.add(treatment);
            } while (cursor.moveToNext());
        }
        Log.d("getAllTreatemnts()", treatments.toString());
        return treatments; // return treatments
    }

    public List<Patient> getAllPatients()
    {
        List<Patient> patients = new LinkedList<>();
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_PATIENT;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build user and add it to list
        Patient findPatient = null;
        if (cursor.moveToFirst()) {
            do {
                findPatient = new Patient();
                findPatient.setSsn(cursor.getString(0));
                findPatient.setFirstName(cursor.getString(1));
                findPatient.setLastName(cursor.getString(2));
                findPatient.setPhoneNo(cursor.getString(3));
                findPatient.setEmail(cursor.getString(4));
                findPatient.setPassword(cursor.getString(5));
                findPatient.setEmgPhoneNo(cursor.getString(6));
                // Add user to users
                patients.add(findPatient);
            } while (cursor.moveToNext());
        }
        Log.d("getAllPatients()", patients.toString());
        return patients; // return users
    }

    public List<Doctor> getAllDoctors()
    {
        List<Doctor> doctors = new LinkedList<>();
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_DOCTOR;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build user and add it to list
        Doctor findDoctor = null;
        if (cursor.moveToFirst()) {
            do {
                findDoctor = new Doctor();
                findDoctor.setHospitalName(cursor.getString(0));
                findDoctor.setHospitalAddress(cursor.getString(1));
                findDoctor.setDoctorName(cursor.getString(2));
                findDoctor.setSSN(cursor.getString(3));
                findDoctor.setEmail(cursor.getString(4));
                findDoctor.setPassword(cursor.getString(5));
                findDoctor.setContact(cursor.getString(6));
                // Add user to users
                doctors.add(findDoctor);
            } while (cursor.moveToNext());
        }
        Log.d("getAllDoctors()", doctors.toString());
        return doctors; // return doctors
    }

    public void deleteAllTreatments()
    {
        // 1. build the query
        String query = "DELETE FROM " + TABLE_TREATMENTS;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }
}