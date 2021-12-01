package com.myowncountry.mystocks.dto;

import com.myowncountry.mystocks.constants.GenericsConstants;
import com.myowncountry.mystocks.recycleview.model.ShopTransactionUpdateDTO;
import com.myowncountry.mystocks.util.FixedArrayList;

import java.util.Date;
import java.util.List;

public class ShopTransactionsDTO {

    private List<ShopTransaction> shopTransactionList = new FixedArrayList<>(GenericsConstants.MAX_TRANSACTION_PER_SHOP);

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

    public void add(long bottleReceived, double amountReceived, long outStandingBottle, double outStandingAmount) {
        ShopTransaction newObj = new ShopTransaction();
        newObj.setCreatedOn(new Date());
        newObj.setReceivedAmount(amountReceived);
        newObj.setReceivedBottles(bottleReceived);
        newObj.setBottles(outStandingBottle);
        newObj.setTotalPrice(outStandingAmount);
        this.shopTransactionList.add(newObj);
    }

    public static class ShopTransaction {
        private double totalPrice, receivedAmount;
        long bottles, receivedBottles;
        private Date createdOn;

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public double getReceivedAmount() {
            return receivedAmount;
        }

        public void setReceivedAmount(double receivedAmount) {
            this.receivedAmount = receivedAmount;
        }

        public long getBottles() {
            return bottles;
        }

        public void setBottles(long bottles) {
            this.bottles = bottles;
        }

        public long getReceivedBottles() {
            return receivedBottles;
        }

        public void setReceivedBottles(long receivedBottles) {
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
