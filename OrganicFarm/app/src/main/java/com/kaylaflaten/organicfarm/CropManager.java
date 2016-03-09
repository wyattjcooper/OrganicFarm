package com.kaylaflaten.organicfarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import com.kaylaflaten.organicfarm.Entry;
import com.kaylaflaten.organicfarm.DatabaseCtrl;

public class CropManager extends AppCompatActivity {

    TextView section;
    TextView bed;
    EditText name;
    EditText date;
    EditText notes;
    Button back;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_manager);

        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        notes = (EditText) findViewById(R.id.notes);
        enter = (Button) findViewById(R.id.enter);
        back = (Button) findViewById(R.id.back);

        final Bundle extras = getIntent().getExtras();

        String sectionNum = "Section ";
        String bedNum = "Bed ";
        int bedN = -1;
        int sec = -1;

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            sec = extras.getInt("section", -1);
            sectionNum = sectionNum + (sec + 1);
            bedNum = bedNum + (bedN + 1);
        }
        section.setText(sectionNum);
        bed.setText(bedNum);

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(section.getText().toString(),bed.getText().toString(), this);

        dbCtrl.setEntryRef(null, 2);


        // Push new data or modify old data when pressing enter button
        final int finalSec2 = sec;
        final int finalBedN2 = bedN;
        final int finalSec3 = sec;
        final int[] finalBedN3 = {bedN};
        final String[] entryKey = {"blank"};
                enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString());
                // If we are adding a new crop, push a new child and add it to Harvest data branch
//                if (extras.getBoolean("new") == true) {
                    // Push the entry data

                    entryKey[0] = dbCtrl.pushEntryReturnKey(newEntry);
                    intent.putExtra("pushID", entryKey[0]);
                    // Initialize the harvest data
                    Harvest harvestDefault = new Harvest("Enter date here", 0.0, false,"Enter notes here", finalSec3 + 1, finalBedN3[0] + 1);
                    dbCtrl.setOneChildRef("Harvest");
                    dbCtrl.setEntryRef(entryKey[0], 1);
                    dbCtrl.setValueHarvest(harvestDefault);

//                }
//                // If we are not adding a new crop, modify the existing child we clicked on
//                else if (extras.getBoolean("new") != true) {
//                    dbCtrl.setValueEntry(newEntry);
//                }
                intent.putExtra("section", finalSec2);
                intent.putExtra("bed", finalBedN2);
                // Go back to crop entry page
                startActivity(intent);
            }
        });

        // Navigate back to bed page - no changes will be made
        final int finalSec = sec;
        final int finalBedN = bedN;
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                intent.putExtra("section", finalSec);
                intent.putExtra("bed", finalBedN);
                startActivity(intent);
            }
        });
    }
}
