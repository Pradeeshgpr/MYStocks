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
    private boolean given;

    public ShopTransactionDetailsUpdateAdapter(List<ShopTransactionUpdateDTO> data) {
        this.data = data;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflation = LayoutInflater.from(parent.getContext());
        return new ShopTransactionDetailsUpdateAdapter.ViewHolder(inflation.inflate(R.layout.shop_transaction_details_update_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopTransactionUpdateDTO in = data.get(position);
        holder.type.setText(in.getType());
        holder.value.setText(Long.toString(in.getQty()));
        if (!given) {
            holder.multiple.setVisibility(View.INVISIBLE);
        } else {
            holder.multiple.setVisibility(View.VISIBLE);
        }
        holder.multiple.setText("X" + in.getValue());

        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    in.setQty(Long.parseLong(s.toString()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView type, multiple;
        public EditText value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            multiple = itemView.findViewById(R.id.shop_transaction_details_update_list_item_type_value);
            type = itemView.findViewById(R.id.shop_transaction_details_update_list_item_type);
            value = itemView.findViewById(R.id.shop_transaction_details_update_list_item_value);
        }
    }
}
