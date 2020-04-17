package com.fetch.merchant.controller;

import com.fetch.merchant.service.MerchantService;
import com.fetch.merchant.model.User;
import com.fetch.merchant.service.JwtClientAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("public/api/v1/merchant")
public class MerchantController {

    @Autowired
    MerchantService users;

    @Autowired
    JwtClientAdapter jwtClient;

    @PostMapping("register")
    String register(
            @NotNull @RequestParam("username") final String username,
            @NotNull @RequestParam("password") final String password) {
        users.save(new User(username,
                username,
                password));
        //TODO persist
        return UUID.randomUUID().toString();
    }

    @PostMapping("login")
    String login(
            @NotNull @RequestParam("username") final String username,
            @NotNull @RequestParam("password") final String password) {

        Optional<User> ouser = users.findByUsername(username);
        if (!ouser.isPresent()) {
            return "invalid";
        }
        if (!Arrays.equals(ouser.get().getPassword(), password.toCharArray())) {
            //TODO lock account after number of tries
            return "invalid";
        }
        return jwtClient.createToken(username);
    }
}