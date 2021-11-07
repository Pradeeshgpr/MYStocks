package com.myowncountry.mystocks.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.myowncountry.mystocks.R;

public class ShopTransactionDetailsUpdateTransactionDialog extends Dialog {

    public ShopTransactionDetailsUpdateTransactionDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_transaction_details_update_transaction_dialog);
    }
}
