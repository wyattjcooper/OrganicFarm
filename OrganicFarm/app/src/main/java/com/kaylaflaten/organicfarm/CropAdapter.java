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
 * Created by WyattCooper on 4/23/16.
 */
public class CropAdapter extends ArrayAdapter<Entry>  {
    // declaring our ArrayList of items
    private ArrayList<Entry> crops;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public CropAdapter(Context context, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, textViewResourceId, objects);
        this.crops = objects;
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
            v = inflater.inflate(R.layout.crops_in_list, null);
        }

        /*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Entry c = crops.get(position);

        if (c != null) {

            TextView cropName = (TextView) v.findViewById(R.id.cropName_in_list);
            TextView plantedBy = (TextView) v.findViewById(R.id.plantedBy_crops_in_list);
            TextView notes = (TextView) v.findViewById(R.id.notesView_crops_in_list);
            cropName.setText(c.getName());
            plantedBy.setText(" Planted On " + c.getDate());
            notes.setText("Notes: " + c.getNotes());


            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(stringToDate(c.getHarvestDate().toString(), "mm/dd/yyyy"));

            Calendar today = Calendar.getInstance();

            long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);

            v.setBackgroundColor((int) days);

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
