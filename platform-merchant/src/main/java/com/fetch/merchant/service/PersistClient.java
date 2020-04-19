package com.fetch.merchant.service;


import com.fetch.persist.model.Address;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.BankAccount;
import com.fetch.persist.model.User;
import feign.Headers;
import feign.RequestLine;
import feign.QueryMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface PersistClient {

    @RequestLine("POST /api/v1/persist/save?type=User")
    @Headers("Content-Type: application/json")
    void createUser(User user);

    @RequestLine("PUT /api/v1/persist/update?type=User")
    @Headers("Content-Type: application/json")
    void updateUser(@QueryMap Map<String, Long> params, User user);

    @RequestLine("GET /api/v1/persist/findOne")
    //@Headers("Content-Type: application/json")
    User findByUsername(@QueryMap Map<String, String> params);

    @RequestLine("POST /api/v1/persist/save?type=Address")
    @Headers("Content-Type: application/json")
    Long createAddress(Address address);

    @RequestLine("POST /api/v1/persist/save?type=Merchant")
        @Headers("Content-Type: application/json")
    Long createMerchant(Merchant merchant);

    @RequestLine("GET /api/v1/persist/find?type=Merchant")
    //@Headers("Content-Type: application/json")
    Merchant getMerchant(@QueryMap Map<String, Long> params);

    @RequestLine("GET /api/v1/persist/findOne")
        //@Headers("Content-Type: application/json")
    Merchant findMerchantByName(@QueryMap Map<String, String> params);

    @RequestLine("POST /api/v1/persist/save?type=BankAccount")
    @Headers("Content-Type: application/json")
    Long createMerchantBankAccount(BankAccount bankAccount);

    @RequestLine("GET /api/v1/persist/find?type=BankAccount")
    //@Headers("Content-Type: application/json")
    BankAccount getMerchantBankAccount(@QueryMap Map<String, Long> params);

    @RequestLine("PUT /api/v1/persist/update?type=Merchant")
    @Headers("Content-Type: application/json")
    void updateMerchant(@QueryMap Map<String, Long> params, Merchant merchant);

}
