
package com.kaylaflaten.organicfarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

/**
 * Created by Kayla Flaten on 2/16/2016.
 */
public class beds extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beds);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ListView beditems;
        int bedXML = 0;
        int section;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            section = extras.getInt("section", -1);
        } else {
            section = -1;
        }

        //TODO: Make switch statement
        if (section == 0) {
            bedXML = R.array.sec1BedList;
        } else if (section == 1) {
            bedXML = R.array.sec2BedList;
        } else if (section == 2) {
            bedXML = R.array.sec3BedList;
        } else if (section == 3) {
            bedXML = R.array.sec4BedList;
        } else if (section == 4) {
            bedXML = R.array.sec5BedList;
        } else {
            Log.d("IntentError", "No extras sent to Intent");
        }

        beditems = (ListView) findViewById(R.id.bedItems);
        /*Check which section was clicked; change next line with xml name */

        beditems.setAdapter(new ArrayAdapter<String>(beds.this, android.R.layout.simple_list_item_1, getResources().getStringArray(bedXML)));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }



}

//String section = "";
//if (extras != null) {
//        section = extras.getString("section");
//        }
//        Intent intent = new Intent(beds.this, CropsInBed.class);
//        intent.putExtra("section", section);
//        startActivity(intent);