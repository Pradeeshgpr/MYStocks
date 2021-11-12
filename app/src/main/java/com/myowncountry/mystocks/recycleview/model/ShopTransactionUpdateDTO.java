package com.myowncountry.mystocks.recycleview.model;

public class ShopTransactionUpdateDTO {

    private String type;
    private long value;
    private boolean bottleCounter;
    private long qty;

    public ShopTransactionUpdateDTO() {

    }

    public ShopTransactionUpdateDTO(String type, long value, boolean bottleCounter) {
        this.type = type;
        this.value = value;
        this.bottleCounter = bottleCounter;
        this.qty = 0;
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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
