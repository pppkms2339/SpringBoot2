package com.example.thymeleaf.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "new_product")
    private boolean newProduct;

    @Column(name = "hot_product")
    private boolean hotProduct;

    private double price;

    @Column(name = "old_price")
    private double oldPrice;

    private String image;

    @Column(length = 65535)
    @Type(type = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductType productType;

}
