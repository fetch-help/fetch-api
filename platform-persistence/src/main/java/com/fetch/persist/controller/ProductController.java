package com.fetch.persist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fetch.persist.model.ProductCatalog;
import com.fetch.persist.service.PersistenceService;
import com.fetch.persist.service.ProductService;
import com.fetch.persist.service.TypeLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/persist/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("findCatalogWithProductsInStock")
    @ResponseBody
    List<ProductCatalog> findAll(@RequestParam String postCode) throws JsonProcessingException {
        return productService.findCatalogWithProductsInStock(postCode);
    }

}