package com.dev.reactive.controller;

import com.dev.reactive.dto.ProductDto;
import com.dev.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public Flux<ProductDto> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProductById(@PathVariable String id) {
        return service.getProductById(id);
    }

    @GetMapping("/product-price-range")
    public Flux<ProductDto> getProductPriceRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return service.getProductsInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto) {
        return service.saveProduct(productDto);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDto> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDto) {
        return service.updateProduct(productDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return service.deleteProduct(id);
    }
}
