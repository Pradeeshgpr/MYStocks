package com.myowncountry.mystocks.dto;

public class ShopDetails {
    private String name;
    private String address;
    private double outstandingAmount;
    private long outstandingBottles;

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
}
