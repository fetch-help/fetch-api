package com.fetch.merchant.service;

import com.fetch.merchant.model.User;

import java.util.Optional;

public interface MerchantService {

    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);

}
