package com.fetch.merchant.controller;

import com.fetch.merchant.service.JwtClientAdapter;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.ProductCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantCatalogFilterController extends MerchantAbstractController {

    Logger log = LoggerFactory.getLogger(MerchantCatalogFilterController.class);

    @Autowired
    JwtClientAdapter jwtClient;

    @Autowired
    PersistClientAdapter persistClient;

    @GetMapping("findCatalogByPostCode")
    @Cacheable("catalogByPostCode")
    List<ProductCatalog> getCatalog(@RequestParam String callerId,
                                    @RequestParam String postalCode,
                                    Authentication authentication) {

        doChecks(callerId, authentication);

        List<ProductCatalog> pc = persistClient.getProductCatalogWithProductsInStock(postalCode);
        return pc;
    }

    @PostMapping("catalogByPostCode/evict")
    void postCatalog(@RequestParam String callerId, Authentication authentication) {
        doChecks(callerId, authentication);
        evictProductCatalog();
    }

    @CacheEvict(value = "catalogByPostCode", allEntries = true)
    public void evictProductCatalog() {
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegal(Exception exc) {
        log.error("Error handing request", exc);
        return ResponseEntity.badRequest().build();
    }
}