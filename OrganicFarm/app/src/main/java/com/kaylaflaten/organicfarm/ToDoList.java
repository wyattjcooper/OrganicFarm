package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kayla Flaten on 4/12/2016.
 */
public class ToDoList extends AppCompatActivity{

    TextView title;
    TextView date;
    TextView notes;
    Button back;
    Button edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_item_view);

        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        notes = (TextView) findViewById(R.id.notes);
        back = (Button) findViewById(R.id.back);
        edit = (Button) findViewById(R.id.edit);

        final Bundle extras = getIntent().getExtras();

        // Go Back
        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Edit
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoList.this, ToDoEditor.class);
                startActivity(intent);
            }
        });
        
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(getApplicationContext(), "Back button clicked", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }



}
