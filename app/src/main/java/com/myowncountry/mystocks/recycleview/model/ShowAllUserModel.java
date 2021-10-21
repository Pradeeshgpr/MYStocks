package com.myowncountry.mystocks.recycleview.model;

import android.widget.ImageView;

import com.myowncountry.mystocks.animation.textdrawable.TextDrawable;
import com.myowncountry.mystocks.dto.User;

public class ShowAllUserModel {

    private String emailId;
    private boolean admin;
    private TextDrawable iconColor;

    public ShowAllUserModel(String emailId, boolean admin) {
        this.emailId = emailId;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public TextDrawable getIconColor() {
        return iconColor;
    }

    public void setIconColor(TextDrawable iconColor) {
        this.iconColor = iconColor;
    }
}
