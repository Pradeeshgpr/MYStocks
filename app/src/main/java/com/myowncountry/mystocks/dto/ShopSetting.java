package com.myowncountry.mystocks.dto;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopSetting {

    private List<Value> valuesType = new ArrayList<>();
    private double bottlePrice;

    public double getBottlePrice() {
        return bottlePrice;
    }

    public void setBottlePrice(double bottlePrice) {
        this.bottlePrice = bottlePrice;
    }

    public static class Value {
        private String type;
        private String bottleCounted;
        private long value;

        public Value() {
            value = 0;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBottleCounted() {
            return bottleCounted;
        }

        public void setBottleCounted(String bottleCounted) {
            this.bottleCounted = bottleCounted;
        }
    }

    public List<Value> getValuesType() {
        return valuesType;
    }

    public void setValuesType(List<Value> valuesType) {
        this.valuesType.addAll(valuesType);
    }
}
