package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Carmen on 3/7/2016.
 */
public class CropViewer extends AppCompatActivity {

    TextView section;
    TextView bed;
    TextView name;
    TextView date;
    TextView notes;
    Button back;
    Button edit;
    Button harvest;

    int secN = -1;
    int bedN = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_viewer);

        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (TextView) findViewById(R.id.crop);
        date = (TextView) findViewById(R.id.date);
        notes = (TextView) findViewById(R.id.notes);
        back = (Button) findViewById(R.id.back);
        edit = (Button) findViewById(R.id.edit);
        harvest = (Button) findViewById(R.id.button);

        final Bundle extras = getIntent().getExtras();

        String secS = "Section ";
        String bedS = "Bed ";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }
        section.setText(secS);
        bed.setText(bedS);

        final String cropID = extras.getString("itemSelected");

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(section.getText().toString(), bed.getText().toString(), this);

        Entry crop = dbCtrl.returnEntryAtLocation(secS, bedS, cropID);

        //name.setText(crop.getName());
        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        dbCtrl.setEntryRef(cropID, 2);

//        Entry crop  = dbCtrl.returnEntryAtLocation(sectionNum, bedNum, cropID);
//        name.setText(crop.getName());
//        date.setText(crop.getDate());
//        notes.setText(crop.getNotes());
//        dbCtrl.listenAndSetText(name, "name", "Enter name here");
//        dbCtrl.listenAndSetEditText(date, "date", "Enter date here");
//        dbCtrl.listenAndSetEditText(notes, "notes", "Enter notes here");



        // Navigate back to bed page - no changes will be made
        final int finalSec = secN;
        final int finalBedN = bedN;
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropsInBed.class);
                intent.putExtra("section", finalSec);
                intent.putExtra("bed", finalBedN);
                startActivity(intent);
            }
        });

        // Edit crops
        //FIX
        final int finalSec1 = secN;
        final int finalBedN1 = bedN;
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropEditor.class);
                // Look up the key in the keys list - same position
                intent.putExtra("section", finalSec1);
                intent.putExtra("bed", finalBedN1);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);
            }
        });

        // Harvest the crop
        harvest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropHarvester.class);
                // Look up the key in the keys list - same position
                intent.putExtra("section", finalSec1);
                intent.putExtra("bed", finalBedN1);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);
            }
        });
    }
}