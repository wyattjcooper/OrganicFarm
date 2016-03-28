package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Object;
import java.util.TimerTask;
import java.util.Timer;

public class EntriesHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    CropHistoryAdapter ca;
    TextView amountData;
    TimerTask timer;
    Timer scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEntriesHistory);
        toolbar.setTitle("History of Crops Planted");
        setSupportActionBar(toolbar);

        String cropName = "";

        lv = (ListView) findViewById(R.id.entriesHistoryListView);
        amountData = (TextView) findViewById(R.id.entriesHistoryAmountData);

        Entry[] entries = new Entry[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<Entry> entryList = new ArrayList<Entry>();
        entryList.addAll(Arrays.asList(entries));
        ca = new CropHistoryAdapter(this, R.layout.crophistory_in_list, entryList);
        lv.setAdapter(ca);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cropName = extras.getString("cropName");
        }

        toolbar.setTitle("History of "+cropName);

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[2];
        location[0] = "Crop History";
        location[1] = cropName;
        final ArrayList<String> keys = dbCtrl.addEntriesToEntryAdapter(location, ca);

        // Click on a crop - pass the key to the CropManager so that it can load the harvest data
        final ArrayList<String> finalKeys = keys;
        final String finalCropName = cropName;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(EntriesHistory.this, HarvestHistory.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("cropName", finalCropName);
                intent.putExtra("cropID", itemSelected);
                startActivity(intent);
            }
        });

        final String[] locationHarvest = new String[1];
        locationHarvest[0] = "Harvest";
        scheduler = new Timer();
        timer = new TimerTask() {
            @Override
            public void run() {
                if (keys.isEmpty() == false) {
                    dbCtrl.listenAndSetTextToAmountHarvested(locationHarvest, amountData, keys.get(0), "Error");
                }
            }
        };

        scheduler.schedule(timer,0, 10);



    }



}
