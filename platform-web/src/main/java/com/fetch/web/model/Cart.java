package com.fetch.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cart implements Serializable {

    private String postCode;

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


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "postCode='" + postCode + '\'' +
                ", items=" + items +
                '}';
    }
}
