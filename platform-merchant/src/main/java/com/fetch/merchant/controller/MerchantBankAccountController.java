package com.fetch.merchant.controller;

import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantBankAccountController {

    @Autowired
    PersistClientAdapter persistClient;

    @PostMapping("/bank-account")
    @ResponseBody
    Long registerBankAccount(
            @NotNull @RequestBody final BankAccount bankAccount) {
        if(bankAccount.getMerchant()==null || bankAccount.getMerchant().getId()==null){
            throw new IllegalArgumentException();
        }
        return persistClient.createMerchantBankAccount(bankAccount);
    }

    @GetMapping("/bank-account")
    @ResponseBody
    BankAccount getBankAccount(
            @NotNull @RequestParam("id") final Long id) {
        if(id==null){
            throw new IllegalArgumentException();
        }
        return persistClient.getMerchantBankAccount(id);
    }

    @PutMapping("/bank-account")
    @ResponseBody
    void updateBankAccount(
            @NotNull @RequestBody final BankAccount bankAccount) {
        if(bankAccount.getId()==null){
            throw new IllegalArgumentException();
        }
        persistClient.updateMerchantBankAccount(bankAccount);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException exc) {
        return ResponseEntity.badRequest().build();
    }
}