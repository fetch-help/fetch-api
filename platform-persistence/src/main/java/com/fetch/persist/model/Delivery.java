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
        return deliveryScheduledOn;
    }

    public void setDeliveryScheduledOn(Timestamp deliveryScheduledOn) {
        this.deliveryScheduledOn = deliveryScheduledOn;
    }

    public Timestamp getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(Timestamp deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
