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
import android.widget.CheckBox;
import com.kaylaflaten.organicfarm.Entry;
import android.util.Log;
import java.util.List;

/**
 * Created by WyattCooper on 3/1/16.
 */
public class DatabaseCtrl {
    private Firebase ref;
    private Firebase twoChildRef;
    private Firebase entryRef;
    private Firebase oneChildRef;
    private static String REFNAME = "https://dazzling-inferno-9759.firebaseio.com/";
    // Parameterized constructor to set the Firebase reference
    public DatabaseCtrl(String child1, Context context) {
        Firebase.setAndroidContext(context);
        ref = new Firebase(REFNAME);
        oneChildRef = ref.child(child1);
    }

    // Parameterized constructor to set the Firebase reference
    public DatabaseCtrl(String child1, String child2, Context context) {
        Firebase.setAndroidContext(context);
        ref = new Firebase(REFNAME);
        twoChildRef = ref.child(child1).child(child2);
    }

    public DatabaseCtrl() {
        ref = new Firebase(REFNAME);
    }

    public Firebase getRef() {
        return ref;
    }

    private Firebase createReferenceFromLocationList(String[] location) {
        ref = new Firebase(REFNAME);
        for (String child : location) {
            ref = ref.child(child);
        }
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

    public <T> ArrayList<String> fillObjectListReturnKeys(String[] location, final ArrayList<T> OBJECTLIST) {
        Firebase reference = createReferenceFromLocationList(location);
        final ArrayList<String> keys = new ArrayList<String>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    T object = (T) postSnapshot.getValue();
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key)) {
                        // Add key to keys list
                        keys.add(key);
                        // Add name to the list by adding it to the ArrayAdapter
                        OBJECTLIST.add(object);
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

//    public String returnObjectAtLocation(String[] location) {
//        Firebase reference = createReferenceFromLocationList(location);
//        final String[] data = {""};
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                    data[0] =  snapshot.getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError error) {
//            }
//        });
//        return data[0];
//    }

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



    public String pushEntryReturnKey(Entry entry) {
        Firebase pushRef = entryRef.push();
        pushRef.setValue(entry);
        return pushRef.getKey();
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


    public void setValueEntry(Entry entry) {
        entryRef.setValue(entry);
    }
    public void setValueHarvest(Harvest harvest) { entryRef.setValue(harvest);}

    public void removeValueEntry() {
        entryRef.removeValue();
    }

//    public <T> ArrayList<T> returnValueAtLocation(String[] location, final String child, final ArrayList<T> returnList) {
//        Firebase reference = createReferenceFromLocationList(location);
//        Log.d("MyApp", "The reference is: " + reference.child(child).toString());
//        reference.child(child);
//        reference.child(child).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if (snapshot.getValue() == null) {
//
//                } else {
//                    returnList.set(0, (T) snapshot.getValue());
//                    Log.d("MyApp", "Value is: " + snapshot.getValue().toString());
//                    Log.d("MyApp", "Value is: " + returnList.get(0));
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d("MyApp", "The read failed: " + firebaseError.getMessage());
//            }
//        });
//        return returnList;
//    }

//    public Entry returnEntryAtLocation(String section, String bed, String key) {
//        Firebase eRef = ref.child(section).child(bed).child(key);
//        Entry returnEntry = new Entry("NULL","NULL","NULL");
//        eRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Entry data = snapshot.getValue(Entry.class);
//                if (data == null) {
//
//                } else {
//                    Entry returnEntry = data;
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError error) {
//            }
//        });
//        return returnEntry;
//    }
//
//    public Harvest returnHarvestAtLocation(String key) {
//        Firebase hRef = ref.child("Harvest").child(key);
//        final Harvest[] returnHarvest = {new Harvest()};
//        hRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Harvest data = (Harvest) snapshot.getValue(Harvest.class);
//                if (data == null) {
//
//                } else {
//                    returnHarvest[0] = data;
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError error) {
//            }
//        });
//        return returnHarvest[0];
//    }


}