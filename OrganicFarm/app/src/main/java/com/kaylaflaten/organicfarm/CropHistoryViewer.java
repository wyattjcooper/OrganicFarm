package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Wyatt on 4/19/2016.
 */
public class CropHistoryViewer extends AppCompatActivity {

    TextView section;
    TextView bed;
    TextView name;
    TextView date;
    TextView notes;
    TextView amount;
    TextView harvestDate;
    TextView owner;
    Button edit;
    Button harvest;
    Button history;

    int secN;
    int bedN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_history_viewer);

        section = (TextView) findViewById(R.id.sectionCropHistoryViewer);
        bed = (TextView) findViewById(R.id.bedCropHistoryViewer);
        name = (TextView) findViewById(R.id.cropCropHistoryViewer);
        date = (TextView) findViewById(R.id.dateCropHistoryViewer);
        harvestDate = (TextView) findViewById(R.id.harvestDateCropHistoryViewer);
        notes = (TextView) findViewById(R.id.notesCropHistoryViewer);
        amount = (TextView) findViewById(R.id.amountCropHistoryViewer);
        edit = (Button) findViewById(R.id.editCropHistoryViewer);
        //harvest = (Button) findViewById(R.id.harvestCropHistoryViewer);
        history = (Button) findViewById(R.id.HHbuttonCropHistoryViewer);
        owner = (TextView) findViewById(R.id.ownerCropHistoryViewer);


        final Bundle extras = getIntent().getExtras();

        String secS = "Section ";
        String bedS = "Bed ";
        String cropName = "";
        String cropDate = "";

        if (extras != null) {
            bedN = extras.getInt("secN", -1);
            secN = extras.getInt("bedN", -1);
            cropName = extras.getString("cropName");
            cropDate = extras.getString("cropData");
            secS = secS + (secN + 1);
            bedS = bedS + (bedN + 1);
        }
        section.setText(secS);
        bed.setText(bedS);
        //setSupportActionBar(toolbar);


        final String cropID = extras.getString("cropID");


        String[] location = new String[3];
        location[0] = "Crop History";
        location[1] = cropName;
        location[2] = cropID;

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        dbCtrl.listenAndSetText(location,name, "name", "Name");
        dbCtrl.listenAndSetText(location, date, "date", "Date");
        dbCtrl.listenAndSetText(location, notes, "notes", "Notes");
        dbCtrl.listenAndSetText(location, owner,"owner", "Owner");
        dbCtrl.listenAndSetText(location, harvestDate, "harvestDate", "Harvest Date");
        getSupportActionBar().setTitle(cropName + " Planted On " + cropDate);


        dbCtrl.listenAndSetTextToAmountOfSpecificCropHarvested(amount, cropID);
       // dbCtrl.listenAndSetTextToAmountOfCropNameHarvested(amount, name.getText().toString());



        // Edit crops
        final String finalCropName2 = cropName;
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropHistoryViewer.this, CropHistoryEditor.class);
                // Look up the key in the keys list - same position
                intent.putExtra("section", secN);
                intent.putExtra("bed", bedN);
                intent.putExtra("cropID", cropID);
                intent.putExtra("cropName", finalCropName2);
                startActivity(intent);
                finish();
            }
        });

        // Harvest the crop
        final String finalCropName = cropName;
        final String finalSecS = secS;
        final String finalBedS = bedS;
//        harvest.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CropViewer.this, CropHarvester.class);
//                // Look up the key in the keys list - same position
//                intent.putExtra("section", secN);
//                intent.putExtra("bed", bedN);
//                intent.putExtra("itemSelected", cropID);
//                intent.putExtra("cropName", name.getText().toString());
//                intent.putExtra("cropDate", date.getText().toString());
//                intent.putExtra("cropNotes", notes.getText().toString());
//                startActivityForResult(intent,1);
//            }
//        });

        // View harvest history
        final String finalCropName1 = cropName;
        history.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropHistoryViewer.this, HarvestHistory.class);
                // Look up the key in the keys list - same position
                intent.putExtra("cropID", cropID);
                intent.putExtra("cropName", finalCropName1);
                intent.putExtra("cropData", name.getText().toString() + " Planted In " + finalSecS +"," + finalBedS);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onRestart(){
        super.onRestart();

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

    //checks to see if delete was just called.  If so, finish.
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == 2 || resultCode == 3){
            finish();
        }
    }

}