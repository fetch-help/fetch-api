package com.fetch.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.persist.model.*;

import java.util.Collection;
import java.util.List;

public final class TypeLookup {

    public static <T> long getId(T t, String type) {
        switch (type) {
            case "Address":
                return ((Address) t).getId();
            case "Customer":
                return ((Customer) t).getId();
            case "Delivery":
                return ((Delivery) t).getId();
            case "Product":
                return ((Product) t).getId();
            case "Purchase":
                return ((Purchase) t).getId();
            case "PurchaseItem":
                return ((PurchaseItem) t).getId();
            case "Supplier":
                return ((Supplier) t).getId();
        }
        throw new IllegalArgumentException("Invalid type");
    }

    public static Class<?> findClass(String type) {
        switch (type) {
            case "Address":
                return Address.class;
            case "Customer":
                return Customer.class;
            case "Delivery":
                return Delivery.class;
            case "Product":
                return Product.class;
            case "Purchase":
                return Purchase.class;
            case "PurchaseItem":
                return PurchaseItem.class;
            case "Supplier":
                return Supplier.class;
        }
        throw new IllegalArgumentException("Invalid type");
    }

    public static <T> T getObject(String json, Class<?> cl) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(json, (Class<T>) cl);
    }

    public static String writeJson(Object ob) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(ob);
    }

    public static String writeJsonList(Collection<Object> ob) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writerFor(new TypeReference<List<Object>>(){}).writeValueAsString(ob);
    }
}
