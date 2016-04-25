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
 * Allows a user to view harvest information
 */
public class HarvestViewer extends AppCompatActivity {

    TextView notes;
    TextView date;
    TextView owner;
    TextView amount;
    TextView crop;
    Button edit;


    //section/bed ints
    int secN;
    int bedN;

    //section/bed strings
    String secS;
    String bedS;

    String cropName = "";
    String cropID = "";
    String harvestID = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_viewer);
        Firebase.setAndroidContext(this);

        crop = (TextView) findViewById(R.id.crop);
        edit = (Button) findViewById(R.id.edit);
        date = (TextView) findViewById(R.id.date);
        owner = (TextView) findViewById(R.id.harvestedBy);
        notes = (TextView) findViewById(R.id.notes);
        amount = (TextView) findViewById(R.id.amount);


        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            harvestID = extras.getString("harvestID");
            cropID = extras.getString("cropID");
            cropName = extras.getString("cropName");
            secN = extras.getInt("secN", 1);
            bedN = extras.getInt("bedN", 1);
        }

        // setting action bar title
        getSupportActionBar().setTitle(cropName + " Harvest");


        final String[] locationHarvest = new String[2];
        locationHarvest[0] = "Harvest";
        locationHarvest[1] = harvestID;

        // setting text view/editor text
        crop.setText(cropName);
        dbCtrl.listenAndSetText(locationHarvest, date, "date", "Name");
        dbCtrl.listenAndSetText(locationHarvest, notes, "notes", "Notes");
        dbCtrl.listenAndSetText(locationHarvest, amount, "amount", "Amount");
        dbCtrl.listenAndSetText(locationHarvest, owner, "owner", "Owner");

        // opens activity to edit harvest
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HarvestViewer.this, HarvestEditor.class);
                intent.putExtra("cropName", cropName);
                intent.putExtra("harvestID", harvestID);
                intent.putExtra("cropID",cropID );
                startActivityForResult(intent, 1);
            }
        });
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

    //checks to see if delete was just called.  If so, finish.
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == 2 || resultCode == 3){
            finish();
        }
    }
}
