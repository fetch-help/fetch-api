package com.fetch.persist.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Purchase purchase;

    @OneToOne
    private Customer customer;

    private Timestamp deliveryScheduledOn;
    private Timestamp deliveredOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Timestamp getDeliveryScheduledOn() {
        return new Timestamp(deliveryScheduledOn.getTime());
    }

    public void setDeliveryScheduledOn(final Timestamp deliveryScheduledOn) {
        this.deliveryScheduledOn = new Timestamp(deliveryScheduledOn.getTime());
    }

    public Timestamp getDeliveredOn() {
        return new Timestamp(deliveredOn.getTime());
    }

    public void setDeliveredOn(final Timestamp deliveredOn) {
        this.deliveredOn = new Timestamp(deliveredOn.getTime());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

}
