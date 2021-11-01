package com.myowncountry.mystocks.constants;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.myowncountry.mystocks.dto.ShopSetting;

public class GenericsConstants {

    public static final String APPLICATION_NAME = "MyStocks";

    //intent constant
    public static final String SHOP_DETAILS = "shop_details";


    //collections
    public static final String USER_COLLECTION = "users";
    public static final String SHOPS_COLLECTION = "shops";
    public static final String SHOP_SETTINGS_COLLECTION = "shop_setting";
    public static final String SHOP_TRANSACTION_COLLECTION = "shop_transaction";

    public static final ShopSetting.Value SHOP_SETTING_DEFAULT_VALUE = new ShopSetting.Value();


    public static final String EMPTY_STRING = "";


    //max transaction
    public static final int MAX_TRANSACTION_PER_SHOP = 3000;
}
