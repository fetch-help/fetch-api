package com.fetch.merchant.controller;

import com.fetch.merchant.model.UserResponse;
import com.fetch.merchant.service.JwtClientAdapter;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.Address;
import com.fetch.persist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import com.fetch.persist.model.Merchant;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("public/api/v1/merchant")
public class MerchantPublicController {

    Logger log = LoggerFactory.getLogger(MerchantPublicController.class);

    @Autowired
    JwtClientAdapter jwtClient;

    @Autowired
    PersistClientAdapter persistClient;

    @PostMapping("register")
    @ResponseBody
    UserResponse register(
            @NotNull @RequestBody final Merchant merchant) {

        String username = merchant.getName();
        String password = UUID.randomUUID().toString();
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(DigestUtils.md5DigestAsHex(password.getBytes()));
        persistClient.createUser(u);

        Long addressId = persistClient.createAddress(merchant.getAddress());
        merchant.getAddress().setId(addressId);

        Address address = new Address();
        address.setId(addressId);
        merchant.setAddress(address);
        Long merchantId = persistClient.createMerchant(merchant);

        return new UserResponse(merchantId, username, password);
    }

    @PostMapping("login")
    String login(
            @NotNull @RequestParam("username") final String username,
            @NotNull @RequestParam("password") final String password) {

        log.info("Login user {}", username);

        User user = persistClient.findByUsername(username);
        if (user == null) {
            log.warn("Login user {} not found !!", username);
            return "invalid";
        }
        if (!Objects.equals(user.getPasswordHash(),
                DigestUtils.md5DigestAsHex(password.getBytes()))) {
            //TODO lock account after number of tries
            return "invalid";
        }
        return jwtClient.createToken(username);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegal(Exception exc) {
        log.error("Error handing request",exc);
        return ResponseEntity.badRequest().build();
    }
}