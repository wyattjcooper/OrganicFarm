package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import android.widget.Toolbar;
import android.widget.CheckBox;
import com.kaylaflaten.organicfarm.Entry;
import android.util.Log;
import java.util.List;

/**
 * Created by WyattCooper on 3/1/16.
 */
public class DatabaseCtrl {
    private static String REFNAME = "https://dazzling-inferno-9759.firebaseio.com/";
    // Parameterized constructor to set the android context
    public DatabaseCtrl(Context context) {
        Firebase.setAndroidContext(context);
    }

    private Firebase createReferenceFromLocationList(String[] location) {
        Firebase ref = new Firebase(REFNAME);
        for (String child : location) {
            ref = ref.child(child);
        }
        return ref;
    }

    // Populates an array adapter with crop names and creates a key list with their key
    public ArrayList<String> generateKeysListFromLocation(String[] location, final ArrayAdapter<String> aa) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = createReferenceFromLocationList(location);
        // Attach crops already in the database to our list
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry cropEntry = postSnapshot.getValue(Entry.class);
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key)) {
                        // Add key to keys list
                        keys.add(key);
                        // Add name to the list by adding it to the ArrayAdapter
                        aa.add(cropEntry.getName());
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return keys;
    }


    // Populates an array adapter with crop names and creates a key list with their key
    public ArrayList<String> addHarvestsToHarvestAdapter(String[] location, final HarvestAdapter ha) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = createReferenceFromLocationList(location);
        // Attach crops already in the database to our list
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest object = (Harvest) postSnapshot.getValue(Harvest.class);
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key)) {
                        // Add key to keys list
                        keys.add(key);
                        // Add name to the list by adding it to the ArrayAdapter
                        ha.add(object);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return keys;
    }

    // Populates an array adapter with crop names and creates a key list with their key
    public ArrayList<String> addEntriesToEntryAdapter(String[] location, final CropHistoryAdapter ca) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = createReferenceFromLocationList(location);
        // Attach crops already in the database to our list
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry object = (Entry) postSnapshot.getValue(Entry.class);
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key)) {
                        // Add key to keys list
                        keys.add(key);
                        // Add name to the list by adding it to the ArrayAdapter
                        ca.add(object);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return keys;
    }

    // Populates an array adapter with crop names and creates a key list with their key
    public ArrayList<String> addCropNamesToArrayAdapter(String[] location, final ArrayAdapter aa) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = createReferenceFromLocationList(location);
        // Attach crops already in the database to our list
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    String name = (String) postSnapshot.getKey();
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key)) {
                        // Add key to keys list
                        keys.add(key);
                        // Add name to the list by adding it to the ArrayAdapter
                        aa.add(name);
                    }
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
    public void listenAndSetEditText(String[] location, final EditText et, String child, final String defaultVal) {
        Firebase reference = createReferenceFromLocationList(location);
        reference.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    et.setText(defaultVal);
                } else {
                    String data = snapshot.getValue().toString();
                    et.setText(data.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetText(String[] location, final TextView et, String child, final String defaultVal) {
        Firebase reference = createReferenceFromLocationList(location);
        reference.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    et.setText(defaultVal);
                } else {
                    String data = snapshot.getValue().toString();
                    et.setText(data.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToAmountHarvested(String[] location, final TextView et, String child, final String defaultVal) {
        final double[] amount = {0.0};
        Firebase reference = createReferenceFromLocationList(location);
        reference.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest harvest = postSnapshot.getValue(Harvest.class);
                    amount[0] = amount[0] + harvest.getAmount();
                }
                et.setText(amount[0] + "lbs");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public <T> String pushObjectReturnKey(String[] location, T object ) {
        Firebase reference = createReferenceFromLocationList(location);
        Firebase pushRef = reference.push();
        pushRef.setValue(object);
        return pushRef.getKey();
    }

    public <T> void  setValueAtLocation(String[] location, T object) {
        Firebase reference = createReferenceFromLocationList(location);
        reference.setValue(object);
    }

    public void removeValueFromLocation(String[] location) {
        Firebase reference = createReferenceFromLocationList(location);
        reference.removeValue();

    }
}