package com.fetch.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PostalCode extends ModelId{

    @Column(unique=true)
    private String code;

    private double lat;
    private double lon;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
