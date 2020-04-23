package com.fetch.persist.service;

import com.fetch.persist.model.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;

    @Transactional
    public List<ProductCatalog> findCatalogWithProductsInStock(String postalCode) {
        return repo.findCatalogWithProductsInStock(postalCode);
    }

}
