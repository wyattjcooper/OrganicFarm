package com.kaylaflaten.organicfarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import java.util.List;
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
    int secN;
    int bedN;

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

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            sectionNum = sectionNum + (secN + 1);
            bedNum = bedNum + (bedN + 1);
        }
        section.setText(sectionNum);
        bed.setText(bedNum);

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        //dbCtrl.setEntryRef(null, 2);


        // Push new data or modify old data when pressing enter button
        final String[] entryKey = {"blank"};
        final String finalSectionNum = sectionNum;
        final String finalBedNum = bedNum;
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString());


                String[] location = new String[2];
                location[0] = finalSectionNum;
                location[1] = finalBedNum;
                entryKey[0] = dbCtrl.pushObjectReturnKey(location,newEntry);

                //entryKey[0] = dbCtrl.pushEntryReturnKey(newEntry);
                intent.putExtra("pushID", entryKey[0]);
                // Initialize the harvest data
                //Harvest harvestDefault = new Harvest("Enter date here", 0.0, false,"Enter notes here", secN + 1, bedN + 1);
                //dbCtrl.setOneChildRef("Harvest");
                //dbCtrl.setEntryRef(entryKey[0], 1);
                //dbCtrl.setValueHarvest(harvestDefault);

                //String[] locationHarvest = new String[2];
                //locationHarvest[0] = "Harvest";
                //locationHarvest[1] = entryKey[0];
                //dbCtrl.setValueAtLocation(locationHarvest, null);

                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                // Go back to crop entry page
                startActivity(intent);
            }
        });

        // Navigate back to bed page - no changes will be made
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                startActivity(intent);
            }
        });
    }
}
