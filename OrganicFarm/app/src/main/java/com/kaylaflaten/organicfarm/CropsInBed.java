package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import android.widget.TextView;
import android.widget.Toast;

import com.kaylaflaten.organicfarm.DatabaseCtrl;

public class CropsInBed extends AppCompatActivity {

    //TextView sectionDisplay;
    //TextView bedDisplay;
    Button add;
    Button history;
    ListView lv;
    DatabaseCtrl dbCtrl;
    ArrayAdapter<String> aa;

    int secN;
    int bedN;

    String secS;
    String bedS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_in_be);



        add = (Button) findViewById(R.id.add);
        //reload = (Button) findViewById(R.id.cropsInBedRefreshB);
        lv = (ListView) findViewById(R.id.listView);
        history = (Button) findViewById(R.id.bedsHistoryButton);


        String[] crops = new String[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<String> cropList = new ArrayList<String>();
        cropList.addAll( Arrays.asList(crops) );
        aa = new ArrayAdapter<String>(this, R.layout.simplerow, cropList);
        lv.setAdapter(aa);

        // Grabbing Section and Bed values from intent
        secS = "Section ";
        bedS = "Bed ";

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            secN = extras.getInt("section", -1);
            bedN = extras.getInt("bed", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[2];
        location[0] = secS;
        location[1] = bedS;
        ArrayList<String> keys = dbCtrl.generateKeysListFromLocation(location, aa);

        // Add new crop by clicking the add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("new", true);
                startActivity(intent);
            }
        });


        // Go to bed history
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, BedHistory.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                startActivity(intent);

            }
        });


        // Click on a crop - pass the key to the CropManager so that it can load the crops data
        final ArrayList<String> finalKeys = keys;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, CropViewer.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("itemSelected", itemSelected);
                startActivity(intent);
            }
        });


        getSupportActionBar().setTitle(secS + " " + bedS);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(getApplicationContext(), "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onRestart(){
        super.onRestart();

        // Setting up the ArrayAdapter and ListView
        String[] crops = new String[] { };
        final ArrayList<String> cropList = new ArrayList<String>();
        cropList.addAll( Arrays.asList(crops) );
        aa = new ArrayAdapter<String>(this, R.layout.simplerow, cropList);
        lv.setAdapter(aa);


        String[] location = new String[2];
        location[0] = secS;
        location[1] = bedS;
        ArrayList<String> keys = dbCtrl.generateKeysListFromLocation(location, aa);
    }


}