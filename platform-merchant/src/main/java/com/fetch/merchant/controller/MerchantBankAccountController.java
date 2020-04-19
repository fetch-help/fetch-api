package com.fetch.merchant.controller;

import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.BankAccount;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantBankAccountController {

    Logger log = LoggerFactory.getLogger(MerchantBankAccountController.class);

    @Autowired
    PersistClientAdapter persistClient;

    /**
     * Check if the user matches the merchant whose account is being updated
     * @param merchantId
     * @param authentication
     */
    private void doChecks(final Long merchantId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User ou = persistClient.findByUsername(userDetails.getUsername());
        if(ou == null){
            throw new IllegalArgumentException();
        }
        Merchant merchant = persistClient.findMerchantByName(userDetails.getUsername());
        if(!merchant.getId().equals(merchantId)){
            throw new IllegalArgumentException();
        }
    }

    @PostMapping("/bank-account")
    @ResponseBody
    Long registerBankAccount(
            @NotNull @RequestBody final BankAccount bankAccount, Authentication authentication) {
        if(bankAccount.getMerchant()==null || bankAccount.getMerchant().getId()==null){
            throw new IllegalArgumentException();
        }

        doChecks(bankAccount.getMerchant().getId(), authentication);

        return persistClient.createMerchantBankAccount(bankAccount);
    }

    @GetMapping("/bank-account")
    @ResponseBody
    BankAccount getBankAccount(
            @NotNull @RequestParam("merchantId") final Long merchantId, Authentication authentication) {
        if(merchantId==null){
            throw new IllegalArgumentException();
        }

        Merchant merchant = persistClient.getMerchant(merchantId);
        if(merchant==null || merchant.getBankAccount()==null){
            throw new IllegalArgumentException();
        }
        doChecks(merchant.getId(), authentication);

        return merchant.getBankAccount();
    }

    @PutMapping("/bank-account")
    @ResponseBody
    void updateBankAccount(
            @NotNull @RequestBody final BankAccount bankAccount, Authentication authentication) {
        if(bankAccount.getId()==null || bankAccount.getMerchant().getId()==null){
            throw new IllegalArgumentException();
        }

        doChecks(bankAccount.getMerchant().getId(), authentication);

        persistClient.updateMerchantBankAccount(bankAccount);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException exc) {
        log.error("Error handing request",exc);
        return ResponseEntity.badRequest().build();
    }
}