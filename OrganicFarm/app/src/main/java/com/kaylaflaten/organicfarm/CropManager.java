package com.kaylaflaten.organicfarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.firebase.client.Firebase;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import android.view.View;
import android.content.Intent;
import com.kaylaflaten.organicfarm.Entry;

public class CropManager extends AppCompatActivity {

    TextView section;
    TextView bed;
    EditText name;
    EditText date;
    EditText notes;
    Button back;
    Button enter;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_manager);
        Firebase.setAndroidContext(this);

        final Firebase myFirebaseRef = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");

        section = (TextView) findViewById(R.id.textView);
        bed = (TextView) findViewById(R.id.textView2);
        name = (EditText) findViewById(R.id.editText3);
        date = (EditText) findViewById(R.id.editText);
        notes = (EditText) findViewById(R.id.editText2);
        enter = (Button) findViewById(R.id.button);
        back = (Button) findViewById(R.id.button2);
        delete = (Button) findViewById(R.id.button5);

        final Bundle extras = getIntent().getExtras();

        // If we are adding a new crop, we don't set our Firebase reference to any specific child ID
        // becuase we will push a new child on
        Firebase entryRef = myFirebaseRef.child(section.getText().toString()).child(bed.getText().toString());

        // If we selected a crop from the list, we will have passed its ID, so we set our Firebase reference to that ID
        if (extras != null) {
            String cropID = extras.getString("itemSelected");
            entryRef = myFirebaseRef.child(section.getText().toString()).child(bed.getText().toString()).child(cropID);
        }

        // Sets name if we have already selected a crop
        entryRef.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String data = (String) snapshot.getValue();
                if (data == null) {
                    name.setText("Enter name here");
                }
                else {
                    name.setText(data.toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        // Sets date if we have already selected a crop
        entryRef.child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String data = (String) snapshot.getValue();
                if (data == null) {
                    date.setText("Enter date here");
                } else {
                    date.setText(data.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        // Sets notes if we have already selected a crop
        entryRef.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String data = (String) snapshot.getValue();
                if (data == null) {
                    notes.setText("Enter notes here");
                } else {
                    notes.setText(data.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

        // Push new data or modify old data when pressing enter button
        final Firebase finalEntryRef = entryRef;
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString());
                // If we are adding a new crop, push a new child
                if (extras == null) {
                    Firebase pushRef = finalEntryRef.push();
                    pushRef.setValue(newEntry);
                    intent.putExtra("pushID", pushRef.getKey());
                }
                // If we are not adding a new crop, modify the existing child we clicked on
                else if (extras != null) {
                    finalEntryRef.setValue(newEntry);
                }
                // Go back to crop entry page
                startActivity(intent);
            }
        });

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras == null) {
                    // If we are adding a new crop, there is nothing to delete so do nothing

                }
                // If we are not adding a new crop, delete the existing child we clicked on and return to bed view
                else if (extras != null) {
                    finalEntryRef.removeValue();
                    Intent intent = new Intent(CropManager.this, CropsInBed.class);
                    startActivity(intent);
                }

            }
        });
    }
}
