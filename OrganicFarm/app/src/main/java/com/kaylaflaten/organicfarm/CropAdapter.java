package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;



import java.util.ArrayList;

/**
 * Creates a list of crops
 */
public class CropAdapter extends ArrayAdapter<Entry>  {
    // declaring our ArrayList of items
    private ArrayList<Entry> crops;

    // override the constructor for ArrayAdapter
    public CropAdapter(Context context, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, textViewResourceId, objects);
        this.crops = objects;
    }

    // we are overriding the getView method here
    public View getView(int position, View convertView, ViewGroup parent){
        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, inflate it
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.crops_in_list, null);
        }

        Entry c = crops.get(position);

        if (c != null) {

            TextView cropName = (TextView) v.findViewById(R.id.cropName_in_list);
            // TextView plantedBy = (TextView) v.findViewById(R.id.plantedBy_crops_in_list);
            // TextView notes = (TextView) v.findViewById(R.id.notesView_crops_in_list);
            cropName.setText(c.getName());
            // plantedBy.setText(" Planted On " + c.getDate());
            // notes.setText("Notes: " + c.getNotes());


        }

        // the view must be returned to our activity
        return v;
    }

    private Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }
}
