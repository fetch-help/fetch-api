package com.fetch.merchant.controller;

import com.fetch.merchant.model.NewPassword;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.Address;
import com.fetch.persist.model.BankAccount;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantProfileController extends MerchantAbstractController{

    Logger log = LoggerFactory.getLogger(MerchantProfileController.class);

    @Autowired
    PersistClientAdapter persistClient;

    @PutMapping("/change-password")
    void changePassword(@RequestBody final NewPassword newPassword, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Objects.equals(userDetails.getUsername(), newPassword.getUsername());
        User ou = persistClient.findByUsername(newPassword.getUsername());
        if(ou == null){
            log.warn("User not found for username {} ", newPassword.getUsername());
            throw new IllegalArgumentException();
        }
        ou.setPasswordHash(DigestUtils.md5DigestAsHex(newPassword.getPassword().getBytes()));
        persistClient.updateUser(ou);
    }

    @PutMapping("/change-address")
    void changeAddress(@NotNull @RequestParam("merchantId") final Long merchantId,
                       @RequestBody final Address address, Authentication authentication) {
        doChecks(merchantId, authentication);
        Merchant merchant = getMerchant(merchantId);
        if(!merchant.getAddress().getId().equals(address.getId())){
            log.warn("Merchants address id {} does not match the address id passed in {}",
                    merchant.getAddress().getId(), address.getId() );
            throw new IllegalArgumentException();
        }
        merchant.setAddress(address);
        persistClient.updateMerchant(merchant);

    }

    @GetMapping("/profile-by-id")
    @ResponseBody
    Merchant getMerchant(
            @NotNull @RequestParam("id") final Long id) {

        return persistClient.getMerchant(id);
    }

    @GetMapping("/profile-by-name")
    @ResponseBody
    Merchant getMerchantByName(
            @NotNull @RequestParam("name") final String name) {

        return persistClient.findMerchantByName(name);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException exc) {
        log.error("Error handing request",exc);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegal(Exception exc) {
        log.error("Error handing request",exc);
        return ResponseEntity.badRequest().build();
    }
}
