
package com.kaylaflaten.organicfarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

/**
 * Beds for each section
 */
public class Beds extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beds);

        ListView beditems;
        int bedXML = 0;
        final int section;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            section = extras.getInt("section", -1);
        } else {
            section = 0;
        }

        switch(section) {
            case 0: bedXML = R.array.sec1BedList;
                break;
            case 1: bedXML = R.array.sec2BedList;
                break;
            case 2: bedXML = R.array.sec3BedList;
                break;
            case 3: bedXML = R.array.sec4BedList;
                break;
            case 4: bedXML = R.array.sec5BedList;
                break;
            default: Log.d("IntentError", "No extras sent to Intent");
                break;
        }

        beditems = (ListView) findViewById(R.id.bedItems);

        beditems.setAdapter(new ArrayAdapter<String>(Beds.this, android.R.layout.simple_list_item_1, getResources().getStringArray(bedXML)));
        beditems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(view.getContext(), CropsInBed.class);
                intent.putExtra("section", section);
                intent.putExtra("bed", position);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Section " + (section + 1));
    }
}
