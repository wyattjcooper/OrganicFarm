package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List of each entry already harvested
 */
public class EntriesHistory extends AppCompatActivity {

    ListView lv;
    DatabaseCtrl dbCtrl;
    CropHistoryAdapter ca;
    TextView amountData;
    String cropName;
    Entry[] entries;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_history);

        amountData = (TextView) findViewById(R.id.entriesHistoryAmountData);

        cropName = "";

        lv = (ListView) findViewById(R.id.entriesHistoryListView);

        entries = new Entry[]{};

        // Setting up the ArrayAdapter and ListView
        final ArrayList<Entry> entryList = new ArrayList<Entry>();
        entryList.addAll(Arrays.asList(entries));
        ca = new CropHistoryAdapter(this, R.layout.crophistory_in_list, entryList);
        lv.setAdapter(ca);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cropName = extras.getString("cropName");
        }

        // Adding title to action bar
        getSupportActionBar().setTitle("History Of " + cropName);

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[2];
        location[0] = "Crop History";
        location[1] = cropName;
        final ArrayList<String> keys = dbCtrl.addEntriesToEntryAdapter(location, ca);

        // Click on a crop - pass the key to the CropManager so that it can load the harvest data
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(EntriesHistory.this, CropHistoryViewer.class);
                // Look up the key in the keys list - same position
                String itemSelected = keys.get(position).toString();
                intent.putExtra("cropName", cropName);
                intent.putExtra("cropID", itemSelected);
                View viewAtPos = getViewByPosition(position, lv);
                TextView data = (TextView) viewAtPos.findViewById(R.id.dateBySB);
                TextView sectionBed = (TextView) viewAtPos.findViewById(R.id.location);
                String sectionBED = sectionBed.getText().toString();
                final Pattern pattern = Pattern.compile("\\d+"); // the regex
                final Matcher matcher = pattern.matcher(sectionBED); // your string

                final ArrayList<Integer> ints = new ArrayList<Integer>(); // results

                while (matcher.find()) { // for each match
                    ints.add(Integer.parseInt(matcher.group())); // convert to int
                }

                intent.putExtra("secN",ints.get(0) - 1);
                intent.putExtra("bedN",ints.get(1) - 1);

                String date = data.getText().toString();
                intent.putExtra("cropData", date);
                startActivity(intent);
            }
        });

        dbCtrl.listenAndSetTextToAmountOfCropNameHarvested(amountData, cropName);



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EntriesHistory Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.kaylaflaten.organicfarm/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "EntriesHistory Page",
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.kaylaflaten.organicfarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    // updates list when activity comes to front because data could have changed
    @Override
    public void onRestart() {
        super.onRestart();
        // Setting up the ArrayAdapter and ListView
        final ArrayList<Entry> entryList = new ArrayList<Entry>();
        entryList.addAll(Arrays.asList(entries));
        ca = new CropHistoryAdapter(this, R.layout.crophistory_in_list, entryList);
        lv.setAdapter(ca);

        // Attach crops already in the database to our list
        String[] location = new String[2];
        location[0] = "Crop History";
        location[1] = cropName;
        final ArrayList<String> keys = dbCtrl.addEntriesToEntryAdapter(location, ca);

    }
}
