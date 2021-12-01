package com.myowncountry.mystocks.recycleview.model;

public class ShopTransactionUpdateDTO {

    private String type;
    private double value;
    private boolean bottleCounter;
    private long qty;
    private long stocks;

    public ShopTransactionUpdateDTO() {

    }

    public ShopTransactionUpdateDTO(String type, double value, boolean bottleCounter) {
        this.type = type;
        this.value = value;
        this.bottleCounter = bottleCounter;
        this.qty = 0;
    }

    public long getStocks() {
        return stocks;
    }

    public void setStocks(long stocks) {
        this.stocks = stocks;
    }

    public boolean isBottleCounter() {
        return bottleCounter;
    }

    public void setBottleCounter(boolean bottleCounter) {
        this.bottleCounter = bottleCounter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
