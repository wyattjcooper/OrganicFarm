package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by WyattCooper on 3/25/16.
 */
public class CropHistoryAdapter extends ArrayAdapter<Entry> {
    // declaring our ArrayList of items
    private ArrayList<Entry> entries;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public CropHistoryAdapter(Context context, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, textViewResourceId, objects);
        this.entries = objects;
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
            v = inflater.inflate(R.layout.crophistory_in_list, null);
        }

        /*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Entry e = entries.get(position);

        if (e != null) {
            TextView date = (TextView) v.findViewById(R.id.cropHistoryDate);
            TextView notes = (TextView) v.findViewById(R.id.cropHistoryNotes);
            date.setText(e.getName()+" planted on " + e.getDate().toString());
            notes.setText("Note: "+e.getNotes());



        }

        // the view must be returned to our activity
        return v;
    }
}
