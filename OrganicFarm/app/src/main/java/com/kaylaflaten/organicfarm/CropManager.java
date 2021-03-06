package com.kaylaflaten.organicfarm;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Allows a user to add a crop
 */
public class CropManager extends AppCompatActivity {

    TextView section;
    TextView bed;
    EditText name;
    TextView date;
    TextView owner;
    TextView harvestDate;
    EditText notes;
    Button enter;

    String secS;
    String bedS;

    int secN;
    int bedN;

    private SimpleDateFormat dateFormatter;

    private DatePickerDialog datePicker1;
    private DatePickerDialog datePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_manager);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        setDateTimeField();

        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (EditText) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.dateByName);
        harvestDate = (TextView) findViewById(R.id.harvestDate);
        notes = (EditText) findViewById(R.id.notesHarvestViewer);
        enter = (Button) findViewById(R.id.enter);
        owner = (TextView) findViewById(R.id.planter);

        final Bundle extras = getIntent().getExtras();

        secS = "Section ";
        bedS = "Bed ";

        if (extras != null) {
            bedN = extras.getInt("bed", -1);
            secN = extras.getInt("section", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }
        section.setText(secS);
        bed.setText(bedS);

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        // Push new entry
        final String[] entryKey = {"blank"};
        enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry newEntry = new Entry(name.getText().toString(), date.getText().toString(), harvestDate.getText().toString(), notes.getText().toString(), owner.getText().toString(),false, secN + 1, bedN + 1);
                String[] location = new String[2];
                location[0] = secS;
                location[1] = bedS;
                entryKey[0] = dbCtrl.pushObjectReturnKey(location,newEntry);
                String key = entryKey[0];
                if (entryKey[0] != null) {
                    String[] locationOverall = new String[2];
                    locationOverall[0] = "All Crops";
                    locationOverall[1] = key;
                    dbCtrl.setValueAtLocation(locationOverall, newEntry);

                    String[] allActivitiesLocation = new String[2];
                    allActivitiesLocation[0] = "All Activities";
                    allActivitiesLocation[1] = key;
                    dbCtrl.setValueAtLocation(allActivitiesLocation, newEntry);
                }
                finish();
            }
        });

        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                datePicker1.show();

            }
        });

        harvestDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                datePicker2.show();

            }
        });

        if (!dbCtrl.getUID().equals("no id")) {
            dbCtrl.listenAndSetToUsername(owner);
        }

    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePicker1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                harvestDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
