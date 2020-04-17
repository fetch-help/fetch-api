package com.fetch.persist.model;

import javax.persistence.*;

@Entity
public class PurchaseItem extends ModelId{

    @ManyToOne
    private Purchase purchase;

    @OneToOne
    private Product product;

    private Long qty;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

}
