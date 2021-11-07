package com.myowncountry.mystocks.recycleview.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.recycleview.model.ShopTransactionUpdateDTO;

import java.util.List;

public class ShopTransactionDetailsUpdateAdapter extends RecyclerView.Adapter<ShopTransactionDetailsUpdateAdapter.ViewHolder>{

    private List<ShopTransactionUpdateDTO> data;

    public ShopTransactionDetailsUpdateAdapter(List<ShopTransactionUpdateDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflation = LayoutInflater.from(parent.getContext());
        return new ShopTransactionDetailsUpdateAdapter.ViewHolder(inflation.inflate(R.layout.shop_transaction_details_update_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopTransactionUpdateDTO in = data.get(0);
        holder.type.setText(in.getType());
        holder.value.setText(Long.toString(in.getValue()));

        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                in.setValue(Long.parseLong(s.toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView type;
        public EditText value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.shop_transaction_details_update_list_item_type);
            value = itemView.findViewById(R.id.shop_transaction_details_update_list_item_value);
        }
    }
}
