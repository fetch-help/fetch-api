package com.fetch.persist.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Order order;

    private Timestamp deliveryScheduledOn;
    private Timestamp deliveredOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

}
