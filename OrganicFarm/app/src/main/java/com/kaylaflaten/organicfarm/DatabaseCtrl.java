package com.kaylaflaten.organicfarm;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import com.kaylaflaten.organicfarm.Entry;

import java.util.List;

/**
 * Created by WyattCooper on 3/1/16.
 */
public class DatabaseCtrl {
    private Firebase ref;
    private Firebase bedRef;
    private Firebase entryRef;

    // Parameterized constructor to set the Firebase reference
    public DatabaseCtrl(String child1, String child2, Context context) {
        Firebase.setAndroidContext(context);
        ref = new Firebase("https://dazzling-inferno-9759.firebaseio.com/");
        bedRef = ref.child(child1).child(child2);
    }

    public DatabaseCtrl() {

    }

    public Firebase getRef() {
        return ref;
    }

    public Firebase getBedRef() {
        return bedRef;
    }

    public void setEntryRef(String ID) {
        if (ID == null) {
            entryRef = bedRef;
        }
        else {
            entryRef = bedRef.child(ID);
        }
        return;
    }

    // Populates an array adapter with crop names and creates a key list with their key 
    public ArrayList<String> generateKeysList(final ArrayAdapter<String> aa) {
        final ArrayList<String> keys = new ArrayList<String>();
        // Attach crops already in the database to our list
        bedRef.addValueEventListener(new ValueEventListener() {
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

    public String pushEntryReturnKey(Entry entry) {
        Firebase pushRef = entryRef.push();
        pushRef.setValue(entry);
        return pushRef.getKey();
    }

    public void setValueEntry(Entry entry) {
        entryRef.setValue(entry);
    }

    public void removeValueEntry() {
        entryRef.removeValue();
    }


}