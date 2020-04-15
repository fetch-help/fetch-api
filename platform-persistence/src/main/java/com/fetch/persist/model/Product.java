package com.fetch.persist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Indexed
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private long version;

    @Field(termVector = TermVector.YES)
    private String name;

    private Double price;

    private String description;
    @CreationTimestamp
    private Timestamp createdOn;
    @UpdateTimestamp
    private Timestamp lastUpdatedOn;

    @JsonIgnore
    @JoinColumn(name = "supplier_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Supplier.class, fetch = FetchType.LAZY)
    private Supplier supplier;

    @Column(name = "supplier_id")
    private Long supplierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
