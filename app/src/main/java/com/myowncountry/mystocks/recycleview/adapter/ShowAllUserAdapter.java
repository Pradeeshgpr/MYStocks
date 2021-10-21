package com.myowncountry.mystocks.recycleview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.animation.textdrawable.TextDrawable;
import com.myowncountry.mystocks.animation.textdrawable.util.ColorGenerator;
import com.myowncountry.mystocks.dto.User;
import com.myowncountry.mystocks.firebase.db.FireBaseService;
import com.myowncountry.mystocks.recycleview.model.ShowAllUserModel;
import com.myowncountry.mystocks.service.UserService;

import java.util.List;

public class ShowAllUserAdapter extends RecyclerView.Adapter<ShowAllUserAdapter.ViewHolder> {

    private List<ShowAllUserModel> showAllUserModel;
    private UserService userService;

    public ShowAllUserAdapter(List<ShowAllUserModel> showAllUserModel) {
        this.showAllUserModel = showAllUserModel;
        this.userService = UserService.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflation = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflation.inflate(R.layout.show_all_user_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowAllUserModel data = this.showAllUserModel.get(position);
        holder.emailTextView.setText(data.getEmailId());
        if (data.getIconColor() == null) {
            data.setIconColor(TextDrawable.builder().buildRoundRect("" + data.getEmailId().charAt(0), ColorGenerator.MATERIAL.getRandomColor(), 10));
        }
        holder.icon.setImageDrawable(data.getIconColor());
    }

    @Override
    public int getItemCount() {
        return showAllUserModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView emailTextView;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.show_all_user_list_item_email);
            icon = itemView.findViewById(R.id.show_all_user_list_item_email_icon);
        }
    }
}
