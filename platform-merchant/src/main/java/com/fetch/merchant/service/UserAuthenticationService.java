package com.fetch.merchant.service;

import com.fetch.merchant.model.JwtUser;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Finds a user by issued token
     *
     * @param token JWT
     * @return
     */
    Optional<JwtUser> findByToken(String token);

}
