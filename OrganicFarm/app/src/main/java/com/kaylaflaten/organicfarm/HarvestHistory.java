package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class HarvestHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    HarvestAdapter ha;
    Button edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_history);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Harvest History For Something");
        //setSupportActionBar(toolbar);

        String cropID = "";
        String cropName = "";
        String cropDate = "";


        lv = (ListView) findViewById(R.id.listViewHH);
        Harvest[] harvests = new Harvest[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<Harvest> harvestList = new ArrayList<Harvest>();
        harvestList.addAll(Arrays.asList(harvests));
        ha = new HarvestAdapter(this, R.layout.harvest_in_list, harvestList);
        lv.setAdapter(ha);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cropID = extras.getString("cropID");
            cropName = extras.getString("cropName");
            cropDate = extras.getString("cropData");
        }

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);
        ArrayList<String> keys = dbCtrl.addHarvestsOfSpecificCropToHarvestAdapter(cropID, ha);

        // Click on a crop - pass the key to the CropManager so that it can load the crops data
        final ArrayList<String> finalKeys = keys;
        final String finalCropID = cropID;
        final String finalCropName = cropName;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(HarvestHistory.this, HarvestViewer.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("harvestID", itemSelected);
                intent.putExtra("cropID", finalCropID);
                intent.putExtra("cropName", finalCropName);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Harvest History Of "+ cropDate);
        //toolbar.setTitle("Harvest History Of "+ cropDate);

        final String finalCropID1 = cropID;


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

}
