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


    TextView section;
    TextView bed;
    TextView name;
    EditText date;
    EditText notes;
    EditText amount;
    TextView owner;
    Button enter;
    Button delete;

    TextView sectionTitle;
    TextView bedTitle;
    TextView dateTitle;
    TextView amountTitle;
    TextView notesTitle;


    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker;


    String harvestID;
    String cropID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        section = (TextView) findViewById(R.id.sectionHarvestEditor);
        bed = (TextView) findViewById(R.id.bedHarvestEditor);
        name = (TextView) findViewById(R.id.nameTextHarvestEditor);
        date = (EditText) findViewById(R.id.dateHarvestEditor);
        notes = (EditText) findViewById(R.id.notesHarvestEditor);
        owner = (TextView) findViewById(R.id.ownerHarvestEditor);
        amount = (EditText) findViewById(R.id.amountHarvestEditor);
        enter = (Button) findViewById(R.id.enterHarvestEditor);
        delete = (Button) findViewById(R.id.deleteHarvestEditor);

        sectionTitle = (TextView) findViewById(R.id.sectionDisplayTitleHarvestEditor);
        bedTitle = (TextView) findViewById(R.id.bedDisplayLabelHarvestEditor);
        dateTitle = (TextView) findViewById(R.id.dateLabelHarvestEditor);
        amountTitle = (TextView) findViewById(R.id.amountLabelHarvestEditor);
        notesTitle = (TextView) findViewById(R.id.notesLabelHarvestEditor);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            harvestID = extras.getString("harvestID");
            cropID = extras.getString("cropID");
        }

        final String[] locationHarvest = new String[2];
        locationHarvest[0] = "Harvest";
        locationHarvest[1] = harvestID;

        final String[] locationAllActivities = new String[2];
        locationAllActivities[0] = "All Activities";
        locationAllActivities[1] = harvestID;


        dbCtrl.listenAndSetText(locationHarvest, name,"name", "NULL" );
        dbCtrl.listenAndSetEditText(locationHarvest, date,"date", "NULL" );
        dbCtrl.listenAndSetEditText(locationHarvest, amount,"amount", "NULL" );
        dbCtrl.listenAndSetText(locationHarvest, section, "section", "NULL");
        dbCtrl.listenAndSetText(locationHarvest, bed, "bed", "NULL");
        dbCtrl.listenAndSetText(locationHarvest, owner, "owner", "NULL");






        // Push new data or modify old data when pressing enter button
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Harvest newHarvest = new Harvest(name.getText().toString(), cropID, date.getText().toString(), owner.getText().toString(),
                                                Double.parseDouble(amount.getText().toString()), notes.getText().toString(),
                                                Integer.parseInt(section.getText().toString()), Integer.parseInt(bed.getText().toString()) );
                dbCtrl.setValueAtLocation(locationHarvest, newHarvest);
                dbCtrl.setValueAtLocation(locationAllActivities, newHarvest);

                finish();
            }
        });




        // Delete harvests
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras.getBoolean("new") == true) {
                    // If we are adding a new crop, there is nothing to delete so do nothing
                }
                // If we are not adding a new crop, delete the existing child we clicked on and return to bed view
                else if (extras.getBoolean("new") != true) {
                    dbCtrl.removeValueFromLocation(locationHarvest);
                    dbCtrl.removeValueFromLocation(locationAllActivities);
                    finish();
                }
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



