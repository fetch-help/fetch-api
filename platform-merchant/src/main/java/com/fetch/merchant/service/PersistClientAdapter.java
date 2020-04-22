package com.fetch.merchant.service;

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

    public void createUser(User user){
        client.createUser(user);
    }

    public void updateUser(User user){
        Map<String, Long> params = new LinkedHashMap<>();
        params.put("id", user.getId());
        client.updateUser(params, user);
    }

    public User findByUsername(String username){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", User.class.getSimpleName());
        params.put("attr", "username");
        params.put("value", username);
        return client.findByUsername(params);
    }

    public Merchant findMerchantByName(String name){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", Merchant.class.getSimpleName());
        params.put("attr", "name");
        params.put("value", name);
        return client.findMerchantByName(params);
    }

    public Long createAddress(Address address) {
        return client.createAddress(address);
    }

    public Long createMerchant(Merchant merchant) {
        return client.createMerchant(merchant);
    }

    public Long createMerchantBankAccount(BankAccount bankAccount) {
        return client.createMerchantBankAccount(bankAccount);
    }
    public BankAccount getMerchantBankAccount(long id){
        Map<String, Long> params = new LinkedHashMap<>();
        params.put("id", id);
        return client.getMerchantBankAccount(params);
    }

    public Merchant getMerchant(Long id){
        Map<String, Long> params = new LinkedHashMap<>();
        params.put("id", id);
        return client.getMerchant(params);
    }
    public void updateMerchant(Merchant merchant){
        Map<String, Long> params = new LinkedHashMap<>();
        params.put("id", merchant.getId());
        client.updateMerchant(params, merchant);
    }

    public Long createProduct(Product product){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", Product.class.getSimpleName());
        return client.createProduct(params, product);
    }

    public void createProducts(List<Product> products){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", Product.class.getSimpleName());
        client.createProducts(params, products);
    }

    public List<Product> getProducts(Long merchantId){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("attr", "merchantId");
        params.put("value", merchantId);
        return client.getProducts(params);
    }

    public void deleteAllProducts(List<Long> productIds){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", Product.class.getSimpleName());
        client.deleteAllProducts(params, productIds);
    }

    public void deleteProduct(Long productId){
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("type", Product.class.getSimpleName());
        params.put("id", productId);
        client.deleteProduct(params);
    }

    public Product getProduct(Long id){
        Map<String, Long> params = new LinkedHashMap<>();
        params.put("id", id);
        return client.getProduct(params);
    }

    public List<ProductCatalog> getProductCatalog(){
        return client.getProductCatalog();
    }
}
