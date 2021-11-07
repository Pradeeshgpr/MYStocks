package com.myowncountry.mystocks.service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopSetting;
import com.myowncountry.mystocks.firebase.db.FireBaseService;

public class ShopService {

    private static ShopService shopService = new ShopService();
    private FireBaseService fireBaseService;
    private String DEFAULT_SETTING = "default_setting";
    private static ShopSetting shopSetting;

    private ShopService() {
        fireBaseService = FireBaseService.getInstance();
    }

    protected CollectionReference getCollection() {
        return fireBaseService.getCollection(GenericsConstants.SHOP_SETTINGS_COLLECTION);
    }

    public Task<DocumentSnapshot> getData() {
        return getCollection().document(DEFAULT_SETTING).get();
    }

    public Task<Void> updateData(ShopSetting shopSettings) {
        return getCollection().document(DEFAULT_SETTING).set(shopSettings);
    }

    public void cacheData() {
        getData().addOnSuccessListener(v -> {
            shopSetting = v.toObject(ShopSetting.class);
        });
    }

    public void cacheData(ShopSetting shopSetting) {
        shopSetting = shopSetting;
    }

    public ShopSetting getCachedData() {
        if (shopSetting != null) {
            return shopSetting;
        } else {
            throw new NullPointerException();
        }
    }

    public static ShopService getInstance() {
        return shopService;
    }

}
