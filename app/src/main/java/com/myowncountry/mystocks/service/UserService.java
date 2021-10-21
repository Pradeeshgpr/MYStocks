package com.myowncountry.mystocks.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.QuerySnapshot;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.firebase.db.FireBaseService;

public class UserService {

    private static UserService userService;
    private static FireBaseService fireBaseService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
            fireBaseService = FireBaseService.getInstance();
        }
        return userService;
    }

    public Task<QuerySnapshot> getUser(CollectionReference collection, String email) {
        return collection.whereEqualTo(FieldPath.documentId(), email).get();
    }

    public CollectionReference getCollection() {
        return fireBaseService.getCollection(GenericsConstants.USER_COLLECTION);
    }

    public Task<QuerySnapshot> deleteUser(String email) {
        return null;
    }

    public Task<QuerySnapshot> getAllUsers(CollectionReference collectionReference) {
        return collectionReference.get();
    }
}
