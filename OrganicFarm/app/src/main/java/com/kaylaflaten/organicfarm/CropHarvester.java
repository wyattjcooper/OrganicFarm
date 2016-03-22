package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kaylaflaten.organicfarm.DatabaseCtrl;

import com.firebase.client.Firebase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carmen on 2/29/2016.
 */
public class CropHarvester extends AppCompatActivity {


    EditText notes;
    TextView textViewUserDate;
    EditText amount;
    CheckBox finished;
    Button change;
    Button back;
    Button enter;

    DatePicker datePic;

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
        textViewUserDate = (TextView) findViewById(R.id.date);
       // change = (Button) findViewById(R.id.change);
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

        datePic = new DatePicker(this);

        // hide the whole calendar view (works in api 11 or greater)
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 11) {
            datePic.setCalendarViewShown(false);
        }

        // initialize the date to current date
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String dateStr = sdfDateTime.format(new Date(System.currentTimeMillis()));

        String[] dateSplit = dateStr.split("-");
        int currentMonth = Integer.parseInt(dateSplit[0]);
        int currentDay = Integer.parseInt(dateSplit[1]);
        int currentYear = Integer.parseInt(dateSplit[2]);




//        change.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        final String cropID = extras.getString("itemSelected");
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CropHarvester.this, MainActivity.class);
                Harvest newHarvest = new Harvest(textViewUserDate.getText().toString(), Double.parseDouble(amount.getText().toString()), finished.isChecked(), notes.getText().toString(), 1, 1);
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



        // hide the whole calendar view (works in api 11 or greater)
        if (currentapiVersion >= 11) {
            datePic.setCalendarViewShown(false);
        }

        // create the TextView
//        textViewUserDate = new TextView(this);
//        textViewUserDate.setGravity(Gravity.CENTER);

        // initialize the date to current date
//        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        String dateStr = sdfDateTime.format(new Date(System.currentTimeMillis()));

//        String[] dateSplit = dateStr.split("-");
//        int currentYear = Integer.parseInt(dateSplit[0]);
//        int currentMonth = Integer.parseInt(dateSplit[1]);
//        int currentDay = Integer.parseInt(dateSplit[2]);

        // to show date and day of week in the TextView
        setHumanReadableDate(currentYear, currentMonth, currentDay);

        // initialize date picker listener
        // currentMonth - 1, because on the picker, 0 is January
        datePic.init(currentYear, currentMonth - 1, currentDay, birthdayListener);

        // add to the container
        //LinearLayout linearLayoutCalTvContainer = new LinearLayout(this);
      //  linearLayoutCalTvContainer.setOrientation(LinearLayout.VERTICAL);
       // linearLayoutCalTvContainer.addView(datePic);
//        linearLayoutCalTvContainer.addView(textViewUserDate);

        // set the views for the activity
        //setContentView(linearLayoutCalTvContainer);

    }

    // the date picker listener
    DatePicker.OnDateChangedListener birthdayListener = new DatePicker.OnDateChangedListener() {

        @Override
        public void onDateChanged(DatePicker birthDayDatePicker,
                                  int newYear, int newMonth, int newDay) {

            try{

                // currentMonth + 1, to retrieve proper month
                setHumanReadableDate(newYear, newMonth + 1, newDay);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // show the user a better date format
    public void setHumanReadableDate(int newYear, int newMonth, int newDay){
        try {

            String clickedDate = newYear + "-" + newMonth + "-" + newDay;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(clickedDate);

            SimpleDateFormat sdfDateTime = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String dateStr = sdfDateTime.format(d);

            textViewUserDate.setText(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    }

