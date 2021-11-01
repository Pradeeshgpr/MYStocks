package com.myowncountry.mystocks.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopDetails;
import com.myowncountry.mystocks.firebase.db.FireBaseService;

public class ShopDetailService {

    private static final ShopDetailService shopDetailsService = new ShopDetailService();
    private FireBaseService fireBaseService;

    private ShopDetailService() {
        fireBaseService = FireBaseService.getInstance();
    }

    public Task<DocumentReference> createShopDetails(ShopDetails shopDetails) {
        return getCollection().add(shopDetails);
    }

    public Task<QuerySnapshot> getAllShopDetails() {
        return getCollection().get();
    }

    public CollectionReference getCollection() {
        return fireBaseService.getCollection(GenericsConstants.SHOPS_COLLECTION);
    }

    public Task<Void> deleteDocument(String id) {
        return getCollection().document(id).delete();
    }

    public static ShopDetailService getInstance() {
        return shopDetailsService;
    }
}
