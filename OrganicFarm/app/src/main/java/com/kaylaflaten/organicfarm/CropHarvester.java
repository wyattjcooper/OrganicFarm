package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carmen on 2/29/2016.
 */
public class CropHarvester extends AppCompatActivity {


    EditText notes;
    EditText date;
    EditText amount;
    CheckBox finished;
    Button change;
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

//    Calendar myCalendar = Calendar.getInstance();
//
//    DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            // TODO Auto-generated method stub
//            myCalendar.set(Calendar.YEAR, year);
//            myCalendar.set(Calendar.MONTH, monthOfYear);
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateLabel();
//        }
//
//        private void updateLabel() {
//
//            String myFormat = "MM/dd/yy"; //In which you need put here
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//            date.setText(sdf.format(myCalendar.getTime()));
//        }
//
//
//    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvester);
        Firebase.setAndroidContext(this);

        enter = (Button) findViewById(R.id.enter);
        back = (Button) findViewById(R.id.back);
        date = (EditText) findViewById(R.id.date);
        change = (Button) findViewById(R.id.change);
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




//        change.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(CropHarvester.this, PicDate.class);
////                startActivity(intent);
//                DatePicker datePic = new DatePicker(this);
//            }
//        });



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


                } else if (finished.isChecked()) {
                    String[] locationCropHist = new String[3];
                    locationCropHist[0] = "Crop History";
                    locationCropHist[1] = cropName;
                    locationCropHist[2] = cropID;
                    Entry entryHistory = new Entry(cropName, cropDate, cropNotes,0.0);
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


        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                final int mYear=mcurrentDate.get(Calendar.YEAR);
                final int mMonth=mcurrentDate.get(Calendar.MONTH);
                final int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(CropHarvester.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        date.setText(mMonth + "/" + mDay + "/" + mYear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });

//        date.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(CropHarvester.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });


    }



    }

