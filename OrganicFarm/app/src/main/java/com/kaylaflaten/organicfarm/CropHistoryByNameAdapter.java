package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Creates list of crops for the history by name
 */
public class CropHistoryByNameAdapter extends ArrayAdapter<Entry> {
    // declaring our ArrayList of items
    private ArrayList<Entry> entries;

    //override the constructor for ArrayAdapter
    public CropHistoryByNameAdapter(Context context, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, textViewResourceId, objects);
        this.entries = objects;
    }


    // define how each list item looks
    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view we are converting to a local variable
        View v = convertView;



        // inflates view
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.crophistorybyname_in_list, null);
        }

        Entry e = entries.get(position);

        if (e != null) {
            TextView crop = (TextView) v.findViewById(R.id.crop);
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView notes = (TextView) v.findViewById(R.id.notes);
            TextView harvestDate = (TextView) v.findViewById(R.id.harvestCompleted);
            TextView plantedBy = (TextView) v.findViewById(R.id.plantedBy);
            crop.setText(e.getName());
            date.setText(e.getDate().toString());
            harvestDate.setText(e.getHarvestDate());
            plantedBy.setText(e.getOwner());
            notes.setText(e.getNotes());



        }
        return v;
    }
}

