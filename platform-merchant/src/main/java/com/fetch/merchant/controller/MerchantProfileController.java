package com.fetch.merchant.controller;

import com.fetch.merchant.model.NewPassword;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantProfileController {

    @Autowired
    PersistClientAdapter persistClient;

    @PostMapping("/change-password")
    void changePassword(@RequestBody final NewPassword newPassword, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Objects.equals(userDetails.getUsername(), newPassword.getUsername());

        User ou = persistClient.findByUsername(newPassword.getUsername());
        if(ou == null){
            throw new IllegalArgumentException();
        }
        ou.setPasswordHash(DigestUtils.md5DigestAsHex(newPassword.getPassword().getBytes()));
        persistClient.updateUser(ou);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException exc) {
        return ResponseEntity.badRequest().build();
    }
}
