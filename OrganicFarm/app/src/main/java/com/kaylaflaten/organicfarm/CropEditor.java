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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_editor);

        section = (TextView) findViewById(R.id.textView);
        bed = (TextView) findViewById(R.id.textView2);
        bed = (TextView) findViewById(R.id.textView2);
        name = (EditText) findViewById(R.id.editText3);
        date = (EditText) findViewById(R.id.editText);
        notes = (EditText) findViewById(R.id.editText2);
        enter = (Button) findViewById(R.id.button);
        back = (Button) findViewById(R.id.button2);
        delete = (Button) findViewById(R.id.button5);

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

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        String cropID = extras.getString("itemSelected");
        dbCtrl.setEntryRef(cropID, 2);

        dbCtrl.listenAndSetEditText(name, "name", "Enter name here");
        dbCtrl.listenAndSetEditText(date,"date", "Enter date here");
        dbCtrl.listenAndSetEditText(notes,"notes", "Enter notes here");

        // Push new data or modify old data when pressing enter button
        final int finalSec2 = sec;
        final int finalBedN2 = bedN;
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropEditor.this, CropsInBed.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString());
                // If we are adding a new crop, push a new child
                if (extras.getBoolean("new") == true) {
                    String key = dbCtrl.pushEntryReturnKey(newEntry);
                    intent.putExtra("pushID", key);
                }
                // If we are not adding a new crop, modify the existing child we clicked on
                else if (extras.getBoolean("new") != true) {
                    dbCtrl.setValueEntry(newEntry);
                }
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
                Intent intent = new Intent(CropEditor.this, CropsInBed.class);
                intent.putExtra("section", finalSec);
                intent.putExtra("bed", finalBedN);
                startActivity(intent);
            }
        });

        // Delete crops
        final int finalSec1 = sec;
        final int finalBedN1 = bedN;
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras.getBoolean("new") == true) {
                    // If we are adding a new crop, there is nothing to delete so do nothing
                }
                // If we are not adding a new crop, delete the existing child we clicked on and return to bed view
                else if (extras.getBoolean("new") != true) {
                    dbCtrl.removeValueEntry();
                    Intent intent = new Intent(CropEditor.this, CropsInBed.class);
                    intent.putExtra("section", finalSec1);
                    intent.putExtra("bed", finalBedN1);
                    startActivity(intent);
                }
            }
        });
    }
}