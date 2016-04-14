package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kayla Flaten on 4/13/2016.
 */
public class ToDoEditor extends AppCompatActivity{

    EditText title;
    EditText date;
    EditText notes;
    Button back;
    Button enter;
    Button delete;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePicker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_add_item);

        title = (EditText) findViewById(R.id.title);
        date = (EditText) findViewById(R.id.date);
        notes = (EditText) findViewById(R.id.notes);
        enter = (Button) findViewById(R.id.enter);
        back = (Button) findViewById(R.id.back);
        delete = (Button) findViewById(R.id.delete);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        final Bundle extras = getIntent().getExtras();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date.setEnabled(false);
                datePicker.show();

            }
        });


        // Push new data or modify old data when pressing enter button
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoEditor.this, ToDoEntry.class);
                // Entry newEntry = new Entry(title.getText().toString(), date.getText().toString(), notes.getText().toString());
                // If we are adding a new crop, push a new child

                finish();
            }
        });

        // Navigate back to bed page - no changes will be made

        back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        // Delete crops
        delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras.getBoolean("new") == true) {
                    // If we are adding a new crop, there is nothing to delete so do nothing
                }
                // If we are not adding a new crop, delete the existing child we clicked on and return to bed view
                else if (extras.getBoolean("new") != true) {
                    //Intent intent = new Intent(ToDoEditor.this, );

                    //startActivity(intent);
                    finish();

                }
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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
