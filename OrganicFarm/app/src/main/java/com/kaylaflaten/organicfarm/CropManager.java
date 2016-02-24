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

        Bundle extras = getIntent().getExtras();
        Firebase entryRef = myFirebaseRef.child(section.getText().toString()).child(bed.getText().toString());

        if (extras != null) {
            String name = extras.getString("itemSelected");
            entryRef = myFirebaseRef.child(section.getText().toString()).child(bed.getText().toString()).child(name);

        }


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

        entryRef.child("text").addValueEventListener(new ValueEventListener() {
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

        final Firebase finalEntryRef = entryRef;
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), notes.getText().toString());
                Firebase pushRef = finalEntryRef.push();
                pushRef.setValue(newEntry);
                Intent intent = new Intent(CropManager.this, CropsInBed.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("pushID",pushRef.getKey());
                startActivity(intent);
            }
        });

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropManager.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
