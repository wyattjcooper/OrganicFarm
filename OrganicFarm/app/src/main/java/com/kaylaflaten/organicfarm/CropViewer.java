package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    int secN;
    int bedN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_viewer);

        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (TextView) findViewById(R.id.crop);
        date = (TextView) findViewById(R.id.date);
        notes = (TextView) findViewById(R.id.notes);
        amount = (TextView) findViewById(R.id.NDtextView2);
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

        String[] locationHarvest = new String[1];
        locationHarvest[0] = "Harvest";

        dbCtrl.listenAndSetTextToAmountHarvested(locationHarvest,amount,cropID,"0" );

        // Navigate back to bed page - no changes will be made
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropsInBed.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                startActivity(intent);
            }
        });

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
        harvest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, CropHarvester.class);
                // Look up the key in the keys list - same position
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("itemSelected", cropID);
                startActivity(intent);
            }
        });
    }
}