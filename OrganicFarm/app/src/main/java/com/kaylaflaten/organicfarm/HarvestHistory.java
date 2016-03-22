package com.kaylaflaten.organicfarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class HarvestHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    HarvestAdapter ha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String cropID = "";

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

        }

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[2];
        location[0] = "Harvest";
        location[1] = cropID;
        ArrayList<String> keys = dbCtrl.addHarvestsToHarvestAdapter(location, ha);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finish();

            }
        });
    }

}
