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
public class Product extends ModelId{

    @JsonIgnore
    @Version
    private long version;

    @Field(termVector = TermVector.YES)
    @Column(unique=true)
    private String name;

    private Double price;

    private String description;
    @JsonIgnore
    @CreationTimestamp
    private Timestamp createdOn;
    @JsonIgnore
    @UpdateTimestamp
    private Timestamp lastUpdatedOn;

    @JsonIgnore
    @JoinColumn(name = "merchant_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Merchant.class, fetch = FetchType.LAZY)
    private Merchant merchant;

    @Column(name = "merchant_id")
    private Long merchantId;

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


    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Timestamp getCreatedOn() {
        return new Timestamp(createdOn.getTime());
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = new Timestamp(createdOn.getTime());
    }

    public Timestamp getLastUpdatedOn() {
        return new Timestamp(lastUpdatedOn.getTime());
    }

    public void setLastUpdatedOn(final Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = new Timestamp(lastUpdatedOn.getTime());
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}
