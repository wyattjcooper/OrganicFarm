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
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class CropsInBed extends AppCompatActivity {

    Button add;
    ListView lv;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_in_be);

        lv = (ListView) findViewById(R.id.listView);

        add = (Button) findViewById(R.id.button4);

        String[] planets = new String[] { };

        ArrayList<String> planetList = new ArrayList<String>();

        planetList.addAll( Arrays.asList(planets) );

        aa = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                intent.putExtra("itemSelected",lv.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("pushID");
            aa.add(name);
        }

        lv.setAdapter(aa);
    }

}
