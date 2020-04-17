package com.fetch.merchant.service;


import com.fetch.persist.model.Address;
import com.fetch.persist.model.Merchant;
import feign.Headers;
import feign.RequestLine;

public interface PersistClient {

    @RequestLine("POST /api/v1/persist/save?type=Address")
    @Headers("Content-Type: application/json")
    Long createAddress(Address address);

    @RequestLine("POST /api/v1/persist/save?type=Merchant")
        @Headers("Content-Type: application/json")
    Long createMerchant(Merchant merchant);

}
