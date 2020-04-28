package com.fetch.merchant.util;

import com.fetch.merchant.model.PostCode;

/**
 * https://www.movable-type.co.uk/scripts/latlong.html
 */
public class Haversine {
    /**
     * https://en.wikipedia.org/wiki/Earth_radius
     * Earths radius in miles
     */
    private static final int R = 3963;
    /**
     *JavaScript:
     *
     * const R = 6371e3; // metres
     * const φ1 = lat1 * Math.PI/180; // φ, λ in radians
     * const φ2 = lat2 * Math.PI/180;
     * const Δφ = (lat2-lat1) * Math.PI/180;
     * const Δλ = (lon2-lon1) * Math.PI/180;
     *
     * const a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
     *           Math.cos(φ1) * Math.cos(φ2) *
     *           Math.sin(Δλ/2) * Math.sin(Δλ/2);
     * const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
     *
     * const d = R * c; // in metres
     * @param p1
     * @param p2
     * @return
     */
    public double distance(PostCode p1, PostCode p2){
        double lat1 = p1.getLat();
        double lat2 = p2.getLat();
        double lon1 = p1.getLon();
        double lon2 = p2.getLon();
        double lat1Rads = lat1 * Math.PI/180; // φ, λ in radians
        double lat2Rads = lat2 * Math.PI/180;
        double deltaLatRads = (lat2-lat1) * Math.PI/180;
        double deltaLonRads = (lon2-lon1) * Math.PI/180;
        double a = Math.sin(deltaLatRads/2) * Math.sin(deltaLatRads/2) +
                Math.cos(lat1Rads) * Math.cos(lat2Rads) *
                        Math.sin(deltaLonRads/2) * Math.sin(deltaLonRads/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // in miles
        return d;
    }
}
