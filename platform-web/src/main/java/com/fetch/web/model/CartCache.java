package com.fetch.web.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartCache {

    private final int MAX = 20000;
    Map<String, Cart> cache =
            Collections.synchronizedMap(new LinkedHashMap<String, Cart>() {
                protected boolean removeEldestEntry(Map.Entry<String, Cart> eldest)
                {
                    return size() > MAX;
                }
            });

    public void add(String id, Cart cart){
        cache.put(id, cart);
    }

    public Cart get(String id){
        return cache.get(id);
    }

    public boolean contains(String id){
        return cache.containsKey(id);
    }

    public void remove(String id){
        cache.remove(id);
    }

}
