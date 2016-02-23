package com.kaylaflaten.organicfarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ArrayAdapter.*;
import android.widget.ListView;
import android.widget.Button;

/**
 * Created by Kayla Flaten on 2/16/2016.
 */
public class beds  extends AppCompatActivity {

    ListView beditems;

    ArrayAdapter<String> aa;

    Button add;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beds);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        beditems = (ListView) findViewById(R.id.bedItems);
        /*Check which section was clicked; change next line with xml name */

        add = (Button) findViewById(R.id.button3);

        aa = new ArrayAdapter<String>(beds.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sectionList));

        beditems.setAdapter(aa);

        add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                beditems.getAdapter().
            }
        });



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
