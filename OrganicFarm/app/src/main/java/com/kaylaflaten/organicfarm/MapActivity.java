package com.kaylaflaten.organicfarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by Carmen on 2/17/2016.
 */
public class MapActivity extends Activity {
    MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map = new MapView(MapActivity.this);
        setContentView(map);
    }



}
