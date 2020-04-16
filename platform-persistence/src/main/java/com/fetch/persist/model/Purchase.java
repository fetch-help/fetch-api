package com.fetch.persist.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<PurchaseItem> items = new ArrayList<PurchaseItem>();

    private Timestamp createdOn;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(final List<PurchaseItem> items) {
        this.items = items;
    }

    public Timestamp getCreatedOn() {
        return new Timestamp(createdOn.getTime());
    }

    public void setCreatedOn(final Timestamp createdOn) {
        this.createdOn = new Timestamp(createdOn.getTime());
    }

}
