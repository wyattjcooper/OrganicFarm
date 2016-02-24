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
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.kaylaflaten.organicfarm.Entry;

public class CropsInBed extends AppCompatActivity {

    Button add;
    Button back;
    ListView lv;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_in_be);
        Firebase.setAndroidContext(this);

        add = (Button) findViewById(R.id.button4);

        back = (Button) findViewById(R.id.button3);

        lv = (ListView) findViewById(R.id.listView);

        String[] planets = new String[] { };

        // Store the keys of the crops retrieved from Firebase
        final ArrayList<String> keys = new ArrayList<String>();

        // Store crop names
        final ArrayList<String> planetList = new ArrayList<String>();

        planetList.addAll( Arrays.asList(planets) );

        aa = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);

        lv.setAdapter(aa);



        final Firebase myFirebaseRef = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");

        Firebase bedRef = myFirebaseRef.child("Section").child("Bed");

        // Attach crops already in the database to our list
        bedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Entry cropEntry = postSnapshot.getValue(Entry.class);
                    String key = postSnapshot.getKey();
                    keys.add(key);
                    aa.add(cropEntry.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                startActivity(intent);
            }
        });


        // Pass key of the selected crop to the CropManager so that it can load the data
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                String itemSelected = keys.get(position).toString();
                intent.putExtra("itemSelected", itemSelected);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
