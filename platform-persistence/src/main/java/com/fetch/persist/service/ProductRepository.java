package com.fetch.persist.service;

import com.fetch.persist.model.ProductCatalog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductRepository extends Repository<ProductCatalog, Long> {

    @Query( " SELECT DISTINCT pc " +
            "   FROM ProductCatalog pc," +
            "        Product p, " +
            "        Merchant m" +
            "  WHERE pc.id = p.productCatalogId" +
            "    AND p.merchantId = m.id" +
            "    AND m.address.postalCode = ?1 " +
            "    AND p.inStock = True ")
    List<ProductCatalog> findCatalogWithProductsInStock(String postalCode);
}
