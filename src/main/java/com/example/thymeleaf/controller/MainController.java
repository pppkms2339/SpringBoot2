package com.example.thymeleaf.controller;

import com.example.thymeleaf.entity.Product;
import com.example.thymeleaf.entity.ProductType;
import com.example.thymeleaf.repository.ProductRepository;
import com.example.thymeleaf.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<ProductType> types = productTypeRepository.findAll();
        Map<ProductType, List<Product>> map = new HashMap<>();

        types.forEach(type -> map.put(type, productRepository.findByProductType(type)));

        model.addAttribute("map", map);

        return "index";
    }

    @GetMapping("/product")
    public String product(@RequestParam("id") long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/productTypeList")
    public String productTypeList(Model model) {
        model.addAttribute("list", productTypeRepository.findAll());
        return "productTypeList";
    }

    // Для демонстрации передачи параметров в запросе, сделаем здесь через
    // PathVariable
    @GetMapping("/productTypeList/delete/{productTypeId}")
    public String productTypeListDelete(@PathVariable("productTypeId") long id, Model model) {
        productTypeRepository.deleteById(id);
        model.addAttribute("list", productTypeRepository.findAll());
        return "productTypeList";
    }

    @GetMapping("/productTypeList/add")
    public String productTypeListAdd(Model model) {
        ProductType productType = new ProductType();
        model.addAttribute("productType", productType);
        return "productTypeForm";
    }

}
