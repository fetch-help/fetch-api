package com.fetch.merchant.service;

import com.fetch.persist.model.Address;
import com.fetch.persist.model.BankAccount;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.User;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
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
        client.updateUser(user);
    }

    public User findByUsername(String username){
        Map<String, String> params = new LinkedHashMap<>();
        params.put("type", "User");
        params.put("attr", "username");
        params.put("value", username);
        return client.findByUsername(params);
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
        return client.getMerchantBankAccount(id);
    }

    public void updateMerchantBankAccount(BankAccount bankAccount){
        client.updateMerchantBankAccount(bankAccount);
    }
}
