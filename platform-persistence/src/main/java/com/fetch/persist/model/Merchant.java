package com.fetch.persist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.dom4j.rule.Mode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Indexed
public class Merchant extends ModelId {

    @Version
    private long version;

    @Field(termVector = TermVector.YES)
    @Column(unique=true)
    private String name;
    private String contactName;
    private String email;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Address address;
    @JsonIgnore
    @CreationTimestamp
    private Timestamp createdOn;
    @JsonIgnore
    @UpdateTimestamp
    private Timestamp lastUpdatedOn;
    @Field(termVector = TermVector.YES)
    private String locale;
    private String currency;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private BankAccount bankAccount;

    public Merchant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Timestamp getCreatedOn() {
        return new Timestamp(createdOn.getTime());
    }

    public void setCreatedOn(final Timestamp createdOn) {
        this.createdOn = new Timestamp(createdOn.getTime());
    }

    public Timestamp getLastUpdatedOn() {
        return new Timestamp(lastUpdatedOn.getTime());
    }

    public void setLastUpdatedOn(final Timestamp lastUpdatedOn) {
        this.lastUpdatedOn =  new Timestamp(lastUpdatedOn.getTime());
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}