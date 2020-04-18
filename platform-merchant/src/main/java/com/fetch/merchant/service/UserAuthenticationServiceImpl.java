package com.fetch.merchant.service;

import com.fetch.merchant.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    JwtClientAdapter jwtClient;

    @Override
    public Optional<JwtUser> findByToken(String token) {
        Map<String, String> claims = jwtClient.validateToken(token);
        if(claims.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new JwtUser(claims.get("username")));
    }
}
