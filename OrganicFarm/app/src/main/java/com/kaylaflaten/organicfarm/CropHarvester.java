package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.CheckBox;
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
    EditText datePicker;
    EditText amount;
    CheckBox finished;
    Button back;
    Button enter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvester);
        Firebase.setAndroidContext(this);

        enter = (Button) findViewById(R.id.buttonEnter);
        back = (Button) findViewById(R.id.buttonBack);
        datePicker = (EditText) findViewById(R.id.editTextHD);
        notesInput = (EditText) findViewById(R.id.editTextHN);
        finished = (CheckBox) findViewById(R.id.checkboxFH);
        amount = (EditText) findViewById(R.id.editTextAH);


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
        final String cropID = extras.getString("itemSelected");
        //String cropID = "-KCSKgIhqPfeJtezBxP-";
        //dbCtrl.setEntryRef(cropID, 1);

        //Harvest harvestData = dbCtrl.returnHarvestAtLocation(cropID);

        //notesInput.setText(harvestData.getNotes().toString());
        //finished.setActivated(harvestData.getFinished());
        //datePicker.setText(harvestData.getDate().toString());
//        amount.setText(harvestData.getAmount().toString());
        //dbCtrl.listenAndSetEditText(notesInput, "notes", "NULL");
        //dbCtrl.listenAndSetEditText(datePicker, "date", "NULL");
        //dbCtrl.listenAndSetCheckBox(finished, "finished");
        //dbCtrl.listenAndSetEditText(amount, "amount", "NULL");



        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropHarvester.this, MainActivity.class);
                Harvest newHarvest = new Harvest(datePicker.getText().toString(), Double.parseDouble(amount.getText().toString()), finished.isActivated(), notesInput.getText().toString(), 1, 1);
                //dbCtrl.setValueHarvest(newHarvest);
                String[] locationHarvest = new String[2];
                locationHarvest[0] = "Harvest";
                locationHarvest[1] = cropID;
                String key = dbCtrl.pushObjectReturnKey(locationHarvest,newHarvest);
                startActivity(intent);
            }
        });

        // Navigate back to bed page - no changes will be made
        final int finalSec = sec;
        final int finalBedN = bedN;
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropHarvester.this, CropViewer.class);
                intent.putExtra("section", finalSec);
                intent.putExtra("bed", finalBedN);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);
            }
        });

    }
}
