package com.kaylaflaten.organicfarm;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Allows user to edit a crop already harvested in history
 */
public class CropHistoryEditor extends AppCompatActivity {

    TextView section;
    TextView bed;
    TextView harvestDate;
    EditText name;
    TextView date;
    TextView owner;
    TextView amount;
    EditText notes;
    Button enter;
    Button delete;

    int secN;
    int bedN;
    String secS;
    String bedS;
    String cropName;
    String cropID;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker1;
    private DatePickerDialog datePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_history_editor);

        name = (EditText) findViewById(R.id.crop);
        date = (TextView) findViewById(R.id.date);
        harvestDate = (TextView) findViewById(R.id.harvestDate);
        owner = (TextView) findViewById(R.id.plantedBy);
        notes = (EditText) findViewById(R.id.notes);
        owner = (EditText) findViewById(R.id.plantedBy);
        enter = (Button) findViewById(R.id.enter);
        delete = (Button) findViewById(R.id.delete);
        amount = (TextView) findViewById(R.id.amount);

        //used for date picker
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        setDateTimeField();

        final Bundle extras = getIntent().getExtras();

        secS = "Section ";
        bedS = "Bed ";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            cropName = extras.getString("cropName");
            cropID = extras.getString("cropID");
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }

        final String[] location = new String[3];
        location[0] = "Crop History";
        location[1] = cropName;
        location[2] = cropID;

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);


        dbCtrl.listenAndSetEditText(location, name, "name", "Enter name here");
        dbCtrl.listenAndSetText(location, date, "date", "Date");
        dbCtrl.listenAndSetEditText(location, notes, "notes", "Enter notes here");
        dbCtrl.listenAndSetText(location, owner, "owner", "Owner");
        dbCtrl.listenAndSetText(location, harvestDate, "harvestDate", "Date");
        dbCtrl.listenAndSetTextToAmountOfSpecificCropHarvested(amount, cropID);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker1.show();

            }
        });

        harvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker2.show();
            }
        });

        // Push new data or modify old data when pressing enter button
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), harvestDate.getText().toString(), notes.getText().toString(),owner.getText().toString(), false, secN + 1, bedN + 1);
                String[] locationCropHistory = new String[3];
                locationCropHistory[0] = "Crop History";
                locationCropHistory[1] = name.getText().toString();
                locationCropHistory[2] = cropID;
                dbCtrl.setValueAtLocation(locationCropHistory, newEntry);

                String[] allActivitiesLocation = new String[2];
                allActivitiesLocation[0] = "All Activities";
                allActivitiesLocation[1] = cropID;
                dbCtrl.setValueAtLocation(allActivitiesLocation, newEntry);

                finish();
            }
        });


        // Delete crops
        final String finalCropID1 = cropID;
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                // remove all harvest data associated with crop instance
                String[] locationHarvests = new String[2];
                locationHarvests[0] = "Harvest";

                ArrayList<String> harvestKeys = dbCtrl.getHarvestsOfCropID(finalCropID1);
                    for (String key : harvestKeys) {
                        Toast.makeText(getApplicationContext(), "Back button clicked", Toast.LENGTH_SHORT).show();

                        location[1] = key;
                        dbCtrl.removeValueFromLocation(locationHarvests);
                    }

                    String[] locationCropHistory = new String[3];
                    locationCropHistory[0] = "Crop History";
                    locationCropHistory[1] = name.getText().toString();
                    locationCropHistory[2] = finalCropID1;
                    dbCtrl.removeValueFromLocation(locationCropHistory);

                    String[] allActivitiesLocation = new String[2];
                    allActivitiesLocation[0] = "All Crops";
                    allActivitiesLocation[1] = finalCropID1;
                    dbCtrl.removeValueFromLocation(allActivitiesLocation);
                    allActivitiesLocation[0] = "All Activities";
                    dbCtrl.removeValueFromLocation(allActivitiesLocation);


                Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                }

        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePicker1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                harvestDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
