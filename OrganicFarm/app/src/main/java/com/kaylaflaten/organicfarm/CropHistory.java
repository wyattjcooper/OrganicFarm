package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kaylaflaten.organicfarm.DatabaseCtrl;

import java.util.ArrayList;
import java.util.Arrays;

public class CropHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_history3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.cropHistoryListView);
        String[] crops = new String[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<String> cropList = new ArrayList<String>();
        cropList.addAll(Arrays.asList(crops));
        aa = new ArrayAdapter<String>(this, R.layout.simplerow, cropList);
        lv.setAdapter(aa);

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[1];
        location[0] = "Crop History";
        ArrayList<String> keys = dbCtrl.addCropNamesToArrayAdapter(location, aa);

        // Click on a crop - pass the key to the CropManager so that it can load the crops data
        final ArrayList<String> finalKeys = keys;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropHistory.this, EntriesHistory.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("cropName", itemSelected);
                startActivity(intent);
            }
        });


    }

}
