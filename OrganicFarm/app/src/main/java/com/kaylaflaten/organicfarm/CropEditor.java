package com.kaylaflaten.organicfarm;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Carmen on 3/7/2016.
 */
public class CropEditor extends AppCompatActivity {

    TextView section;
    TextView bed;
    EditText name;
    TextView date;
    TextView harvestDate;
    EditText notes;
    Button back;
    Button enter;
    Button delete;

    int secN;
    int bedN;

    String secS;
    String bedS;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker1;
    private DatePickerDialog datePicker2;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_editor);



        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (EditText) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.dateByName);
        harvestDate = (TextView) findViewById(R.id.harvestDateByName);
        notes = (EditText) findViewById(R.id.notesHarvestViewer);
        enter = (Button) findViewById(R.id.enter);
        delete = (Button) findViewById(R.id.delete);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        final Bundle extras = getIntent().getExtras();

        secS = "Section ";
        bedS = "Bed ";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }
        section.setText(secS);
        bed.setText(bedS);

        final String[] location = new String[3];
        location[0] = secS;
        location[1] = bedS;

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        final String cropID = extras.getString("itemSelected");
        location[2] = cropID;

        dbCtrl.listenAndSetEditText(location, name, "name", "Enter name here");
        dbCtrl.listenAndSetText(location, date, "date", "Date");
        dbCtrl.listenAndSetEditText(location, notes, "notes", "Enter notes here");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date.setEnabled(false);
                datePicker1.show();

            }
        });

        harvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date.setEnabled(false);
                datePicker2.show();

            }
        });




        // Push new data or modify old data when pressing enter button
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Intent intent = new Intent(CropEditor.this, CropViewer.class);
           // Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString(),dbCtrl.getUID(), false, secN + 1, bedN + 1);
            // If we are adding a new crop, push a new child
                //Intent intent = new Intent(CropEditor.this, CropViewer.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), harvestDate.getText().toString(), notes.getText().toString(),dbCtrl.getUID(), false, secN + 1, bedN + 1);
                // If we are adding a new crop, push a new child

            String[] location = new String[3];
            location[0] = secS;
            location[1] = bedS;
            location[2] = cropID;
            // If we are not adding a new crop, modify the existing child we clicked on
            dbCtrl.setValueAtLocation(location, newEntry);


            String[] locationCropOverall = new String[2];
            locationCropOverall[0] = "All Crops";
            locationCropOverall[1] = cropID;
            dbCtrl.setValueAtLocation(locationCropOverall, newEntry);


            String[] allActivitiesLocation = new String[2];
            allActivitiesLocation[0] = "All Activities";
            allActivitiesLocation[1] = cropID;
            dbCtrl.setValueAtLocation(allActivitiesLocation, newEntry);


            finish();
            }
        });


        // Delete crops
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CropEditor.this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (extras.getBoolean("new") == true) {
                                // If we are adding a new crop, there is nothing to delete so do nothing
                            }
                            // If we are not adding a new crop, delete the existing child we clicked on and return to bed view
                            else if (extras.getBoolean("new") != true) {
                                dbCtrl.removeValueFromLocation(location);

                                String[] harvestLocation = new String[2];
                                harvestLocation[0] = "Harvest";
                                harvestLocation[1] = cropID;
                                dbCtrl.removeValueFromLocation(harvestLocation);

                                String[] cropsOverallLocation = new String[2];
                                cropsOverallLocation[0] = "All Crops";
                                cropsOverallLocation[1] = cropID;
                                dbCtrl.removeValueFromLocation(cropsOverallLocation);
                                dbCtrl.deleteHarvests(cropID);

                                String[] allActivitiesLocation = new String[2];
                                allActivitiesLocation[0] = "All Activities";
                                allActivitiesLocation[1] = cropID;
                                dbCtrl.removeValueFromLocation(allActivitiesLocation);

                                Intent intent = new Intent();
                                setResult(2, intent);
                                finish();
                            }
                        }
                    })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
                //Toast.makeText(getApplicationContext(), "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

}
