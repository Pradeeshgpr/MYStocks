package com.myowncountry.mystocks.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopTransactionsDTO;
import com.myowncountry.mystocks.firebase.db.FireBaseService;

public class ShopTransactionService {

    private FireBaseService fireBaseService;

    private static ShopTransactionService shopTransactionService = new ShopTransactionService();

    public ShopTransactionService() {
        this.fireBaseService = FireBaseService.getInstance();
    }

    private CollectionReference getCollection() {
        return fireBaseService.getCollection(GenericsConstants.SHOP_TRANSACTION_COLLECTION);
    }

    public Task<Void> updateData(String id, ShopTransactionsDTO data) {
        return getCollection().document(id).set(data);
    }

    public Task<DocumentSnapshot> getData(String id) {
        return getCollection().document(id).get();
    }

    public Task<Void> deleteCollection(String id) {
        return getCollection().document(id).delete();
    }

    public static ShopTransactionService getInstance() {
        return shopTransactionService;
    }

}
