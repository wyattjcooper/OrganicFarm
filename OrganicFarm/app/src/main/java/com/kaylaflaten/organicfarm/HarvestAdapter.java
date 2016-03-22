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
 * Created by WyattCooper on 3/21/16.
 * Code partially taken from https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 */
public class HarvestAdapter extends ArrayAdapter<Harvest> {
    // declaring our ArrayList of items
    private ArrayList<Harvest> harvests;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public HarvestAdapter(Context context, int textViewResourceId, ArrayList<Harvest> objects) {
        super(context, textViewResourceId, objects);
        this.harvests = objects;
    }

    /*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view we are converting to a local variable
        View v = convertView;



        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.harvest_in_list, null);
        }

        /*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Harvest h = harvests.get(position);

        if (h != null) {

            TextView date = (TextView) v.findViewById(R.id.DAtextView);
            TextView amount = (TextView) v.findViewById(R.id.AMtextView);
            ImageView image = (ImageView) v.findViewById(R.id.imageView);
            date.setText(h.getDate());
            amount.setText(h.getAmount().toString());
        }

        // the view must be returned to our activity
        return v;
    }
}
