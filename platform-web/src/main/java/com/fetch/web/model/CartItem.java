package com.fetch.web.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long productId;
    private int quantity;

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
}