package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    TextView crop;
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

        crop = (TextView) findViewById(R.id.crop);
        edit = (Button) findViewById(R.id.edit);
        date = (TextView) findViewById(R.id.date);
        owner = (TextView) findViewById(R.id.harvestedBy);
        notes = (TextView) findViewById(R.id.notes);
        amount = (TextView) findViewById(R.id.amount);
        //pid = (TextView) findViewById(R.id.pidPlaceHolderHarvestViewer);


        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        final Bundle extras = getIntent().getExtras();

        String harvestID = "";

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

        crop.setText(cropName);

        dbCtrl.listenAndSetText(locationHarvest, date, "date", "Name");
        dbCtrl.listenAndSetText(locationHarvest, notes, "notes", "Notes");
        dbCtrl.listenAndSetText(locationHarvest, amount, "amount", "Amount");
        dbCtrl.listenAndSetText(locationHarvest, owner, "owner", "Owner");
        //dbCtrl.listenAndSetText(locationHarvest, pid, "pid", "PID");
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
                intent.putExtra("cropID",cropID );
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public void onRestart(){
        super.onRestart();

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

    //checks to see if delete was just called.  If so, finish.
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == 2 || resultCode == 3){
            finish();
        }
    }
}
