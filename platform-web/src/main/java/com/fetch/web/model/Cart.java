package com.fetch.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cart implements Serializable {

    private Set<CartItem> items = new LinkedHashSet<>();

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem ci){
        items.add(ci);
    }

    public void removeItem(CartItem ci){
        items.remove(ci);
    }


    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
