package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import android.widget.TextView;
import com.kaylaflaten.organicfarm.DatabaseCtrl;

public class CropsInBed extends AppCompatActivity {

    TextView sectionDisplay;
    TextView bedDisplay;
    Button add;
    Button back;
    ListView lv;
    DatabaseCtrl dbCtrl;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_in_be);

        sectionDisplay = (TextView) findViewById(R.id.textView6);
        bedDisplay = (TextView) findViewById(R.id.textView7);
        add = (Button) findViewById(R.id.button4);
        back = (Button) findViewById(R.id.button3);
        lv = (ListView) findViewById(R.id.listView);
        String[] crops = new String[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<String> cropList = new ArrayList<String>();
        cropList.addAll( Arrays.asList(crops) );
        aa = new ArrayAdapter<String>(this, R.layout.simplerow, cropList);
        lv.setAdapter(aa);

        // Grabbing Section and Bed values from intent
        String sectionNum = "Section ";
        String bedNum = "Bed ";
        int sec = -1;
        int bedN = -1;

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sec = extras.getInt("section", -1);
            bedN = extras.getInt("bed", -1);
            sectionNum = sectionNum + (sec + 1);
            bedNum = bedNum + (bedN + 1);
        }

        // Display current section/bed in the TextViews
        sectionDisplay.setText(sectionNum);
        bedDisplay.setText(bedNum);

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(sectionNum, bedNum, this);

        // Attach crops already in the database to our list
        ArrayList<String> keys = dbCtrl.generateKeysList(aa);

        // Add new crop by clicking the add button
        final int finalSec1 = sec;
        final int finalBedN = bedN;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                intent.putExtra("section", finalSec1);
                intent.putExtra("bed", finalBedN);
                intent.putExtra("new",true);
                startActivity(intent);
            }
        });

        // Click on a crop - pass the key to the CropManager so that it can load the crops data
        final int finalSec2 = sec;
        final int finalBedN1 = bedN;
        final ArrayList<String> finalKeys = keys;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("section", finalSec2);
                intent.putExtra("bed", finalBedN1);
                intent.putExtra("itemSelected", itemSelected);
                startActivity(intent);
            }
        });

        // Go back to Beds
        final int finalSec = sec;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, Beds.class);
                intent.putExtra("section", finalSec);
                startActivity(intent);
            }
        });
    }


}