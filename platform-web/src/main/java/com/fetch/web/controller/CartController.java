package com.fetch.web.controller;

import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.User;
import com.fetch.web.model.Cart;
import com.fetch.web.model.CartCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    Logger log = LoggerFactory.getLogger(CartController.class);

    CartCache cartCache = new CartCache();

    @GetMapping("/get")
    public Cart get(@RequestParam String userIdentifier, Authentication authentication) {
        doChecks(userIdentifier, authentication);
        if(!cartCache.contains(userIdentifier)){
            throw new IllegalArgumentException(userIdentifier);
        }
        return cartCache.get(userIdentifier);
    }

    @PostMapping("/add")
    public void post(@RequestParam String userIdentifier, @RequestBody Cart cart, Authentication authentication) {
        doChecks(userIdentifier, authentication);
        cartCache.add(userIdentifier, cart);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String userIdentifier, Authentication authentication) {
        doChecks(userIdentifier, authentication);
        cartCache.remove(userIdentifier);
    }

    private void doChecks(final String userIdentifier, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!Objects.equals(userIdentifier, userDetails.getUsername())) {
            throw new IllegalArgumentException();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException exc) {
        log.error("Error handing request", exc);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegal(Exception exc) {
        log.error("Error handing request", exc);
        return ResponseEntity.unprocessableEntity().build();
    }

}