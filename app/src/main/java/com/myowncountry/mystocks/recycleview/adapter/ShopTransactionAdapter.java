package com.myowncountry.mystocks.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.dto.ShopTransactionsDTO;

import java.text.SimpleDateFormat;

public class ShopTransactionAdapter extends RecyclerView.Adapter<ShopTransactionAdapter.ViewHolder>  {

    private ShopTransactionsDTO shopTransactionsDTO;
    private SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

    public ShopTransactionAdapter(ShopTransactionsDTO shopTransactions) {
        this.shopTransactionsDTO = shopTransactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflation = LayoutInflater.from(parent.getContext());
        return new ShopTransactionAdapter.ViewHolder(inflation.inflate(R.layout.shop_transaction_details_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopTransactionsDTO.ShopTransaction data = shopTransactionsDTO.getShopTransactionList().get(position);
        holder.activityOn.setText(dateFormater.format(data.getCreatedOn()));
        holder.bottlesReceived.setText(GenericsConstants.EMPTY_STRING + data.getReceivedBottles());
        holder.bottles.setText(GenericsConstants.EMPTY_STRING + data.getBottles());
        holder.amountGiven.setText(GenericsConstants.EMPTY_STRING + data.getTotalPrice());
        holder.amountReceived.setText(GenericsConstants.EMPTY_STRING + data.getReceivedAmount());
    }

    @Override
    public int getItemCount() {
        return shopTransactionsDTO.getShopTransactionList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bottlesReceived, amountReceived, bottles, amountGiven, activityOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bottles = itemView.findViewById(R.id.shop_transaction_details_activity_list_item_given_bottle);
            amountGiven = itemView.findViewById(R.id.shop_transaction_details_activity_list_item_given_amount);
            activityOn = itemView.findViewById(R.id.shop_transaction_details_activity_list_item_date);
            bottlesReceived = itemView.findViewById(R.id.shop_transaction_details_activity_list_item_received_bottle);
            amountReceived = itemView.findViewById(R.id.shop_transaction_details_activity_list_item_received_amount);
        }
    }

}
