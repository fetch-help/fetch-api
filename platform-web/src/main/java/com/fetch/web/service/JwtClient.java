package com.fetch.web.service;


import com.fetch.persist.model.PostalCode;
import com.fetch.persist.model.User;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface JwtClient {

    @RequestLine("POST /api/v1/token/create")
    //@Headers("Content-Type: application/json")
    String createToken(@QueryMap Map<String, String> params);

    @RequestLine("POST /api/v1/token/validate")
        //@Headers("Content-Type: application/json")
    Map<String, String> validateToken(@QueryMap Map<String, String> params);


}
