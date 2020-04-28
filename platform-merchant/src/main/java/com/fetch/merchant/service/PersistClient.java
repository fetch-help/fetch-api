package com.fetch.merchant.service;


import com.fetch.persist.model.*;
import feign.Headers;
import feign.RequestLine;
import feign.QueryMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface PersistClient {

    @RequestLine("GET /api/v1/persist/findOne")
        //@Headers("Content-Type: application/json")
    PostalCode findByPostCode(@QueryMap Map<String, String> params);

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

    @RequestLine("POST /api/v1/persist/save?type=Product")
    @Headers("Content-Type: application/json")
    Long createProduct(@QueryMap Map<String, String> params, Product product);

    @RequestLine("POST /api/v1/persist/saveList?type=Product")
    @Headers("Content-Type: application/json")
    void createProducts(@QueryMap Map<String, String> params, List<Product> products);

    @RequestLine("GET /api/v1/persist/findAllByParentId?type=Product")
    @Headers("Content-Type: application/json")
    List<Product> getProducts(@QueryMap Map<String, Object> params);

    @RequestLine("DELETE /api/v1/persist/deleteAll?type=Product")
    @Headers("Content-Type: application/json")
    void deleteAllProducts(@QueryMap Map<String, String> params, List<Long> productIds);

    @RequestLine("DELETE /api/v1/persist/delete?type=Product")
    @Headers("Content-Type: application/json")
    void deleteProduct(@QueryMap Map<String, Object> params);

    @RequestLine("GET /api/v1/persist/find?type=Product")
        //@Headers("Content-Type: application/json")
    Product getProduct(@QueryMap Map<String, Long> params);

    @RequestLine("GET /api/v1/persist/findAll?type=ProductCatalog")
        //@Headers("Content-Type: application/json")
    List<ProductCatalog> getProductCatalog();

    @RequestLine("GET /api/v1/persist/product/findCatalogWithProductsInStock")
        //@Headers("Content-Type: application/json")
    List<ProductCatalog> getProductCatalogWithProductsInStock(@QueryMap Map<String, String> params);



}
