package com.fetch.merchant.service;


import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface JwtClient {

    @RequestLine("POST /api/v1/token/create")
    //@Headers("Content-Type: application/json")
    String createToken(@QueryMap Map<String, String> params);
}
