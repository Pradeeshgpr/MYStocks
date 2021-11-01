package com.myowncountry.mystocks.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public class FixedArrayList<E> extends ArrayList<E> {

    private int maxSize = 20000;

    public FixedArrayList(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        boolean res = super.addAll(0, c);
        checkOverFlow();
        return res;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        boolean res = super.addAll(0, c);
        checkOverFlow();
        return res;
    }

    private void checkOverFlow() {
        if (this.size() >= maxSize) {
            this.subList(0, maxSize);
        }
    }

    @Override
    public boolean add(E e) {
        try {
            super.add(0, e);
            checkOverFlow();
            return true;
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void add(int index, E element) {
        super.add(0, element);
    }
}
