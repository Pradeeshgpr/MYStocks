package com.myowncountry.mystocks.recycleview.model;

public class ShopTransactionUpdateDTO {

    private String type;
    private long value;
    private boolean bottleCounter;

    public ShopTransactionUpdateDTO() {

    }

    public ShopTransactionUpdateDTO(String type, long value, boolean bottleCounter) {
        this.type = type;
        this.value = value;
        this.bottleCounter = bottleCounter;
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
}
