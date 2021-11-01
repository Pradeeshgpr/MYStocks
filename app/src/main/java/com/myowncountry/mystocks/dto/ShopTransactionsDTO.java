package com.myowncountry.mystocks.dto;

import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.util.FixedArrayList;

import java.util.Date;
import java.util.List;

public class ShopTransactionsDTO {

    private List<ShopTransaction> shopTransactionList = new FixedArrayList(GenericsConstants.MAX_TRANSACTION_PER_SHOP);

    public ShopTransactionsDTO() {

    }

    public ShopTransactionsDTO(ShopTransactionsDTO shopTransactionsDTO) {
        if (shopTransactionsDTO == null) {
            return;
        }

        if (shopTransactionsDTO.getShopTransactionList() != null) {
            this.shopTransactionList.addAll(shopTransactionsDTO.getShopTransactionList());
        }
    }

    public void addAll(ShopTransactionsDTO shopTransactionsDTO) {
        if (shopTransactionsDTO == null) {
            return;
        }

        if (shopTransactionsDTO.getShopTransactionList() != null) {
            this.shopTransactionList.addAll(shopTransactionsDTO.getShopTransactionList());
        }
    }

    public static class ShopTransaction {
        private int bottles, totalPrice, receivedAmount, receivedBottles;
        private Date createdOn;

        public int getBottles() {
            return bottles;
        }

        public void setBottles(int bottles) {
            this.bottles = bottles;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getReceivedAmount() {
            return receivedAmount;
        }

        public void setReceivedAmount(int receivedAmount) {
            this.receivedAmount = receivedAmount;
        }

        public int getReceivedBottles() {
            return receivedBottles;
        }

        public void setReceivedBottles(int receivedBottles) {
            this.receivedBottles = receivedBottles;
        }

        public Date getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }
    }

    public List<ShopTransaction> getShopTransactionList() {
        return shopTransactionList;
    }

    public void setShopTransactionList(List<ShopTransaction> shopTransactionList) {
        this.shopTransactionList = shopTransactionList;
    }
}
