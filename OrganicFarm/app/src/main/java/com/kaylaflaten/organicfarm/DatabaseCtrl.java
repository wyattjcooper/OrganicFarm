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

import com.google.android.gms.auth.api.Auth;
import com.kaylaflaten.organicfarm.Entry;
import android.util.Log;
import com.firebase.client.AuthData;
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
    public ArrayList<String> addHarvestsOfSpecificCropToHarvestAdapter(final String pid1, final HarvestAdapter ha) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = new Firebase(REFNAME);
        // Attach crops already in the database to our list
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest object = (Harvest) postSnapshot.getValue(Harvest.class);
                    Log.println(1, "MyApp", "Reading harvests");
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key) && (object != null) && (pid1.equals(object.getPID()))) {
                        Log.println(1, "MyApp", "The read succeeded");

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
    public ArrayList<String> addHarvestsOfBedToHarvestAdapter(final int section, final int bed, final HarvestAdapter ha) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = new Firebase(REFNAME);
        // Attach crops already in the database to our list
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest object = (Harvest) postSnapshot.getValue(Harvest.class);
                    Log.println(1, "MyApp", "Reading harvests");
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key) && (object != null) && (((int) object.getSection() == section) && ((int) object.getBed() == bed))) {
                        Log.println(1, "MyApp", "The read succeeded");

                        // Add key to keys list
                        int currLength = keys.size();
                        keys.add(currLength, key);
                        // Add name to the list by adding it to the ArrayAdapter
                        ha.insert(object, currLength);

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
    public ArrayList<String> addHarvestsOfCropNameToHarvestAdapter(final String name1, final HarvestAdapter ha) {
        final ArrayList<String> keys = new ArrayList<String>();
        Firebase reference = new Firebase(REFNAME);
        // Attach crops already in the database to our list
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest object = (Harvest) postSnapshot.getValue(Harvest.class);
                    Log.println(1, "MyApp", "Reading harvests");
                    // Fetch the key from the database
                    String key = postSnapshot.getKey();
                    if (!keys.contains(key) && (name1.equals(object.getName()))) {
                        Log.println(1, "MyApp", "The read succeeded");

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
    public ArrayList<String> addEntriesToEntryAdapterBySectionAndBedHistorically(String[] location, final CropHistoryAdapter ca, final int section, final int bed) {
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
                    if (!keys.contains(key) && (object.getSection() == section) && (object.getBed() == bed) && (object.getFinished())) {
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
    public ArrayList<String> addEntriesToEntryAdapterByNameHistorically(String[] location, final CropHistoryByNameAdapter ca, final int section, final int bed) {
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
                    if (!keys.contains(key) && (object.getSection() == section) && (object.getBed() == bed) && (object.getFinished())) {
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

    public void listenAndSetTextToAmountOfSpecificCropHarvested(final TextView et, final String pid1) {
        final String[] parentID = {""};
        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final double[] amount = {0.0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest harvest = postSnapshot.getValue(Harvest.class);
                    //parentID[0] = harvest.getPID();
                    //amount[0] = amount[0] + harvest.getAmount();
                    if (harvest.getPID()!=null && harvest.getPID().equals(pid1)) {
                        Log.println(1, "MyApp", "Read succeeded");

                        amount[0] = amount[0] + harvest.getAmount();
                    }
                }
                et.setText(amount[0] + "lbs");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToAmountOfCropNameHarvested(final TextView et, final String name1) {

        final String[] parentID = {""};
        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final double[] amount = {0.0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest harvest = postSnapshot.getValue(Harvest.class);
                    //parentID[0] = harvest.getPID();
                    //amount[0] = amount[0] + harvest.getAmount();
                    if (harvest.getName()!=null && harvest.getName().equals(name1)) {
                        Log.println(1, "MyApp", "Read succeeded");

                        amount[0] = amount[0] + harvest.getAmount();
                    }
                }
                et.setText(amount[0] + "lbs");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToAmountInSectionAndBedHistorically(final TextView et, final int secN, final int bedN) {

        final String[] parentID = {""};
        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final double[] amount = {0.0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest entry = postSnapshot.getValue(Harvest.class);
                    //parentID[0] = harvest.getPID();
                    //amount[0] = amount[0] + harvest.getAmount();
                    if ((entry.getBed() == bedN) && (entry.getSection() == secN)) {
                        Log.println(1, "MyApp", "Read succeeded");

                        amount[0] = amount[0] + entry.getAmount();
                    }
                }
                et.setText(amount[0] + "lbs");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToTotalAmount(final TextView et) {

        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final double[] amount = {0.0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest harvest = postSnapshot.getValue(Harvest.class);
                    //parentID[0] = harvest.getPID();
                    //amount[0] = amount[0] + harvest.getAmount();
                    amount[0] = amount[0] + harvest.getAmount();
                }
                et.setText((int) amount[0] + " lbs");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToTotalNumberOfHarvests(final TextView et) {

        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("Harvest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final int[] amount = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    amount[0] += 1;
                }
                et.setText(amount[0] + " Harvests");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToTotalNumberOfCropsCurrentlyPlanted(final TextView et) {

        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("All Crops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final int[] amount = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry currCrop = (Entry) postSnapshot.getValue(Entry.class);
                    if (currCrop.getFinished() == false) {
                        amount[0] += 1;
                    }
                }
                et.setText(amount[0] + " Crops");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToTotalNumberOfCropsEverPlanted(final TextView et) {

        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("All Crops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final int[] amount = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry currCrop = (Entry) postSnapshot.getValue(Entry.class);
                    amount[0] += 1;

                }
                et.setText(amount[0] + " Crops");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetTextToTotalNumberOfCropsHistoricallyInSectionBed(final TextView et, final int secN, final int bedN) {

        Log.println(1, "MyApp", "Called");
        Firebase reference = new Firebase(REFNAME);
        reference.child("All Crops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final int[] amount = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Entry currCrop = (Entry) postSnapshot.getValue(Entry.class);
                    if (currCrop.getFinished() == true && (currCrop.getSection() == secN) && (currCrop.getBed() == bedN)) {
                        amount[0] += 1;
                    }
                }
                et.setText(amount[0] + " Crops");
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

    public void deleteHarvests(final String cropID) {
        Firebase reference = new Firebase(REFNAME);
        reference = reference.child("Harvest");
        final Firebase finalReference = reference;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final int[] amount = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest currHarvest = postSnapshot.getValue(Harvest.class);
                    if (currHarvest.getPID() != null && currHarvest.getPID().equals(cropID)) {
                        finalReference.child(postSnapshot.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });



    }

    public void deleteHarvestsFromAllActivities(final String cropID) {
        Firebase reference = new Firebase(REFNAME);
        reference = reference.child("All Activites");
        final Firebase finalReference = reference;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final int[] amount = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // Fetch the object from the database
                    Harvest currHarvest = postSnapshot.getValue(Harvest.class);
                    if (currHarvest.getPID().equals(cropID)) {
                        finalReference.child(postSnapshot.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void listenAndSetToUsername(final TextView text){
        Firebase ref = new Firebase(REFNAME);
        if (ref.getAuth()!=null) {
            ref = ref.child("Users").child(ref.getAuth().getUid());
        }
        final Firebase finalRef = ref;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("firstName").getValue()!=null) {
                    String user = dataSnapshot.child("firstName").getValue().toString();
                    text.setText(user);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public String getUID() {
        Firebase ref = new Firebase(REFNAME);
        String uid = "";
        if (ref.getAuth()!=null) {
            uid = ref.getAuth().getUid();
        }
        return uid;
    }

    public void logout() {
        Firebase ref = new Firebase(REFNAME);
        ref.unauth();
    }
}