package com.fetch.merchant.controller;

import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.persist.model.BankAccount;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantProductController extends MerchantAbstractController{

    Logger log = LoggerFactory.getLogger(MerchantProductController.class);

    @Autowired
    PersistClientAdapter persistClient;


    @PostMapping("/product-upload")
    @ResponseBody
    void setup(
            @NotNull @RequestParam("merchantId") final Long merchantId,
            @NotNull @RequestBody final List<Product> products, Authentication authentication) {


        Merchant merchant = persistClient.getMerchant(merchantId);
        doChecks(merchantId, authentication);

        products.stream().forEach(p->p.setMerchantId(merchantId));

        persistClient.createProducts(products);
    }

    @GetMapping("/products")
    @ResponseBody
    List<Product> download(
            @NotNull @RequestParam("merchantId") final Long merchantId, Authentication authentication) {

        Merchant merchant = persistClient.getMerchant(merchantId);
        if(merchant==null){
            log.warn("Merchant not found for {} ", merchantId);
            throw new IllegalArgumentException();
        }
        doChecks(merchant.getId(), authentication);

        return persistClient.getProducts(merchantId);
    }

    @DeleteMapping("/delete-product")
    @ResponseBody
    void deleteProduct(
            @NotNull @RequestParam("merchantId") final Long merchantId,
            @NotNull @RequestParam("productId") final Long productId,
            Authentication authentication) {

        doChecks(merchantId, authentication);
        Merchant merchant = persistClient.getMerchant(merchantId);
        if(merchant==null){
            log.warn("Merchant not found for {} ", merchantId);
            throw new IllegalArgumentException();
        }

        Product merchantProduct = persistClient.getProduct(productId);
        if(!merchantProduct.getMerchantId().equals(merchantId)){
            log.warn("Product merchant id{} does not match merchant id {} ", merchantProduct.getMerchantId(),
                    merchantId);
            throw new IllegalArgumentException();
        }

        persistClient.deleteProduct(productId);
    }

    @DeleteMapping("/delete-products")
    @ResponseBody
    void deleteProducts(
            @NotNull @RequestParam("merchantId") final Long merchantId,
            @NotNull @RequestBody final List<Long> productIds, Authentication authentication) {

        doChecks(merchantId, authentication);
        Merchant merchant = persistClient.getMerchant(merchantId);
        if(merchant==null){
            log.warn("Merchant not found for {} ", merchantId);
            throw new IllegalArgumentException();
        }

        List<Product> merchantProducts = persistClient.getProducts(merchantId);
        Set<Long> merchantProductIds = merchantProducts.stream().map(Product::getId).collect(
                Collectors.toSet());
        Set<Long> productIdsToDelete = productIds.stream().filter(
                pi->merchantProductIds.contains(pi)).collect(Collectors.toSet());

        persistClient.deleteAllProducts(new ArrayList<>(productIdsToDelete));
    }


}