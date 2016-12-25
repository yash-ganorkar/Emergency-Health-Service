package com.spatil32.emergency_health_services;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Shreyas on 11/29/2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity Context;
    private final String[] diseaseNames;
    private final String[] diseaseTreatments;

    public CustomListAdapter(Activity context, String[] diseaseNames, String[] diseaseTreatments)
    {
        super(context, R.layout.patient_treatments, diseaseNames);
        this.Context = context;
        this.diseaseNames = diseaseNames;
        this.diseaseTreatments = diseaseTreatments;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = Context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.patient_treatments, null, true);
        TextView ListViewDiseaseNames = (TextView)ListViewSingle.findViewById(R.id.txtDiseaseName);
        TextView ListViewDiseaseTreatments = (TextView)ListViewSingle.findViewById(R.id.txtTratment);

        ListViewDiseaseNames.setText(diseaseNames[position]);
        ListViewDiseaseTreatments.setText(diseaseTreatments[position]);
        return ListViewSingle;
    }
}
