package com.fetch.web.controller;

import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.User;
import com.fetch.web.model.Cart;
import com.fetch.web.model.CartCache;
import com.fetch.web.model.CartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

//TODO https://spring.io/guides/gs/rest-service-cors/
@RestController
@RequestMapping("public/api/v1/cart")
public class CartController {

    Logger log = LoggerFactory.getLogger(CartController.class);

    CartCache cartCache = new CartCache();

    @CrossOrigin(origins = "http://localhost:9001")
    @GetMapping("/ip")
    public String ip(HttpServletRequest request) {
        return UUID.randomUUID().toString();
    }

    @CrossOrigin(origins = "http://localhost:9001")
    @GetMapping("/get")
    public Cart get(@RequestParam String userIdentifier, Authentication authentication) {
        //TODO
        //doChecks(userIdentifier, authentication);
        if(!cartCache.contains(userIdentifier)){
            throw new IllegalArgumentException(userIdentifier);
        }
        return cartCache.get(userIdentifier);
    }

    @CrossOrigin(origins = "http://localhost:9001")
    @PostMapping("/add")
    public void post(@NonNull @RequestParam String userIdentifier,
                     @NonNull @RequestParam String productId,
                     @NonNull @RequestParam String qty, Authentication authentication) {
        //TODO
        //doChecks(userIdentifier, authentication);
        if(!cartCache.contains(userIdentifier)){
            Cart c = new Cart();
            c.addItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
            cartCache.add(userIdentifier, c);
        }
        else{
            cartCache.get(userIdentifier).addItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
        }

    }

    @CrossOrigin(origins = "http://localhost:9001")
    @PostMapping("/remove")
    public void remove(@NonNull @RequestParam String userIdentifier,
                     @NonNull @RequestParam String productId,
                     @NonNull @RequestParam String qty, Authentication authentication) {
        //TODO
        //doChecks(userIdentifier, authentication);
        if(cartCache.contains(userIdentifier)){
            cartCache.get(userIdentifier).removeItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
        }
    }

    @CrossOrigin(origins = "http://localhost:9001")
    @DeleteMapping("/delete")
    public void delete(@RequestParam String userIdentifier, Authentication authentication) {
        //TODO
        //doChecks(userIdentifier, authentication);
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