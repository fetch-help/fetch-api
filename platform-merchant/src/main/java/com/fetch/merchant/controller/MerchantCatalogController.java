package com.fetch.merchant.controller;

import com.fetch.merchant.model.UserResponse;
import com.fetch.merchant.service.JwtClientAdapter;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.ProductCatalog;
import com.fetch.persist.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantCatalogController extends MerchantAbstractController {

    Logger log = LoggerFactory.getLogger(MerchantCatalogController.class);

    @Autowired
    JwtClientAdapter jwtClient;

    @Autowired
    PersistClientAdapter persistClient;

    @GetMapping("catalog")
    List<ProductCatalog> getCatalog(@RequestParam String callerId, Authentication authentication){

        doChecks(callerId, authentication);

        return persistClient.getProductCatalog();
    }

    @PostMapping("catalog/evict")
    void postCatalog(@RequestParam String callerId, Authentication authentication){
        doChecks(callerId, authentication);
        evictProductCatalog();
    }

    @CacheEvict(value = "catalog", allEntries = true)
    public void evictProductCatalog() {
    }

    @GetMapping("catalog/level1")
    List<ProductCatalog> getCatalogLevel1(@RequestParam String callerId, Authentication authentication,
                                          @NotNull @RequestParam String level1){
        List<ProductCatalog> pc = getCatalog(callerId, authentication);
        return pc.stream().filter(p->level1.equals(p.getLevel1())).collect(Collectors.toList());
    }

    @GetMapping("catalog/level2")
    List<ProductCatalog> getCatalogLevel2(@RequestParam String callerId, Authentication authentication,
                                          @NotNull @RequestParam String level2){
        List<ProductCatalog> pc = getCatalog(callerId, authentication);
        return pc.stream().filter(p->level2.equals(p.getLevel2())).collect(Collectors.toList());
    }

    @GetMapping("catalog/level3")
    List<ProductCatalog> getCatalogLevel3(@RequestParam String callerId, Authentication authentication,
                                          @NotNull @RequestParam String level3){
        List<ProductCatalog> pc = getCatalog(callerId, authentication);
        return pc.stream().filter(p->level3.equals(p.getLevel3())).collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegal(Exception exc) {
        log.error("Error handing request",exc);
        return ResponseEntity.badRequest().build();
    }
}