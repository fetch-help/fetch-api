package com.fetch.web.service;

import com.fetch.persist.model.PostalCode;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface PersistClient {

    @RequestLine("GET /api/v1/persist/findOne")
        //@Headers("Content-Type: application/json")
    PostalCode findByPostCode(@QueryMap Map<String, String> params);

}
