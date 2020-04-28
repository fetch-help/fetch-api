package com.fetch.web.service;

import com.fetch.persist.model.User;
import com.fetch.web.model.JwtUser;
import com.fetch.web.model.SessionUser;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Finds a user by issued token
     *
     * @param token JWT
     * @return
     */
    Optional<JwtUser> findByToken(String token);

    Optional<SessionUser> findBySession(String sessionId);

}
