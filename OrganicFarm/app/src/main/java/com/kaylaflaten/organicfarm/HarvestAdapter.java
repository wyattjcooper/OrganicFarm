package com.kaylaflaten.organicfarm;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Creates custom list for harvested crops
 * Code partially taken from https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 */
public class HarvestAdapter extends ArrayAdapter<Harvest> {
    // declaring our ArrayList of items
    private ArrayList<Harvest> harvests;

    public HarvestAdapter(Context context, int textViewResourceId, ArrayList<Harvest> objects) {
        super(context, textViewResourceId, objects);
        this.harvests = objects;
    }

    // defines how each view will look
    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view to a local variable
        View v = convertView;



        // inflates view
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.harvest_in_list, null);
        }

        Harvest h = harvests.get(position);

        if (h != null) {

            TextView date = (TextView) v.findViewById(R.id.date);
            TextView amount = (TextView) v.findViewById(R.id.amount);
            TextView notes = (TextView) v.findViewById(R.id.notes);
            TextView harvestBy = (TextView) v.findViewById(R.id.harvestedBy);
            TextView name = (TextView) v.findViewById(R.id.name);
            date.setText(h.getDate());
            harvestBy.setText(h.getOwner());
            amount.setText(h.getAmount().toString());
            name.setText(h.getName());
            notes.setText(h.getNotes());
        }

        return v;
    }
}
