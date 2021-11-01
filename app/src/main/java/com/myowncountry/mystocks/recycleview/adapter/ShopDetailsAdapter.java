package com.myowncountry.mystocks.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.animation.textdrawable.TextDrawable;
import com.myowncountry.mystocks.animation.textdrawable.util.ColorGenerator;
import com.myowncountry.mystocks.recycleview.model.ShopDetailsRV;

import java.util.List;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.ViewHolder>  {

    private List<ShopDetailsRV> shopDetailsRVList;
    private View.OnClickListener clickListener;

    public ShopDetailsAdapter(List<ShopDetailsRV> shopDetailsRVList, View.OnClickListener clickListener) {
        this.shopDetailsRVList = shopDetailsRVList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflation = LayoutInflater.from(parent.getContext());
        View view = inflation.inflate(R.layout.shop_details_list_item, parent, false);
        view.setOnClickListener(v -> clickListener.onClick(v));
        return new ShopDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopDetailsRV shop = shopDetailsRVList.get(position);
        if (shop.getImage() == null) {
            shop.setImage(TextDrawable.builder().buildRoundRect("" + shop.getName().charAt(0),  ColorGenerator.MATERIAL.getRandomColor(), 10));
        }
        holder.shopNameImage.setImageDrawable(shop.getImage());
        holder.shopName.setText(shop.getName());
        holder.shopAddress.setText(shop.getAddress());
    }

    @Override
    public int getItemCount() {
        return shopDetailsRVList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView shopNameImage;
        public TextView shopName, shopAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameImage = itemView.findViewById(R.id.shop_details_list_item_image);
            shopName = itemView.findViewById(R.id.shop_details_list_item_name);
            shopAddress = itemView.findViewById(R.id.shop_details_list_item_address);
        }
    }

}
