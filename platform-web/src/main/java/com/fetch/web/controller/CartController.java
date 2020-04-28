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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

//TODO https://spring.io/guides/gs/rest-service-cors/
@RestController
@RequestMapping("public/api/v1/cart")
public class CartController {

    Logger log = LoggerFactory.getLogger(CartController.class);

    CartCache cartCache = new CartCache();

    @GetMapping("/ip")
    public String ip(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/get")
    public List<CartItem> get(HttpSession session, Authentication authentication) {
        //TODO
        //doChecks(userIdentifier, authentication);
        if(!cartCache.contains(session.getId())){
            throw new IllegalArgumentException(session.getId());
        }
        return new ArrayList<>(cartCache.get(session.getId()).getItems());
    }

    @PostMapping("/add")
    public void post(HttpSession session,
                     @NonNull @RequestParam String productId,
                     @NonNull @RequestParam String qty) {
        //TODO
        //doChecks(userIdentifier, authentication);
        if(!cartCache.contains(session.getId())){
            Cart c = new Cart();
            c.addItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
            cartCache.add(session.getId(), c);
        }
        else{
            cartCache.get(session.getId()).addItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
        }

    }

    @CrossOrigin(origins = "http://localhost:9001")
    @PostMapping("/remove")
    public void remove(HttpSession session,
                     @NonNull @RequestParam String productId,
                     @NonNull @RequestParam String qty) {
        //TODO
        //doChecks(userIdentifier, authentication);
        if(cartCache.contains(session.getId())){
            cartCache.get(session.getId()).removeItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
        }
    }

    @CrossOrigin(origins = "http://localhost:9001")
    @DeleteMapping("/delete")
    public void delete(HttpSession session) {
        //TODO
        //doChecks(userIdentifier, authentication);
        cartCache.remove(session.getId());
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