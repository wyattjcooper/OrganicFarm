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
public class CropEditor extends AppCompatActivity {

    TextView section;
    TextView bed;
    EditText name;
    EditText date;
    EditText notes;
    Button back;
    Button enter;
    Button delete;

    int secN;
    int bedN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_editor);

        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        notes = (EditText) findViewById(R.id.notes);
        enter = (Button) findViewById(R.id.enter);
        back = (Button) findViewById(R.id.back);
        delete = (Button) findViewById(R.id.delete);

        final Bundle extras = getIntent().getExtras();

        String sectionNum = "Section ";
        String bedNum = "Bed ";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            sectionNum = sectionNum + (secN + 1);
            bedNum = bedNum + (bedN + 1);
        }
        section.setText(sectionNum);
        bed.setText(bedNum);

        String[] location = new String[3];
        location[0] = sectionNum;
        location[1] = bedNum;



        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(section.getText().toString(),bed.getText().toString(), this);

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        final String cropID = extras.getString("itemSelected");
        location[2] = cropID;

        //dbCtrl.setEntryRef(cropID, 2);

        dbCtrl.listenAndSetEditText(location, name, "name", "Enter name here");
        dbCtrl.listenAndSetEditText(location, date,"date", "Enter date here");
        dbCtrl.listenAndSetEditText(location, notes,"notes", "Enter notes here");

        // Push new data or modify old data when pressing enter button
        final int finalSec2 = secN;
        final int finalBedN2 = bedN;
        final int finalSec3 = secN;
        final int[] finalBedN3 = {bedN};
        final String[] entryKey = {"blank"};
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropEditor.this, CropViewer.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString());
                // If we are adding a new crop, push a new child

                // If we are adding a new crop, push a new child and add it to Harvest data branch
                if (extras.getBoolean("new") == true) {
                    // Push the entry data
                    entryKey[0] = dbCtrl.pushEntryReturnKey(newEntry);
                    intent.putExtra("pushID", entryKey[0]);
                    // Initialize the harvest data
                    Harvest harvestDefault = new Harvest("Enter date here", 0.0, false,"Enter notes here", finalSec3 + 1, finalBedN3[0] + 1);
                    dbCtrl.setOneChildRef("Harvest");
                    dbCtrl.setEntryRef(entryKey[0], 1);
                    dbCtrl.setValueHarvest(harvestDefault);
                }
                // If we are not adding a new crop, modify the existing child we clicked on
                    dbCtrl.setValueEntry(newEntry);


                // Look up the key in the keys list - same position
                intent.putExtra("section", finalSec2);
                intent.putExtra("bed", finalBedN2);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);



                // Go back to crop entry page
                startActivity(intent);
            }
        });

        // Navigate back to bed page - no changes will be made

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropEditor.this, CropViewer.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);
            }
        });

        // Delete crops
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras.getBoolean("new") == true) {
                    // If we are adding a new crop, there is nothing to delete so do nothing
                }
                // If we are not adding a new crop, delete the existing child we clicked on and return to bed view
                else if (extras.getBoolean("new") != true) {
                    dbCtrl.removeValueEntry();
                    dbCtrl.setOneChildRef("Harvest");
                    dbCtrl.setEntryRef(cropID, 1);
                    dbCtrl.removeValueEntry();
                    Intent intent = new Intent(CropEditor.this, CropsInBed.class);
                    intent.putExtra("section", secN);
                    intent.putExtra("bed", bedN);
                    startActivity(intent);
                }
            }
        });
    }
}
