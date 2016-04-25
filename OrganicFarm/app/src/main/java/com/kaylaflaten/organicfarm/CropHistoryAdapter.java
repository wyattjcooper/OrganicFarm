package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Creates list of crops for the history
 */
public class CropHistoryAdapter extends ArrayAdapter<Entry> {
    // declaring our ArrayList of items
    private ArrayList<Entry> entries;

    public CropHistoryAdapter(Context context, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, textViewResourceId, objects);
        this.entries = objects;
    }

    // we are overriding the getView method here
    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view we are converting to a local variable
        View v = convertView;

        // check to see if the view is null. if so, inflate it.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.crophistory_in_list, null);
        }

        Entry e = entries.get(position);

        if (e != null) {
            TextView date = (TextView) v.findViewById(R.id.dateBySB);
            TextView location = (TextView) v.findViewById(R.id.location);
            TextView notes = (TextView) v.findViewById(R.id.notes);
            TextView harvestDate = (TextView) v.findViewById(R.id.harvestCompleted);
            TextView plantedBy = (TextView) v.findViewById(R.id.plantedBy);

            date.setText(e.getDate().toString());
            location.setText("Section: " + e.getSection() + " Bed: " + e.getBed());
            notes.setText(e.getNotes());
            plantedBy.setText("Planted By " + e.getOwner());
            harvestDate.setText(e.getHarvestDate());
        }
        return v;
    }
}
