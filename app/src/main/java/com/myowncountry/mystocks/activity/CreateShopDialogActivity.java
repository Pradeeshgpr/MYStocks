package com.myowncountry.mystocks.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.dto.ShopDetails;

import java.util.function.Consumer;
import java.util.function.Function;

public class CreateShopDialogActivity extends Dialog {

    private Consumer<ShopDetails> callback;
    private EditText shopName, shopAddress;
    private Button create, cancel;

    public CreateShopDialogActivity(@NonNull Context context, Consumer<ShopDetails> callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_shop_dialog);

        shopAddress = findViewById(R.id.create_shop_dialog_shop_address);
        shopName = findViewById(R.id.create_shop_dialog_shop_name);

        create = findViewById(R.id.create_shop_dialog_create_bt);
        cancel = findViewById(R.id.create_shop_dialog_cancel_bt);

        cancel.setOnClickListener(v -> this.cancel());
        create.setOnClickListener(v -> createShop());
    }

    private void createShop() {
        ShopDetails shopDetails = new ShopDetails();
        shopDetails.setOutstandingAmount(0);
        shopDetails.setOutstandingBottles(0);
        shopDetails.setAddress(shopAddress.getText().toString());
        shopDetails.setName(shopName.getText().toString());
        callback.accept(shopDetails);
        this.cancel();
    }
}
