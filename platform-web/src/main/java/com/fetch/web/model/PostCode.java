package com.fetch.web.model;

public class PostCode<T> {

    T postCode;
    double lat;
    double lon;

    public PostCode(){
    }

    public PostCode(T t, double lat, double lon){
        this.postCode = t;
        this.lat = lat;
        this.lon = lon;
    }

    public T getPostCode() {
        return postCode;
    }

    public void setPostCode(T postCode) {
        this.postCode = postCode;
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
