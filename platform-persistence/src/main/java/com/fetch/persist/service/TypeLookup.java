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
                PurchaseItem.class, Merchant.class, BankAccount.class, User.class);

        l.stream().forEach(c->{
            classes.put(c.getSimpleName(), c);
        });
    }

    public static <T> long getId(T t) {
        ModelId c = (ModelId) t;
        return c.getId();
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
