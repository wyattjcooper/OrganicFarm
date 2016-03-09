package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import android.widget.CheckBox;
import com.kaylaflaten.organicfarm.Entry;

import java.util.List;

/**
 * Created by WyattCooper on 3/1/16.
 */
public class DatabaseCtrl {
    private Firebase ref;
    private Firebase twoChildRef;
    private Firebase entryRef;
    private Firebase oneChildRef;

    // Parameterized constructor to set the Firebase reference
    public DatabaseCtrl(String child1, Context context) {
        Firebase.setAndroidContext(context);
        ref = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");
        oneChildRef = ref.child(child1);
    }

    // Parameterized constructor to set the Firebase reference
    public DatabaseCtrl(String child1, String child2, Context context) {
        Firebase.setAndroidContext(context);
        ref = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");
        twoChildRef = ref.child(child1).child(child2);
    }

    public DatabaseCtrl() {
        ref = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");
    }

    public Firebase getRef() {
        return ref;
    }

    public void setOneChildRef(String child1) { oneChildRef = ref.child(child1); }
    public void setTwoChildRef(String child1, String child2) { twoChildRef = ref.child(child1).child(child2); }

    public Firebase getBedRef() {
        return twoChildRef;
    }

    public void setEntryRef(String ID, int numChildren) {
        if (numChildren == 2) {
            if (ID == null) {
                entryRef = twoChildRef;
            } else {
                entryRef = twoChildRef.child(ID);
            }
        }
        else if (numChildren == 1) {
            if (ID == null) {
                entryRef = oneChildRef;
            } else {
                entryRef = oneChildRef.child(ID);
            }
        }
        return;
    }

    // Populates an array adapter with crop names and creates a key list with their key 
    public ArrayList<String> generateKeysList(final ArrayAdapter<String> aa) {
        final ArrayList<String> keys = new ArrayList<String>();
        // Attach crops already in the database to our list
        twoChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry cropEntry = postSnapshot.getValue(Entry.class);
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    // Add key to keys list
                    keys.add(key);
                    // Add name to the list by adding it to the ArrayAdapter
                    aa.add(cropEntry.getName());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return keys;
    }

    // Input: an EditText field to be modified, a value to listen for changes to, and a default string
    // that the EditText will have if no changes are found
    public void listenAndSetEditText(final EditText et, String child, final String defaultVal) {
        entryRef.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String data = (String) snapshot.getValue();
                if (data == null) {
                    et.setText(defaultVal);
                } else {
                    et.setText(data.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    // Input: an EditText field to be modified, a value to listen for changes to, and a default string
    // that the EditText will have if no changes are found
    public void listenAndSetCheckBox(final CheckBox cb, String child) {
        entryRef.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean data = (boolean) snapshot.getValue();
                cb.setActivated(data);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }



    public String pushEntryReturnKey(Entry entry) {
        Firebase pushRef = entryRef.push();
        pushRef.setValue(entry);
        return pushRef.getKey();
    }


    public void setValueEntry(Entry entry) {
        entryRef.setValue(entry);
    }
    public void setValueHarvest(Harvest harvest) { entryRef.setValue(harvest);}

    public void removeValueEntry() {
        entryRef.removeValue();
    }

    public Entry returnEntryAtLocation(String section, String bed, String key) {
        Firebase eRef = ref.child(section).child(bed).child(key);
        final Entry returnEntry = new Entry();
        eRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Entry data = (Entry) snapshot.getValue(Entry.class);
                if (data == null) {

                } else {
                    returnEntry.setDate(data.getDate());
                    returnEntry.setName(data.getName());
                    returnEntry.setNotes(data.getNotes());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
        return returnEntry;
    }

    public Harvest returnHarvestAtLocation(String key) {
        Firebase hRef = ref.child("Harvest").child(key);
        final Harvest returnHarvest = new Harvest();
        hRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Harvest data = (Harvest) snapshot.getValue(Harvest.class);
                if (data == null) {

                } else {
                    returnHarvest.setDate(data.getDate());
                    returnHarvest.setFinished(data.getFinished());
                    returnHarvest.setNotes(data.getNotes());
                    returnHarvest.setSection(data.getSection());
                    returnHarvest.setBed(data.getBed());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
        return returnHarvest;
    }


}