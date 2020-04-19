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
public class MerchantBankAccountController extends MerchantAbstractController{

    Logger log = LoggerFactory.getLogger(MerchantBankAccountController.class);

    @Autowired
    PersistClientAdapter persistClient;


    @PostMapping("/bank-account")
    @ResponseBody
    Long registerBankAccount(
            @NotNull @RequestParam("merchantId") final Long merchantId,
            @NotNull @RequestBody final BankAccount bankAccount, Authentication authentication) {
        Merchant merchant = persistClient.getMerchant(merchantId);
        doChecks(merchantId, authentication);

        Long bankAccountId = persistClient.createMerchantBankAccount(bankAccount);

        bankAccount.setId(bankAccountId);
        merchant.setBankAccount(bankAccount);

        persistClient.updateMerchant(merchant);

        return bankAccountId;
    }

    @GetMapping("/bank-account")
    @ResponseBody
    BankAccount getBankAccount(
            @NotNull @RequestParam("merchantId") final Long merchantId, Authentication authentication) {

        Merchant merchant = persistClient.getMerchant(merchantId);
        if(merchant==null || merchant.getBankAccount()==null){
            log.warn("Merchant or Bank account not found for {} ", merchantId);
            throw new IllegalArgumentException();
        }
        doChecks(merchant.getId(), authentication);

        return merchant.getBankAccount();
    }

    @PutMapping("/bank-account")
    @ResponseBody
    void updateBankAccount(
            @NotNull @RequestParam("merchantId") final Long merchantId,
            @NotNull @RequestBody final BankAccount bankAccount, Authentication authentication) {

        doChecks(merchantId, authentication);
        Merchant merchant = persistClient.getMerchant(merchantId);
        if(merchant==null || merchant.getBankAccount()==null){
            log.warn("Merchant or Bank account not found for {} ", merchantId);
            throw new IllegalArgumentException();
        }
        if(!merchant.getBankAccount().getId().equals(bankAccount.getId())){
            log.warn("Merchants Bank account Id {} does not match passed in bank account idr {} ",
                    merchant.getBankAccount().getId(), bankAccount.getId());
            throw new IllegalArgumentException();
        }
        merchant.setBankAccount(bankAccount);

        persistClient.updateMerchant(merchant);
    }

}