package com.fetch.web.service;


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
