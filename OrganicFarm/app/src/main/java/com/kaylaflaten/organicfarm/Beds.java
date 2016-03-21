
package com.kaylaflaten.organicfarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Kayla Flaten on 2/16/2016.
 */
public class Beds extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beds);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ListView beditems;
        TextView sectionDisplay;
        Button back;
        int bedXML = 0;
        final int section;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            section = extras.getInt("section", -1);
        } else {
            section = -1;
        }

        //TODO: Make switch statement
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
        sectionDisplay = (TextView) findViewById(R.id.textView8);
        back = (Button) findViewById(R.id.button6);
        sectionDisplay.setText("Section " + (section + 1));
        beditems = (ListView) findViewById(R.id.bedItems);
        /*Check which section was clicked; change next line with xml name */

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
