package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.kaylaflaten.organicfarm.DatabaseCtrl;

import com.firebase.client.Firebase;

/**
 * Created by Carmen on 2/29/2016.
 */
public class CropHarvester extends AppCompatActivity {

    TextView section;
    TextView bed;
    TextView cropName;
    TextView harvestNotes;
    String date;
    boolean done;
    EditText notesInput;
    DatePicker datePicker;
    Switch finished;
    Button back;
    Button enter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvester);
        Firebase.setAndroidContext(this);

        enter = (Button) findViewById(R.id.buttonEnter);
        back = (Button) findViewById(R.id.buttonBack);


        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl("Harvest", this);

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

        // If we selected a crop from the list,
        // we will have passed its ID, so we set our reference to that ID
        String cropID = extras.getString("itemSelected");
        dbCtrl.setEntryRef(cropID,1);



        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        // Navigate back to bed page - no changes will be made
        final int finalSec = sec;
        final int finalBedN = bedN;
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropHarvester.this, CropsInBed.class);
                intent.putExtra("section", finalSec);
                intent.putExtra("bed", finalBedN);
                startActivity(intent);
            }
        });

    }
}
