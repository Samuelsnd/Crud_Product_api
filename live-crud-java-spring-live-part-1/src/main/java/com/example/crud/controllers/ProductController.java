package com.example.crud.controllers;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProduct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        Product product = new Product(data);
        repository.save(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct requestProduct) {
        Optional<Product> product = repository.findById(requestProduct.id());
        if(product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setName(requestProduct.name());
            newProduct.setPrice_in_cents(requestProduct.price_in_cents());
            return ResponseEntity.ok(newProduct);
        }
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable String id){
        Optional<Product> product = repository.findById(id);
        if(product.isPresent()) {
            Product newProduct = product.get();
            newProduct.setActive(false);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.noContent().build();
    }

}
