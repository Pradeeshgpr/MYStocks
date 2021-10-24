package com.myowncountry.mystocks.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopSetting;
import com.myowncountry.mystocks.recycleview.adapter.ShopSettingAdapter;
import com.myowncountry.mystocks.service.ShopService;
import com.myowncountry.mystocks.util.VerticalSpacingItemDecorator;


public class ShopSettings extends AppCompatActivity {

    private LinearLayout relativeLayout;
    private RecyclerView shopSettingRC;
    private Button addNewBT, saveBT;
    private ShopSettingAdapter shopSettingAdapter;
    private ShopSetting shopSettings = new ShopSetting();
    private AlertDialog.Builder alertDialogBuilder;
    private ShopService shopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_settings);

        initObjects();

        initItems();
    }

    private void initItems() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        shopSettingAdapter = new ShopSettingAdapter(shopSettings);
        alertDialogBuilder = new AlertDialog.Builder(this);
        shopService = ShopService.getInstance();

        shopService.getData().addOnSuccessListener(v-> {
            ShopSetting data = v.toObject(ShopSetting.class);
            if (data != null && data.getValuesType() != null && !data.getValuesType().isEmpty()) {
                shopSettings.getValuesType().addAll(data.getValuesType());
                shopSettingAdapter.notifyDataSetChanged();
            }
        });


        shopSettingRC.addItemDecoration(new VerticalSpacingItemDecorator(10));
        shopSettingRC.setAdapter(shopSettingAdapter);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(shopSettingRC);
        shopSettingRC.setHasFixedSize(true);
        shopSettingRC.setLayoutManager(new LinearLayoutManager(this));

        addNewBT.setOnClickListener(v -> {
            shopSettings.getValuesType().add(GenericsConstants.SHOP_SETTING_DEFAULT_VALUE);
            shopSettingAdapter.notifyDataSetChanged();
        });

        saveBT.setOnClickListener(v -> saveAction());

    }

    private void saveAction() {
        alertDialogBuilder.setTitle("Save")
                .setMessage("Do you want to save changes")
                .setPositiveButton("Yes", (dialog, which) -> {
                    shopService.updateData(shopSettings).addOnSuccessListener( v -> {
                        Snackbar.make(relativeLayout, "Save success", Snackbar.LENGTH_SHORT).show();
                    });
                }).setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
            shopSettingAdapter.notifyDataSetChanged();
        }).create().show();
    }

    private void initObjects() {
        relativeLayout = findViewById(R.id.shop_setting_activity_layout);
        shopSettingRC = findViewById(R.id.rc_shop_setting_values);
        addNewBT = findViewById(R.id.activity_shop_setting_add_new);
        saveBT = findViewById(R.id.activity_shop_setting_save);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            alertDialogBuilder.setTitle("Delete")
                    .setMessage("Do you want to delete the?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        shopSettings.getValuesType().remove(position);
                        shopSettingAdapter.notifyDataSetChanged();
                    }).setNegativeButton("No", (dialog, which) -> {
                        dialog.cancel();
                        shopSettingAdapter.notifyDataSetChanged();
                    }).create().show();
        }
    };
}