package com.myowncountry.mystocks.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopTransactionsDTO;
import com.myowncountry.mystocks.recycleview.adapter.ShopTransactionAdapter;
import com.myowncountry.mystocks.recycleview.model.ShopDetailsRV;
import com.myowncountry.mystocks.recycleview.model.ShopTransactionUpdateDTO;
import com.myowncountry.mystocks.service.ShopService;
import com.myowncountry.mystocks.service.ShopTransactionService;

import java.io.Serializable;
import java.util.List;

public class ShopTransactionDetails extends AppCompatActivity {

    private TextView remainingBottle, remainingAmount;
    private RecyclerView shopTransactionRV;
    private Button received, gave;

    private ShopDetailsRV shopDetails;
    private ShopTransactionService shopTransactionService;
    private ShopTransactionsDTO shopTransactionsDTO;
    private ShopTransactionAdapter shopTransactionAdapter;
    private ShopTransactionDetailsUpdateTransactionDialog shopTransactionDetailsUpdateTransactionDialog;
    private ShopService shopService;

    private Operation state = Operation.IN_VALID;

    private enum Operation {
        IN_VALID, RECEIVED, GIVEN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_transaction_details);
        shopDetails = (ShopDetailsRV)getIntent().getSerializableExtra(GenericsConstants.SHOP_DETAILS);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(shopDetails.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initObjects();

        initData();
    }

    private void initObjects() {
        remainingAmount = findViewById(R.id.shop_transaction_details_remaining_amount);
        remainingBottle = findViewById(R.id.shop_transaction_details_remaining_bottle);
        shopTransactionRV = findViewById(R.id.shop_transaction_details_activity_list_view);
        received = findViewById(R.id.shop_transaction_details_received_details);
        gave = findViewById(R.id.shop_transaction_details_gave_details);


        shopTransactionService = ShopTransactionService.getInstance();
        shopTransactionsDTO = new ShopTransactionsDTO();
        shopTransactionAdapter = new ShopTransactionAdapter(shopTransactionsDTO);
        shopTransactionDetailsUpdateTransactionDialog = new ShopTransactionDetailsUpdateTransactionDialog(ShopTransactionDetails.this, v-> callback(v), v ->cancelCallback());
        shopService = ShopService.getInstance();
    }

    private void cancelCallback() {
        state = Operation.IN_VALID;
    }

    private void callback(List<ShopTransactionUpdateDTO> v) {
        if (state == Operation.GIVEN) {

        } else if (state == Operation.RECEIVED) {

        }
    }

    private void initData() {

        try {
            shopService.getCachedData();
        } catch (NullPointerException e) {
            shopService.cacheData();
        }

        shopTransactionService.getData(shopDetails.getId()).addOnSuccessListener(v -> {
            if (v.exists()) {
                shopTransactionsDTO.addAll(v.toObject(ShopTransactionsDTO.class));
            }
        });

        remainingBottle.setText(GenericsConstants.EMPTY_STRING + shopDetails.getOutstandingBottles());
        remainingAmount.setText(GenericsConstants.EMPTY_STRING + shopDetails.getOutstandingAmount());


        received.setOnClickListener(v -> {
            state = Operation.RECEIVED;
            shopTransactionDetailsUpdateTransactionDialog.show();
        });

        gave.setOnClickListener(v -> {
            state = Operation.GIVEN;
            shopTransactionDetailsUpdateTransactionDialog.show();
        });


        //Recycle view
        shopTransactionRV.setAdapter(shopTransactionAdapter);
        shopTransactionRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        shopTransactionRV.setHasFixedSize(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}