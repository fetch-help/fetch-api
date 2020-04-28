package com.fetch.web.config;

import com.fetch.web.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @NonNull
    @Autowired
    UserAuthenticationService auth;

    @Override
    protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
        // Nothing to do
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
        final Object token = authentication.getCredentials();
        String tok = String.valueOf(token);
        if(tok.startsWith("JSESSIONID")){
            return auth.findBySession(tok).get();
        }
        else {
            return Optional
                    .ofNullable(token)
                    .map(String::valueOf)
                    .flatMap(auth::findByToken)
                    .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
        }
    }
}