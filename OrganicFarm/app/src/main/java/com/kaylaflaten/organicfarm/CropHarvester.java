package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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

    String cropName = "";
    String cropNotes = "";
    String cropDate = "";
    String cropID = "";

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvester);
        Firebase.setAndroidContext(this);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


        setDateTimeField();

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
            cropID = extras.getString("itemSelected");
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }


        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        //final String cropID = extras.getString("itemSelected");
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CropHarvester.this, MainActivity.class);
                Harvest newHarvest = new Harvest(cropName, cropID, date.getText().toString(), Double.parseDouble(amount.getText().toString()), notes.getText().toString(), secN + 1, bedN + 1);
                String[] locationHarvest = new String[1];
                locationHarvest[0] = "Harvest";
                //locationHarvest[1] = cropID;
                String key = dbCtrl.pushObjectReturnKey(locationHarvest, newHarvest);
                if (finished.isChecked() == false) {


                } else if (finished.isChecked()) {
                    String[] locationCropOverall = new String[2];
                    locationCropOverall[0] = "All Crops";
                    locationCropOverall[1] = cropID;
                    Entry entryHistory = new Entry(cropName, cropDate, cropNotes, true, secN + 1, bedN + 1);
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


        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //date.setEnabled(false);
                datePicker.show();

            }
        });


//        date.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//                // TODO Auto-generated method stub
//                //To show current date in the datepicker
//                final Calendar mcurrentDate = Calendar.getInstance();
//
//
//                DatePickerDialog mDatePicker = new DatePickerDialog(CropHarvester.this, new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                    /*      Your code   to get date and time    */
//                        //harvestDate = mMonth + "/" + mDay + "/" + mYear;
//                        Calendar newDate = Calendar.getInstance();
//                        newDate.set(year, monthOfYear, dayOfMonth);
//                        date.setText(dateFormatter.format(newDate.getTime()));
//                    },mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
//
//                }
//            }
//        }
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "CropHarvester Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.kaylaflaten.organicfarm/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "CropHarvester Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.kaylaflaten.organicfarm/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }


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

