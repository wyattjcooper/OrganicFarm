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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_viewer);

        section = (TextView) findViewById(R.id.textView);
        bed = (TextView) findViewById(R.id.textView2);
        bed = (TextView) findViewById(R.id.textView2);
        name = (TextView) findViewById(R.id.editText3);
        date = (TextView) findViewById(R.id.editText);
        notes = (TextView) findViewById(R.id.editText2);
        back = (Button) findViewById(R.id.button2);
        edit = (Button) findViewById(R.id.button5);

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
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(section.getText().toString(), bed.getText().toString(), this);

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        final String cropID = extras.getString("itemSelected");
        dbCtrl.setEntryRef(cropID, 2);

        name.setText("cat");
        date.setText("Cat");
        notes.setText("catt");
//        dbCtrl.listenAndSetText(name, "name", "Enter name here");
//        dbCtrl.listenAndSetEditText(date, "date", "Enter date here");
//        dbCtrl.listenAndSetEditText(notes, "notes", "Enter notes here");



        // Navigate back to bed page - no changes will be made
        final int finalSec = sec;
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
        final int finalSec1 = sec;
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
    }
}