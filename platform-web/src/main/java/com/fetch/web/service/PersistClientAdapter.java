package com.fetch.web.service;


import com.fetch.persist.model.*;
import feign.Feign;
import feign.Logger;
import feign.QueryMap;
import feign.Request;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class PersistClientAdapter {


    PersistClient client;

    public PersistClientAdapter(@Value("${persistClient.url}")
                                        String persistClientUrl,
                                @Value("${persistClient.url.readTimeoutSecs}")
                                        int persistClientUrlReadTimeout,
                                @Value("${persistClient.url.connectTimeoutSecs}")
                                        int persistClientUrlConnectTimeout) {
        client = createClient(PersistClient.class, persistClientUrl, persistClientUrlReadTimeout,
                persistClientUrlConnectTimeout);
    }

    private <T> T createClient(Class<T> type, String url,
                               int persistClientUrlReadTimeout,
                               int persistClientUrlConnectTimeout) {
        return Feign.builder()
                .options(new Request.Options(persistClientUrlConnectTimeout, persistClientUrlReadTimeout))
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, url);
    }


    public PostalCode findByPostCode(String postCode){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", PostalCode.class.getSimpleName());
        params.put("attr", "code");
        params.put("value", postCode);
        return client.findByPostCode(params);
    }
}
