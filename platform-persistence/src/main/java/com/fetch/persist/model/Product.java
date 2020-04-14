package com.fetch.persist.model;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double price;

    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_supplier")
    private Supplier supplier;

}
