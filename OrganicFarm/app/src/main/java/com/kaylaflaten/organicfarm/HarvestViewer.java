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
 * Created by Carmen on 2/29/2016.
 */
public class HarvestViewer extends AppCompatActivity {

    //TextView name;
    TextView notes;
    TextView date;
    TextView owner;
    TextView amount;
    TextView pid;
    TextView finished;
    Button edit;


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

//    private SimpleDateFormat dateFormatter;
//
//    private DatePickerDialog datePicker;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_viewer);
        Firebase.setAndroidContext(this);



        //dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


        //setDateTimeField();

        //name = (TextView) findViewById(R.id.nameHarvestViewer);
        edit = (Button) findViewById(R.id.editHarvestViewer);
        date = (TextView) findViewById(R.id.dateHarvestViewer);
        owner = (TextView) findViewById(R.id.ownerHarvestViewer);
        notes = (TextView) findViewById(R.id.notesHarvestViewer);
        amount = (TextView) findViewById(R.id.amountHarvestViewer);
        pid = (TextView) findViewById(R.id.pidPlaceHolderHarvestViewer);


        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        final Bundle extras = getIntent().getExtras();

        String harvestID = "";
        String cropID = "";
        String cropName = "";
        int secN;
        int bedN;
        secN = 0;
        bedN = 0;
        cropName = "";
        if (extras != null) {
            harvestID = extras.getString("harvestID");
            cropID = extras.getString("cropID");
            cropName = extras.getString("cropName");
            secN = extras.getInt("secN", 1);
            bedN = extras.getInt("bedN", 1);
        }

        getSupportActionBar().setTitle(cropName + " Harvest");


        final String[] locationHarvest = new String[2];
        locationHarvest[0] = "Harvest";
        locationHarvest[1] = harvestID;

        dbCtrl.listenAndSetText(locationHarvest, date, "date", "Name");
        dbCtrl.listenAndSetText(locationHarvest, notes, "notes", "Notes");
        dbCtrl.listenAndSetText(locationHarvest, amount, "amount", "Amount");
        dbCtrl.listenAndSetText(locationHarvest, owner, "owner", "Owner");
        dbCtrl.listenAndSetText(locationHarvest, pid, "pid", "PID");
        //dbCtrl.listenAndSetText(locationHarvest, finished, "finished", "Finished");


        final String finalHarvestID = harvestID;
        final String finalCropName = cropName;
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HarvestViewer.this, HarvestEditor.class);
                // Look up the key in the keys list - same position
                intent.putExtra("cropName", finalCropName);
                intent.putExtra("harvestID", finalHarvestID);
                intent.putExtra("cropID",pid.getText().toString() );
                startActivityForResult(intent, 1);
            }
        });

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        //final String cropID = extras.getString("itemSelected");
//        enter.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent intent = new Intent(CropHarvester.this, MainActivity.class);
//                Harvest newHarvest = new Harvest(cropName, cropID, date.getText().toString(),dbCtrl.getUID(), Double.parseDouble(amount.getText().toString()), notes.getText().toString(), secN + 1, bedN + 1);
//                String[] locationHarvest = new String[1];
//                locationHarvest[0] = "Harvest";
//                int resultCode = 0;
//
//                //locationHarvest[1] = cropID;
//                String key = dbCtrl.pushObjectReturnKey(locationHarvest, newHarvest);
//                if (finished.isChecked()) {
//                    resultCode = 3;
//                    String[] locationCropOverall = new String[2];
//                    locationCropOverall[0] = "All Crops";
//                    locationCropOverall[1] = cropID;
//                    Entry entryHistory = new Entry(cropName, cropDate, date.getText().toString(),cropNotes,owner.getText().toString(), true, secN + 1, bedN + 1);
//                    dbCtrl.setValueAtLocation(locationCropOverall, entryHistory);
//
//
//                    String[] locationCropHist = new String[3];
//                    locationCropHist[0] = "Crop History";
//                    locationCropHist[1] = cropName;
//                    locationCropHist[2] = cropID;
//                    dbCtrl.setValueAtLocation(locationCropHist, entryHistory);
//
//                    String[] locationOldCrop = new String[3];
//                    locationOldCrop[0] = secS;
//                    locationOldCrop[1] = bedS;
//                    locationOldCrop[2] = cropID;
//                    dbCtrl.removeValueFromLocation(locationOldCrop);
//                }
//                String[] allActivitiesLocation = new String[2];
//                allActivitiesLocation[0] = "All Activities";
//                allActivitiesLocation[1] = key;
//                dbCtrl.setValueAtLocation(allActivitiesLocation, newHarvest);
//                Intent intent = new Intent();
//                setResult(resultCode, intent);
//                finish();
//                finish();
//            }
//        });


//        date.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //date.setEnabled(false);
//                datePicker.show();
//
//            }
//        });

//        if (!dbCtrl.getUID().equals("no id")) {
//            dbCtrl.listenAndSetToUsername(owner);
//        }

    }

//    private void setDateTimeField() {
//        Calendar newCalendar = Calendar.getInstance();
//        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                date.setText(dateFormatter.format(newDate.getTime()));
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//    }

}
