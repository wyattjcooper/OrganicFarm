package com.kaylaflaten.organicfarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carmen on 3/24/2016.
 */
public class PicDate extends AppCompatActivity {
    DatePicker datePic;
    TextView textViewUserDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    datePic = new DatePicker(this);
        textViewUserDate = (TextView) findViewById(R.id.date);

    // hide the whole calendar view (works in api 11 or greater)
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    if (currentapiVersion >= 11) {
        datePic.setCalendarViewShown(false);
    }

    // initialize the date to current date
    SimpleDateFormat sdfDateTime = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    String dateStr = sdfDateTime.format(new Date(System.currentTimeMillis()));

    String[] dateSplit = dateStr.split("-");
    int currentMonth = Integer.parseInt(dateSplit[0]);
    int currentDay = Integer.parseInt(dateSplit[1]);
    int currentYear = Integer.parseInt(dateSplit[2]);

    // hide the whole calendar view (works in api 11 or greater)
    if (currentapiVersion >= 11) {
        datePic.setCalendarViewShown(false);
    }

    // create the TextView
    textViewUserDate = new TextView(this);
    textViewUserDate.setGravity(Gravity.CENTER);
//
//        // initialize the date to current date
//        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        String dateStr = sdfDateTime.format(new Date(System.currentTimeMillis()));
//
//        String[] dateSplit = dateStr.split("-");
//        int currentYear = Integer.parseInt(dateSplit[0]);
//        int currentMonth = Integer.parseInt(dateSplit[1]);
//        int currentDay = Integer.parseInt(dateSplit[2]);

    // to show date and day of week in the TextView
    setHumanReadableDate(currentYear, currentMonth, currentDay);

    // initialize date picker listener
    // currentMonth - 1, because on the picker, 0 is January
    datePic.init(currentYear, currentMonth - 1, currentDay, birthdayListener);

    // add to the container
    LinearLayout linearLayoutCalTvContainer = new LinearLayout(this);
    linearLayoutCalTvContainer.setOrientation(LinearLayout.VERTICAL);
    linearLayoutCalTvContainer.addView(datePic);
    linearLayoutCalTvContainer.addView(textViewUserDate);

    // set the views for the activity
    setContentView(linearLayoutCalTvContainer);

}

// the date picker listener
DatePicker.OnDateChangedListener birthdayListener = new DatePicker.OnDateChangedListener() {

    @Override
    public void onDateChanged(DatePicker birthDayDatePicker,
                              int newYear, int newMonth, int newDay) {

        try{

            // currentMonth + 1, to retrieve proper month
            setHumanReadableDate(newYear, newMonth + 1, newDay);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }};

    // show the user a better date format
    public void setHumanReadableDate(int newYear, int newMonth, int newDay){
        try {

            String clickedDate = newYear + "-" + newMonth + "-" + newDay;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(clickedDate);

            SimpleDateFormat sdfDateTime = new SimpleDateFormat("MM dd, yyyy 'is' EEEE", Locale.US);
            String dateStr = sdfDateTime.format(d);

            textViewUserDate.setText(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
