package com.myowncountry.mystocks.firebase.db;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseService {

    public static FireBaseService fireBaseService = null;
    public static FirebaseFirestore firebaseFirestore;

    public void init(Context context) {
        FirebaseApp.initializeApp(context);
        getFirebaseFireStore();
    }

    public CollectionReference getCollection(String collectionName) {
        return firebaseFirestore.collection(collectionName);
    }

    public FirebaseFirestore getFirebaseFireStore() {
        if (firebaseFirestore == null) {
            firebaseFirestore = FirebaseFirestore.getInstance();
        }
        return firebaseFirestore;
    }



    public static FireBaseService getInstance() {
        if (fireBaseService == null) {
            fireBaseService = new FireBaseService();
        }
        return fireBaseService;
    }

}
