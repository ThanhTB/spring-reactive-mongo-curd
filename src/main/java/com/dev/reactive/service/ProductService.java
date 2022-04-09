package com.dev.reactive.service;

import com.dev.reactive.dto.ProductDto;
import com.dev.reactive.repository.ProductRepository;
import com.dev.reactive.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Flux<ProductDto> getProducts() {
        return repository.findAll().map(AppUtil::entityToDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return repository.findById(id).map(AppUtil::entityToDto);
    }

    public Flux<ProductDto> getProductsInRange(double min, double max) {
        return repository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
        return productDto.map(AppUtil::dtoToEntity).flatMap(repository::insert).map(AppUtil::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id) {
        return repository.findById(id)
                .flatMap(dto -> productDto.map(AppUtil::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(repository::save)
                .map(AppUtil::entityToDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return repository.deleteById(id);
    }
}
