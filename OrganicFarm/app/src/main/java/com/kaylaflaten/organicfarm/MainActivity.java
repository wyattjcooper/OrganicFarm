package com.kaylaflaten.organicfarm;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    ListView sectionitems;
    TextView totalAmountData;
    TextView totalNumHarvestsData;
    DatabaseCtrl dbCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionitems = (ListView) findViewById(R.id.sectionItems);
        totalAmountData = (TextView) findViewById(R.id.totalAmountData);
        totalNumHarvestsData = (TextView) findViewById(R.id.totalNumharvestsData);
        dbCtrl = new DatabaseCtrl(this);

        sectionitems.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sectionList)));
        sectionitems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(view.getContext(), Beds.class);
                intent.putExtra("section", position);
                startActivity(intent);
            }
        });

        dbCtrl.listenAndSetTextToTotalAmount(totalAmountData);
        dbCtrl.listenAndSetTextToTotalNumberOfHarvests(totalNumHarvestsData);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cropHistorySelected) {
            Intent cropHistory = new Intent(MainActivity.this, CropHistory.class);
            startActivity(cropHistory);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
