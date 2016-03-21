package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;
import com.kaylaflaten.organicfarm.DatabaseCtrl;

import com.firebase.client.Firebase;

/**
 * Created by Carmen on 2/29/2016.
 */
public class CropHarvester extends AppCompatActivity {


    EditText notes;
    EditText date;
    EditText amount;
    CheckBox finished;
    Button back;
    Button enter;

    //section/bed ints
    int secN;
    int bedN;

    //section/bed strings
    String secS;
    String bedS;

    String cropName;
    String cropNotes;
    String cropDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvester);
        Firebase.setAndroidContext(this);

        enter = (Button) findViewById(R.id.enter);
        back = (Button) findViewById(R.id.back);
        date = (EditText) findViewById(R.id.date);
        notes = (EditText) findViewById(R.id.notes);
        finished = (CheckBox) findViewById(R.id.finished);
        amount = (EditText) findViewById(R.id.amount);


        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        final Bundle extras = getIntent().getExtras();

        secS = "Section ";
        bedS = "Bed ";


        if (extras != null) {
            cropName = extras.getString("cropName");
            cropNotes = extras.getString("cropNotes");
            cropDate = extras.getString("cropDate");
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        final String cropID = extras.getString("itemSelected");
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CropHarvester.this, MainActivity.class);
                Harvest newHarvest = new Harvest(date.getText().toString(), Double.parseDouble(amount.getText().toString()), finished.isChecked(), notes.getText().toString(), 1, 1);
                String[] locationHarvest = new String[2];
                locationHarvest[0] = "Harvest";
                locationHarvest[1] = cropID;
                String key = dbCtrl.pushObjectReturnKey(locationHarvest, newHarvest);
                if (finished.isChecked() == false) {



                }
                else if (finished.isChecked()) {
                    String[] locationCropHist = new String[3];
                    locationCropHist[0] = "Crop History";
                    locationCropHist[1] = cropName;
                    locationCropHist[2] = cropID;
                    Entry entryHistory = new Entry(cropName, cropDate, cropNotes);
                    dbCtrl.setValueAtLocation(locationCropHist, entryHistory);

                    String[] locationOldCrop = new String[3];
                    locationOldCrop[0] = secS;
                    locationOldCrop[1] = bedS;
                    locationOldCrop[2] = cropID;
                    dbCtrl.removeValueFromLocation(locationOldCrop);
                }
                //startActivity(intent);
                finish();
            }
        });

        // Navigate back to bed page - no changes will be made
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
