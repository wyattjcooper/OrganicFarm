package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Allows user to harvest crops
 */
public class CropHarvester extends AppCompatActivity {

    TextView crop;
    EditText notes;
    TextView date;
    TextView owner;
    EditText amount;
    CheckBox finished;
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
    String cropID;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvester);
        Firebase.setAndroidContext(this);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        crop = (TextView) findViewById(R.id.crop);
        enter = (Button) findViewById(R.id.enter);
        date = (TextView) findViewById(R.id.dateByName);
        owner = (TextView) findViewById(R.id.ownerCropHarvester);
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
            cropID = extras.getString("itemSelected");
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }

        crop.setText(cropName);

        if (!dbCtrl.getUID().equals("no id")) {
            dbCtrl.listenAndSetToUsername(owner);
        }

        // save the harvest
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Harvest newHarvest = new Harvest(cropName, cropID, date.getText().toString(),owner.getText().toString(), Double.parseDouble(amount.getText().toString()), notes.getText().toString(), secN + 1, bedN + 1);
                String[] locationHarvest = new String[1];
                locationHarvest[0] = "Harvest";
                int resultCode = 0;

                String key = dbCtrl.pushObjectReturnKey(locationHarvest, newHarvest);
                if (finished.isChecked()) {
                    resultCode = 3;
                    String[] locationCropOverall = new String[2];
                    locationCropOverall[0] = "All Crops";
                    locationCropOverall[1] = cropID;
                    Entry entryHistory = new Entry(cropName, cropDate, date.getText().toString(),cropNotes,owner.getText().toString(), true, secN + 1, bedN + 1);
                    dbCtrl.setValueAtLocation(locationCropOverall, entryHistory);


                    String[] locationCropHist = new String[3];
                    locationCropHist[0] = "Crop History";
                    locationCropHist[1] = cropName;
                    locationCropHist[2] = cropID;
                    dbCtrl.setValueAtLocation(locationCropHist, entryHistory);

                    String[] locationOldCrop = new String[3];
                    locationOldCrop[0] = secS;
                    locationOldCrop[1] = bedS;
                    locationOldCrop[2] = cropID;
                    dbCtrl.removeValueFromLocation(locationOldCrop);
                }
                String[] allActivitiesLocation = new String[2];
                allActivitiesLocation[0] = "All Activities";
                allActivitiesLocation[1] = key;
                dbCtrl.setValueAtLocation(allActivitiesLocation, newHarvest);
                Intent intent = new Intent();
                setResult(resultCode, intent);
                finish();
                finish();
            }
        });

        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                datePicker.show();

            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

}

