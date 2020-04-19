package com.fetch.merchant.controller;

import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class MerchantAbstractController {

    Logger log = LoggerFactory.getLogger(MerchantAbstractController.class);

    @Autowired
    PersistClientAdapter persistClient;

    /**
     * Check if the user matches the merchant whose account is being updated
     * @param merchantId
     * @param authentication
     */
    protected void doChecks(final Long merchantId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User ou = persistClient.findByUsername(userDetails.getUsername());
        if(ou == null){
            log.warn("Merchant {} User {} not found !!", merchantId, userDetails.getUsername());
            throw new IllegalArgumentException();
        }
        Merchant merchant = persistClient.findMerchantByName(userDetails.getUsername());
        if(!merchant.getId().equals(merchantId)){
            log.warn("Merchant Id {} dows not match merchant id in db for {} {} !!", merchantId, merchant.getId(), userDetails.getUsername());
            throw new IllegalArgumentException();
        }
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
