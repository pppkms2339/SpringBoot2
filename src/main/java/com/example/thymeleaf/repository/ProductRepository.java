package com.example.thymeleaf.repository;

import com.example.thymeleaf.entity.Product;
import com.example.thymeleaf.entity.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByProductType(ProductType productType);

}
