package com.fetch.merchant.controller;

import com.fetch.merchant.model.UserResponse;
import com.fetch.merchant.service.MerchantService;
import com.fetch.merchant.model.User;
import com.fetch.merchant.service.JwtClientAdapter;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fetch.persist.model.Merchant;
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

    @Autowired
    PersistClientAdapter persistClient;

    @PostMapping("register")
    @ResponseBody
    UserResponse register(
            @NotNull @RequestBody final Merchant merchant) {

        String username = merchant.getName();
        String password = UUID.randomUUID().toString();
        users.save(new User(username,
                username,
                password));

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