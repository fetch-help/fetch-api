package com.fetch.web.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long productId;
    private int quantity;

    public CartItem(){}

    public CartItem(long productId, int qty){
        this.productId = productId;
        this.quantity = qty;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return getProductId() == cartItem.getProductId() &&
                getQuantity() == cartItem.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getQuantity());
    }
}