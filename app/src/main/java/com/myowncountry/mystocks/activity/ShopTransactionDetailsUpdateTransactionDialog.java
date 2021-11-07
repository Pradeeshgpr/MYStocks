package com.myowncountry.mystocks.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopSetting;
import com.myowncountry.mystocks.recycleview.adapter.ShopTransactionDetailsUpdateAdapter;
import com.myowncountry.mystocks.recycleview.model.ShopTransactionUpdateDTO;
import com.myowncountry.mystocks.service.ShopService;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ShopTransactionDetailsUpdateTransactionDialog extends Dialog {


    private RecyclerView shopTransactionDetailsRC;
    private Button submit, cancel;
    private Consumer<List<ShopTransactionUpdateDTO>> callback;
    private List<ShopTransactionUpdateDTO> shopTransactionUpdateDTO = new LinkedList<>();
    private ShopService shopService;
    private Consumer<Class<Void>> cancelCallback;
    private ShopTransactionDetailsUpdateAdapter shopTransactionDetailsUpdateAdapter;

    public ShopTransactionDetailsUpdateTransactionDialog(@NonNull Context context, Consumer<List<ShopTransactionUpdateDTO>> callback, Consumer<Class<Void>> cancelCallback) {
        super(context);
        this.callback = callback;
        this.cancelCallback = cancelCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_transaction_details_update_transaction_dialog);

        initObjects();

        //init data
        initData();

        //init listener
        initListeners();
    }

    private void initData() {

        shopTransactionDetailsRC.setAdapter(shopTransactionDetailsUpdateAdapter);
        shopTransactionDetailsRC.setLayoutManager(new LinearLayoutManager(getContext()));
        shopTransactionDetailsRC.setHasFixedSize(true);

        try {
            updateList(shopService.getCachedData().getValuesType());
        } catch (NullPointerException e) {
            shopService.getData().addOnSuccessListener(v -> {
                ShopSetting shopSetting = v.toObject(ShopSetting.class);
                shopService.updateData(shopSetting);
                updateList(shopSetting.getValuesType());
            });
        }
    }

    private void updateList(List<ShopSetting.Value> values) {
        values.forEach(v -> {
            shopTransactionUpdateDTO.add(new ShopTransactionUpdateDTO(v.getType(), v.getValue(), GenericsConstants.YES.equals(v.getBottleCounted())));
        });
        shopTransactionDetailsUpdateAdapter.notifyDataSetChanged();
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        cancelCallback.accept(Void.TYPE);
    }

    private void initObjects() {
        shopTransactionDetailsRC = findViewById(R.id.shop_transaction_Details_update_transaction_updateDetails);
        submit = findViewById(R.id.shop_transaction_Details_update_transaction_submit);
        cancel = findViewById(R.id.shop_transaction_Details_update_transaction_cancel);

        shopTransactionDetailsUpdateAdapter = new ShopTransactionDetailsUpdateAdapter(shopTransactionUpdateDTO);

        shopService = ShopService.getInstance();
    }

    private void initListeners() {
        cancel.setOnClickListener(v -> cancel());
        submit.setOnClickListener(v -> {callback.accept(shopTransactionUpdateDTO); cancel();});
    }
}
