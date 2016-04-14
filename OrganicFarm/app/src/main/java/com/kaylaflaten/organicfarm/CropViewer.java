package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Carmen on 3/7/2016.
 */
public class CropViewer extends AppCompatActivity {

    TextView section;
    TextView bed;
    TextView name;
    TextView date;
    TextView notes;
    TextView amount;
    Button back;
    Button edit;
    Button harvest;
    Button history;

    int secN;
    int bedN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_viewer);
        //final Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarCropViewer);



        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (TextView) findViewById(R.id.crop);
        date = (TextView) findViewById(R.id.date);
        notes = (TextView) findViewById(R.id.notes);
        amount = (TextView) findViewById(R.id.amount);
        edit = (Button) findViewById(R.id.edit);
        harvest = (Button) findViewById(R.id.harvest);
        history = (Button) findViewById(R.id.HHbutton);

        final Bundle extras = getIntent().getExtras();

        String secS = "Section ";
        String bedS = "Bed ";
        String cropName = "";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            cropName = extras.getString("cropName");
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }
        section.setText(secS);
        bed.setText(bedS);
        //setSupportActionBar(toolbar);




        String[] location = new String[3];
        location[0] = secS;
        location[1] = bedS;

        final String cropID = extras.getString("itemSelected");

        location[2] = cropID;

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        dbCtrl.listenAndSetText(location,name, "name", "Name");
        dbCtrl.listenAndSetText(location, date, "date", "Date");
        dbCtrl.listenAndSetText(location, notes, "notes", "Notes");


        //toolbar.setTitle(secS);

//        String[] locationHarvest = new String[1];
//        locationHarvest[0] = "Harvest";

        dbCtrl.listenAndSetTextToAmountOfSpecificCropHarvested(amount, cropID);
        //name.setText(cropID);


        // Edit crops
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropEditor.class);
                // Look up the key in the keys list - same position
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);
            }
        });

        // Harvest the crop
        final String finalCropName = cropName;
        final String finalSecS = secS;
        final String finalBedS = bedS;
        harvest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropHarvester.class);
                // Look up the key in the keys list - same position
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("itemSelected", cropID);
                intent.putExtra("cropName", name.getText().toString());
                intent.putExtra("cropDate", date.getText().toString());
                intent.putExtra("cropNotes", notes.getText().toString());
                startActivity(intent);
            }
        });

        // View harvest history
        history.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, HarvestHistory.class);
                // Look up the key in the keys list - same position
                intent.putExtra("cropID", cropID);
                intent.putExtra("cropData", name.getText().toString() + " Planted In " + finalSecS +"," + finalBedS);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onStart(){
        super.onStart();
        System.out.print("here");
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