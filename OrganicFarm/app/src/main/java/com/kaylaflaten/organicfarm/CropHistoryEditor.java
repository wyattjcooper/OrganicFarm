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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Carmen on 3/7/2016.
 */
public class CropHistoryEditor extends AppCompatActivity {

    TextView section;
    TextView bed;
    EditText name;
    TextView date;
    EditText notes;
    Button enter;
    Button delete;

    int secN;
    int bedN;

    String secS;
    String bedS;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_history_editor);



        //section = (TextView) findViewById(R.id.sectionCropHistoryEditor);
        //bed = (TextView) findViewById(R.id.bedCropHistoryEditor);
        name = (EditText) findViewById(R.id.nameCropHistoryEditor);
        date = (TextView) findViewById(R.id.dateCropHistoryEditor);
        notes = (EditText) findViewById(R.id.notesCropHistoryEditor);
        enter = (Button) findViewById(R.id.enterCropHistoryEditor);
        delete = (Button) findViewById(R.id.deleteCropHistoryEditor);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        final Bundle extras = getIntent().getExtras();

        secS = "Section ";
        bedS = "Bed ";
        String cropName = "";
        String cropID = "";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            cropName = extras.getString("cropName");
            cropID = extras.getString("cropID");
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }
        //section.setText(secS);
        //bed.setText(bedS);

        final String[] location = new String[3];
        location[0] = "Crop History";
        location[1] = cropName;
        location[2] = cropID;

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);


        dbCtrl.listenAndSetEditText(location, name, "name", "Enter name here");
        dbCtrl.listenAndSetText(location, date, "date", "Date");
        dbCtrl.listenAndSetEditText(location, notes, "notes", "Enter notes here");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date.setEnabled(false);
                datePicker.show();

            }
        });


        // Push new data or modify old data when pressing enter button
        final String finalCropID = cropID;
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CropEditor.this, CropViewer.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString(),dbCtrl.getUID(), false, secN + 1, bedN + 1);
                // If we are adding a new crop, push a new child

//                String[] location = new String[3];
//                location[0] = secS;
//                location[1] = bedS;
//                location[2] = cropID;
//                // If we are not adding a new crop, modify the existing child we clicked on
//                dbCtrl.setValueAtLocation(location, newEntry);


                String[] locationCropHistory = new String[3];
                locationCropHistory[0] = "Crop History";
                locationCropHistory[1] = name.getText().toString();
                locationCropHistory[2] = finalCropID;
                dbCtrl.setValueAtLocation(locationCropHistory, newEntry);


                String[] allActivitiesLocation = new String[2];
                allActivitiesLocation[0] = "All Activities";
                allActivitiesLocation[1] = finalCropID;
                dbCtrl.setValueAtLocation(allActivitiesLocation, newEntry);


                finish();
            }
        });


        // Delete crops
        final String finalCropID1 = cropID;
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


                    dbCtrl.removeValueFromLocation(location);

                    String[] harvestLocation = new String[2];
                    harvestLocation[0] = "Harvest";
                    harvestLocation[1] = finalCropID1;
                    dbCtrl.removeValueFromLocation(harvestLocation);

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
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
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
