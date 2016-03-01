package com.kaylaflaten.organicfarm;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.List;

/**
 * Created by WyattCooper on 3/1/16.
 */
public class FirebaseCtrl {
    private Firebase ref= new Firebase("https://dazzling-inferno-9759.firebaseio.com/");

    // Parameterized constructor to set the Firebase reference
    public FirebaseCtrl(String refName) {
        ref = new Firebase(refName);
    }


    public Firebase createNewRef(String[] childList){
        Firebase newRef = ref;
        for (int i = 0; i < childList.length; i++) {
            ref = ref.child(childList[i]);
        }
        return newRef;
    }

    public Firebase getRef() {
        return ref;
    }
}
