package com.myowncountry.mystocks.recycleview.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.dto.ShopSetting;

public class ShopSettingAdapter extends RecyclerView.Adapter<ShopSettingAdapter.ViewHolder>  {

    private ShopSetting shopSetting;
    private ArrayAdapter<CharSequence> adapter;

    public ShopSettingAdapter(ShopSetting shopSetting) {
        this.shopSetting = shopSetting;
    }

    @NonNull
    @Override
    public ShopSettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        adapter = ArrayAdapter.createFromResource(parent.getContext(),
                R.array.boolean_selector, android.R.layout.simple_spinner_item);
        LayoutInflater inflation = LayoutInflater.from(parent.getContext());
        return new ShopSettingAdapter.ViewHolder(inflation.inflate(R.layout.shop_setting_list_item, parent, false)).setAdapter(adapter);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopSettingAdapter.ViewHolder holder, int position) {
        ShopSetting.Value data = shopSetting.getValuesType().get(position);
        holder.name.setText(data.getType());
        holder.value.setText("" + data.getValue());
        if (data.getBottleCounted() != null) {
            holder.booleanSelector.setSelection(adapter.getPosition(data.getBottleCounted()));
        }

        holder.name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NOP
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //NOP
            }

            @Override
            public void afterTextChanged(Editable s) {
                data.setType(s.toString());
            }
        });
        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NOP
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //NOP
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    data.setValue(Long.parseLong(s.toString()));
                } else {
                    data.setValue(0);
                }
            }
        });
        holder.booleanSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data.setBottleCounted(adapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                holder.booleanSelector.setSelection(0);
            }
        });
    }



    @Override
    public int getItemCount() {
        return shopSetting.getValuesType().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected EditText name, value;
        protected Spinner booleanSelector;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shop_setting_list_item_type);
            value = itemView.findViewById(R.id.shop_setting_list_item_value);
            booleanSelector = itemView.findViewById(R.id.shop_setting_list_item_is_bottle_count_added);
        }

        public ViewHolder setAdapter(ArrayAdapter<CharSequence> adapter) {
            booleanSelector.setAdapter(adapter);
            return this;
        }
    }
}
