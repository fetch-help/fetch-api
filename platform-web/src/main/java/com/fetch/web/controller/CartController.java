package com.fetch.web.controller;

import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.PostalCode;
import com.fetch.persist.model.User;
import com.fetch.web.model.Cart;
import com.fetch.web.model.CartCache;
import com.fetch.web.model.CartItem;
import com.fetch.web.service.JwtClient;
import com.fetch.web.service.JwtClientAdapter;
import com.fetch.web.service.PersistClientAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
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


    @Autowired
    JwtClientAdapter jwtClient;

    @Autowired
    PersistClientAdapter persistClient;

    @GetMapping("/ip")
    public String ip(HttpSession session, @RequestParam String time) {
        log.info("Getting an identifier {}" + session.getId());
        return session.getId();
    }

    @PostMapping("/setup")
    public void setup(HttpSession session, @NonNull @RequestParam String postCode,
                      @RequestParam String time,
                      HttpServletResponse response) {
        String id = session.getId();
        log.info("Setup cart for identifier {}", id);
        PostalCode pCode = persistClient.findByPostCode(postCode);
        if (pCode == null) {
            throw new IllegalArgumentException(postCode);
        }
        if (!cartCache.contains(id)) {
            Cart c = new Cart();
            c.setPostCode(postCode);
            cartCache.add(id, c);
        }
        String token = jwtClient.createToken(id);
        Cookie cookie = new Cookie("X_AUTH_TOKEN", token);
        response.addCookie(cookie);
    }

    @GetMapping("/get")
    public List<CartItem> get(@NonNull @RequestParam String id, @RequestParam String time) {
        //TODO
        //doChecks(userIdentifier, authentication);
        log.info("Getting the cart for identifier {}" + id);
        if (!cartCache.contains(id)) {
            throw new IllegalArgumentException(id);
        }
        return new ArrayList<>(cartCache.get(id).getItems());
    }

    @PostMapping("/add")
    public void post(@NonNull @RequestParam String id,
                     @NonNull @RequestParam String productId,
                     @NonNull @RequestParam String qty,
                     @RequestParam String time) {
        //TODO
        //doChecks(userIdentifier, authentication);
        log.info("Adding product {} to the cart for identifier {}", productId, id);
        if (!cartCache.contains(id)) {
            throw new IllegalArgumentException(id);
        }
        cartCache.get(id).addItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
    }

    @PostMapping("/remove")
    public void remove(@NonNull @RequestParam String id,
                       @NonNull @RequestParam String productId,
                       @NonNull @RequestParam String qty,
                       @RequestParam String time) {
        //TODO
        //doChecks(userIdentifier, authentication);
        log.info("Removing product {} from the cart for identifier {}", productId, id);
        if (cartCache.contains(id)) {
            cartCache.get(id).removeItem(new CartItem(Long.parseLong(productId), Integer.parseInt(qty)));
        }
    }

    @DeleteMapping("/delete")
    public void delete(@NonNull @RequestParam String id,
                       @RequestParam String time) {
        //TODO
        //doChecks(userIdentifier, authentication);
        log.info("Deleting the cart for identifier {}", id);
        cartCache.remove(id);
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