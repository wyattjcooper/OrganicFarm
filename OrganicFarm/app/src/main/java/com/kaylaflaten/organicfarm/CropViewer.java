package com.kaylaflaten.organicfarm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Allows a user to view crop info
 */
public class CropViewer extends AppCompatActivity {

    TextView section;
    TextView bed;
    TextView name;
    TextView date;
    TextView notes;
    TextView harvestDate;
    TextView amount;
    Button back;
    Button edit;
    Button harvest;
    Button history;
    TextView owner;
    int[] admin = new int[1];
    int access = 0;

    int secN;
    int bedN;

    String secS;
    String bedS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_viewer);

        section = (TextView) findViewById(R.id.section);
        bed = (TextView) findViewById(R.id.bed);
        name = (TextView) findViewById(R.id.crop);
        date = (TextView) findViewById(R.id.date);
        harvestDate = (TextView) findViewById(R.id.harvestDate);
        notes = (TextView) findViewById(R.id.notes);
        amount = (TextView) findViewById(R.id.amount);
        edit = (Button) findViewById(R.id.edit);
        harvest = (Button) findViewById(R.id.harvest);
        history = (Button) findViewById(R.id.harvestHistory);
        owner = (TextView) findViewById(R.id.plantedBy);

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

        String[] location = new String[3];
        location[0] = secS;
        location[1] = bedS;

        final String cropID = extras.getString("itemSelected");

        location[2] = cropID;

        // Create the DatabaseCtrl object
        final DatabaseCtrl dbCtrl = new DatabaseCtrl(this);

        // set textView information
        dbCtrl.listenAndSetText(location,name, "name", "Name");
        dbCtrl.listenAndSetText(location, date, "date", "Date");
        dbCtrl.listenAndSetText(location, notes, "notes", "Notes");
        dbCtrl.listenAndSetText(location, harvestDate, "harvestDate", "Harvest Date");
        dbCtrl.listenAndSetText(location, owner, "owner", "Owner");
        dbCtrl.listenAndSetTextToAmountOfSpecificCropHarvested(amount, cropID);
        access = dbCtrl.listenSetAdmin(admin);


        // Edit crops
        edit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (admin[0] == 1) {
                    Intent intent = new Intent(CropViewer.this, CropEditor.class);
                    // Look up the key in the keys list - same position
                    intent.putExtra("section", secN);
                    intent.putExtra("bed", bedN);
                    intent.putExtra("itemSelected", cropID);
                    intent.putExtra("cropName", name.getText().toString());
                    startActivityForResult(intent, 1);
                }
                else{
                    new AlertDialog.Builder(CropViewer.this)
                            .setTitle("You must have admin priveledges to edit crops")
                            .show();
                }
            }
        });

        // Harvest the crop
        harvest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin[0] == 1) {
                    Intent intent = new Intent(CropViewer.this, CropHarvester.class);
                    // Look up the key in the keys list - same position
                    intent.putExtra("section", secN);
                    intent.putExtra("bed", bedN);
                    intent.putExtra("itemSelected", cropID);
                    intent.putExtra("cropName", name.getText().toString());
                    intent.putExtra("cropDate", date.getText().toString());
                    intent.putExtra("cropNotes", notes.getText().toString());
                    startActivityForResult(intent, 1);
                } else {
                    new AlertDialog.Builder(CropViewer.this)
                            .setTitle("You must have admin priveledges to harvest crops")
                            .show();
                }
            }
        });

        // View harvest history
        history.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropViewer.this, HarvestHistory.class);
                // Look up the key in the keys list - same position
                intent.putExtra("cropID", cropID);
                intent.putExtra("cropName", name.getText().toString());
                intent.putExtra("cropData", name.getText().toString() + " Planted In " + secS +"," + bedS);
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

    // checks to see if crop was deleted or harvested.  If so, finish.
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // returnCode = 2 is delete, returnCode = 3 is harvested
        if (resultCode == 2 || resultCode == 3){
            finish();
        }
    }

}