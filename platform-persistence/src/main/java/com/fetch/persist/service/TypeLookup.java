package com.fetch.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.persist.model.*;

import java.util.*;

public final class TypeLookup {

    static Map<String,Class<?>> classes = new HashMap<>();

    static{
        List<Class<?>> l = Arrays.asList(Address.class, Customer.class,
                Delivery.class, Product.class, Purchase.class,
                PurchaseItem.class, Merchant.class);

        l.stream().forEach(c->{
            classes.put(c.getSimpleName(), c);
        });
    }

    public static <T> long getId(T t) {
        ModelId c = (ModelId) t;
        return c.getId();
        /*
        switch (type) {
            case Address.class:
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
            case "Merchant":
                return ((Merchant) t).getId();
        }
        throw new IllegalArgumentException("Invalid type");
        */

    }

    public static Class<?> findClass(String type) {
        if(!classes.containsKey(type)) {
            throw new IllegalArgumentException("Invalid type");
        }
        return classes.get(type);
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
