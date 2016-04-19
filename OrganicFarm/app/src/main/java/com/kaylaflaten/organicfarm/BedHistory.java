package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class BedHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    CropHistoryAdapter ca;
    TextView amountData;
    TextView numberData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_history);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBedsHistory);
        amountData = (TextView) findViewById(R.id.amount);
        numberData = (TextView) findViewById(R.id.harvested);

        getSupportActionBar().setTitle("History of Crops Planted");
//        setSupportActionBar(toolbar);

        String cropName = "";
        int secN = 0;
        int bedN = 0;

        lv = (ListView) findViewById(R.id.listViewBedHistory);

        Entry[] entries = new Entry[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<Entry> entryList = new ArrayList<Entry>();
        entryList.addAll(Arrays.asList(entries));
        ca = new CropHistoryAdapter(this, R.layout.crophistory_in_list, entryList);
        lv.setAdapter(ca);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            secN = extras.getInt("section", -1);
            bedN = extras.getInt("bed", -1);
        }

        getSupportActionBar().setTitle("History Of Section" + (secN + 1) + ", Bed " + (bedN + 1));

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[1];
        location[0] = "All Crops";
        final ArrayList<String> keys = dbCtrl.addEntriesToEntryAdapterBySectionAndBedHistorically(location, ca, (secN + 1), (bedN + 1));

        // Click on a crop - pass the key to the CropManager so that it can load the harvest data
        final ArrayList<String> finalKeys = keys;
        final String finalCropName = cropName;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(BedHistory.this, HarvestHistory.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("cropName", finalCropName);
                intent.putExtra("cropID", itemSelected);
                View viewAtPos = getViewByPosition(position, lv);
                TextView data = (TextView) viewAtPos.findViewById(R.id.date);

//                TextView data = (TextView)
                String date = data.getText().toString();
                intent.putExtra("cropData", date);
                startActivity(intent);
            }
        });
    dbCtrl.listenAndSetTextToAmountInSectionAndBedHistorically(amountData, (secN + 1), (bedN + 1));
    dbCtrl.listenAndSetTextToTotalNumberOfCropsHistoricallyInSectionBed(numberData, (secN + 1), (bedN + 1));

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
