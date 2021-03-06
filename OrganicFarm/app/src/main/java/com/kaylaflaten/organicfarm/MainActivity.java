package com.kaylaflaten.organicfarm;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;

/**
 * main activity - list of sections
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {

    ListView sectionitems;
    TextView totalAmountData;
    TextView totalNumHarvestsData;
    TextView totalCropsData;
    TextView userID;
    Button logout;
    DatabaseCtrl dbCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Current Farm");

        // pulls objects from xml layout file
        sectionitems = (ListView) findViewById(R.id.sectionItems);
        totalAmountData = (TextView) findViewById(R.id.totalAmountData);
        totalNumHarvestsData = (TextView) findViewById(R.id.totalNumharvestsData);
        totalCropsData = (TextView) findViewById(R.id.totalCropsData);
        userID = (TextView) findViewById(R.id.userIDTextView);
        logout = (Button) findViewById(R.id.logout_button);
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

        // sets textView text
        dbCtrl.listenAndSetTextToTotalAmount(totalAmountData);
        dbCtrl.listenAndSetTextToTotalNumberOfHarvests(totalNumHarvestsData);
        dbCtrl.listenAndSetTextToTotalNumberOfCropsEverPlanted(totalCropsData);
        dbCtrl.listenAndSetToUsername(userID);

        // centers texts
        totalAmountData.setGravity(Gravity.LEFT);
        totalNumHarvestsData.setGravity(Gravity.CENTER);
        totalCropsData.setGravity(Gravity.RIGHT);

        isOnline();

        // logs out user
        logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCtrl.logout();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

    // Checks if device/app is connected to the Internet
    public void isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean connected = (networkInfo != null && networkInfo.isConnected());

        if (!connected) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Internet Connection");
            alertDialog.setMessage("You are not connected to the Internet");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
}
