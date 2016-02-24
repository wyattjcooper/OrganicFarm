package com.kaylaflaten.organicfarm;

import android.content.Intent;
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
import java.util.List;

/**
 * Created by Kayla Flaten on 2/16/2016.
 */
public class beds  extends AppCompatActivity {

    ListView beditems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beditems = (ListView) findViewById(R.id.bedItems);
        /*Check which section was clicked; change next line with xml name */

//        beditems.setAdapter(new ArrayAdapter<String>(beds.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sectionList)));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(beds.this, CropsInBed.class);
                startActivity(intent);
            }
        });
    }
}
