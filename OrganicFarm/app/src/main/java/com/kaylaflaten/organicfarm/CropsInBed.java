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
import android.widget.TextView;

public class CropsInBed extends AppCompatActivity {

    TextView sectionDisplay;
    Button add;
    Button back;
    ListView lv;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_in_be);
        Firebase.setAndroidContext(this);

        sectionDisplay = (TextView) findViewById(R.id.textView6);

        add = (Button) findViewById(R.id.button4);

        back = (Button) findViewById(R.id.button3);

        lv = (ListView) findViewById(R.id.listView);

        String[] crops = new String[] { };

        // Store the keys of the crops retrieved from Firebase
        final ArrayList<String> keys = new ArrayList<String>();

        // Setting up the ArrayAdapter
        final ArrayList<String> cropList = new ArrayList<String>();

        cropList.addAll( Arrays.asList(crops) );

        aa = new ArrayAdapter<String>(this, R.layout.simplerow, cropList);

        lv.setAdapter(aa);

        final Bundle extras = getIntent().getExtras();

        String sectionNum = "Section";

        if (extras != null) {
            sectionNum = extras.getString("section");
        }

        // Get the reference to our Firebase database
        final Firebase myFirebaseRef = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");

        // Set up reference to the section and bed
        Firebase bedRef = myFirebaseRef.child(sectionNum).child("Bed");


        // Display current section in the TextView
        sectionDisplay.setText(sectionNum);

        // Attach crops already in the database to our list
        bedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry cropEntry = postSnapshot.getValue(Entry.class);
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    // Add key to keys list
                    keys.add(key);
                    // Add name to the list by adding it to the ArrayAdapter
                    aa.add(cropEntry.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        // Add new crop by clicking the add button
        final String finalSectionNum = sectionNum;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                intent.putExtra("section", finalSectionNum);
                startActivity(intent);
            }
        });


        // Click on a crop - pass the key to the CropManager so that it can load the crops data
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                // Look up the key in the keys list - same position
                String itemSelected = keys.get(position).toString();
                intent.putExtra("section", finalSectionNum);
                intent.putExtra("itemSelected", itemSelected);
                startActivity(intent);
            }
        });

        // Go back to Main Activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
