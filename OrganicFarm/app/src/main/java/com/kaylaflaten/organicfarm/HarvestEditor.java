package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HarvestEditor extends AppCompatActivity {



    TextView name;
    TextView date;
    EditText notes;
    EditText amount;
    TextView owner;
    Button enter;
    Button delete;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker;


    String harvestID;
    String cropID;
    String cropName;
    int secN;
    int bedN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        date = (TextView) findViewById(R.id.dateHarvestEditor);
        notes = (EditText) findViewById(R.id.notesHarvestEditor);
        owner = (TextView) findViewById(R.id.ownerHarvestEditor);
        amount = (EditText) findViewById(R.id.amountHarvestEditor);
        delete = (Button) findViewById(R.id.deleteHarvestEditor);
        enter = (Button) findViewById(R.id.enterHarvestEditor);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        final Bundle extras = getIntent().getExtras();
        secN = 0;
        bedN = 0;
        cropName = "";
        String pid = "";
        if (extras != null) {
            harvestID = extras.getString("harvestID");
            cropID = extras.getString("cropID");
            cropName = extras.getString("cropName");
            secN = extras.getInt("secN", 1);
            bedN = extras.getInt("bedN", 1);
            pid = extras.getString("pid");
        }

        final String[] locationHarvest = new String[2];
        locationHarvest[0] = "Harvest";
        locationHarvest[1] = harvestID;

        final String[] locationAllActivities = new String[2];
        locationAllActivities[0] = "All Activities";
        locationAllActivities[1] = harvestID;


        dbCtrl.listenAndSetText(locationHarvest, date,"date", "NULL" );
        dbCtrl.listenAndSetEditText(locationHarvest, amount, "amount", "NULL");
        dbCtrl.listenAndSetText(locationHarvest, owner, "owner", "NULL");
        dbCtrl.listenAndSetText(locationHarvest, notes, "notes", "NULL");

        getSupportActionBar().setTitle(cropName + " Harvest");


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date.setEnabled(false);
                datePicker.show();

            }
        });



        // Push new data or modify old data when pressing enter button
        final String finalPid = pid;
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Harvest newHarvest = new Harvest(cropName, cropID, date.getText().toString(), owner.getText().toString(),
                                                Double.parseDouble(amount.getText().toString()), notes.getText().toString(),
                                                secN, bedN);
                dbCtrl.setValueAtLocation(locationHarvest, newHarvest);
                dbCtrl.setValueAtLocation(locationAllActivities, newHarvest);

                finish();
            }
        });




        // Delete harvests
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                dbCtrl.removeValueFromLocation(locationHarvest);
                dbCtrl.removeValueFromLocation(locationAllActivities);
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


    }



