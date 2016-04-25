package com.kaylaflaten.organicfarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import android.widget.TextView;
import android.widget.Toast;

import com.kaylaflaten.organicfarm.DatabaseCtrl;

/**
 * List of crops in a bed
 */
public class CropsInBed extends AppCompatActivity {

    Button add;
    Button history;
    ListView lv;
    ListView lv_harvest;
    DatabaseCtrl dbCtrl;
    CropAdapter ca;
    HarvestAdapter ha;
    ArrayList<String> keys;
    ArrayList<String> keys_harvest;

    int secN;
    int bedN;

    String secS;
    String bedS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_in_be);

        add = (Button) findViewById(R.id.add);
        lv = (ListView) findViewById(R.id.listView);
        lv_harvest = (ListView) findViewById(R.id.harvest_list_cropsInBed);
        history = (Button) findViewById(R.id.bedsHistoryButton);

        Entry[] crops = new Entry[] { };
        Harvest[] harvests = new Harvest[] { };

        // Setting up the ArrayAdapter and ListView
        final ArrayList<Entry> cropList = new ArrayList<Entry>();
        final ArrayList<Harvest> harvestList = new ArrayList<Harvest>();
        harvestList.addAll(Arrays.asList(harvests));

        cropList.addAll( Arrays.asList(crops) );
        ca = new CropAdapter(this, R.layout.crops_in_list, cropList);
        ha = new HarvestAdapter(this, R.layout.harvest_in_list, harvestList);
        lv.setAdapter(ca);
        lv_harvest.setAdapter(ha);

        // Grabbing Section and Bed values from intent
        secS = "Section ";
        bedS = "Bed ";

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            secN = extras.getInt("section", -1);
            bedN = extras.getInt("bed", -1);
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }

        // Set up our database control object
        dbCtrl = new DatabaseCtrl(this);

        // Attach crops already in the database to our list
        String[] location = new String[2];
        location[0] = secS;
        location[1] = bedS;
        ArrayList<String> keys = dbCtrl.addEntriesOfBedToCropAdapter(secS, bedS, ca);

        ArrayList<String> keys_harvest = dbCtrl.addHarvestsOfBedToHarvestAdapter(secN + 1, bedN + 1, ha);

        // set action bar title
        getSupportActionBar().setTitle(secS + " " + bedS);

        // Add new crop by clicking the add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, CropManager.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("new", true);
                startActivity(intent);
            }
        });


        // Go to bed history
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsInBed.this, BedHistory.class);
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                startActivity(intent);

            }
        });


        // Click on a crop - pass the key to the CropManager so that it can load the crops data
        final ArrayList<String> finalKeys = keys;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, CropViewer.class);
                // Look up the key in the keys list - same position
                String itemSelected = finalKeys.get(position).toString();
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("itemSelected", itemSelected);
                startActivity(intent);
            }
        });

        final ArrayList<String> finalKeys_harvest = keys_harvest;
        lv_harvest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CropsInBed.this, HarvestViewer.class);
                // Look up the key in the keys list - same position
                View viewAtPos = getViewByPosition(position, lv_harvest);
                TextView name = (TextView) viewAtPos.findViewById(R.id.name);
                String itemSelected = finalKeys_harvest.get(position).toString();
                intent.putExtra("harvestID", itemSelected);
                intent.putExtra("cropName", name.getText().toString());
                startActivity(intent);
            }
        });
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

    @Override
    public void onRestart(){
        super.onRestart();

        // Setting up the ArrayAdapter and ListView
        Entry[] crops = new Entry[] { };
        Harvest[] harvests = new Harvest[] { };

        final ArrayList<Entry> cropList = new ArrayList<Entry>();
        cropList.addAll( Arrays.asList(crops) );
        final ArrayList<Harvest> harvestList = new ArrayList<Harvest>();
        harvestList.addAll(Arrays.asList(harvests));
        ca = new CropAdapter(this, R.layout.crops_in_list, cropList);
        ha = new HarvestAdapter(this, R.layout.harvest_in_list, harvestList);
        lv.setAdapter(ca);
        lv_harvest.setAdapter(ha);



        String[] location = new String[2];
        location[0] = secS;
        location[1] = bedS;
        ArrayList<String> keys = dbCtrl.addEntriesOfBedToCropAdapter(secS, bedS, ca);
        keys_harvest = dbCtrl.addHarvestsOfBedToHarvestAdapter(secN + 1, bedN + 1, ha);
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

}