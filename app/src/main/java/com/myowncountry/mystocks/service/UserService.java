package com.myowncountry.mystocks.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.QuerySnapshot;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.User;
import com.myowncountry.mystocks.firebase.db.FireBaseService;

public class UserService {

    private static UserService userService;
    private static FireBaseService fireBaseService;

    public static final User DEFAULT_USER = new User();

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
            fireBaseService = FireBaseService.getInstance();
            DEFAULT_USER.setActive(true);
            DEFAULT_USER.setAdmin(false);
        }
        return userService;
    }

    public Task<QuerySnapshot> getUser(CollectionReference collection, String email) {
        return collection.whereEqualTo(FieldPath.documentId(), email).get();
    }

    public CollectionReference getCollection() {
        return fireBaseService.getCollection(GenericsConstants.USER_COLLECTION);
    }

    public Task<Void> deleteUser(String email) {
        return getCollection().document(email).delete();
    }

    public Task<QuerySnapshot> getAllUsers(CollectionReference collectionReference) {
        return collectionReference.get();
    }

    public Task<Void> createUser(String email) {
        return getCollection().document(email).set(DEFAULT_USER);
    }
}
