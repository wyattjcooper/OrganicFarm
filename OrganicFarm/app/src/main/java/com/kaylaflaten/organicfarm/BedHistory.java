package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * History of each bed
 */
public class BedHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    CropHistoryByNameAdapter ca;
    TextView amountData;
    TextView numberData;

    int secN;
    int bedN;

    ArrayList<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_history);
        amountData = (TextView) findViewById(R.id.amount);
        numberData = (TextView) findViewById(R.id.harvested);

        getSupportActionBar().setTitle("History of Crops Planted");


        lv = (ListView) findViewById(R.id.listViewBedHistory);

        Entry[] entries = new Entry[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<Entry> entryList = new ArrayList<Entry>();
        entryList.addAll(Arrays.asList(entries));
        ca = new CropHistoryByNameAdapter(this, R.layout.crophistorybyname_in_list, entryList);
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
        keys = dbCtrl.addEntriesToEntryAdapterByNameHistorically(location, ca, (secN + 1), (bedN + 1));

        // Click on a crop - pass the key to the CropManager so that it can load the harvest data
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(BedHistory.this, CropHistoryViewer.class);

                // Look up the key in the keys list - same position
                String itemSelected = keys.get(position).toString();

                intent.putExtra("cropID", itemSelected);
                View viewAtPos = getViewByPosition(position, lv);
                TextView data = (TextView) viewAtPos.findViewById(R.id.date);
                TextView name = (TextView) viewAtPos.findViewById(R.id.crop);
                intent.putExtra("secN", secN);
                intent.putExtra("bedN", bedN);

                String date = data.getText().toString();
                intent.putExtra("cropData", date);
                intent.putExtra("cropName", name.getText().toString());
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
