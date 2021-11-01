package com.myowncountry.mystocks.recycleview.model;

import com.myowncountry.mystocks.animation.textdrawable.TextDrawable;
import com.myowncountry.mystocks.dto.ShopDetails;

import java.io.Serializable;

public class ShopDetailsRV implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name, address, id;
    private double outstandingAmount;
    private long outstandingBottles;

    private transient TextDrawable image;

    public ShopDetailsRV(ShopDetails shopDetails, String id) {
        this.name = shopDetails.getName();
        this.address = shopDetails.getAddress();
        this.outstandingAmount = shopDetails.getOutstandingAmount();
        this.outstandingBottles = shopDetails.getOutstandingBottles();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public long getOutstandingBottles() {
        return outstandingBottles;
    }

    public void setOutstandingBottles(long outstandingBottles) {
        this.outstandingBottles = outstandingBottles;
    }

    public TextDrawable getImage() {
        return image;
    }

    public void setImage(TextDrawable image) {
        this.image = image;
    }
}
