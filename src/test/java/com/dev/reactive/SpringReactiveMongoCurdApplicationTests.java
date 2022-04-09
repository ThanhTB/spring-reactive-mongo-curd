package com.dev.reactive;

import com.dev.reactive.controller.ProductController;
import com.dev.reactive.dto.ProductDto;
import com.dev.reactive.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoCurdApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService service;

    @Test
    public void addProductTest() {
        Mono<ProductDto> productDto = Mono.just(new ProductDto("101", "mobile", 1, 1200));
        when(service.saveProduct(productDto)).thenReturn(productDto);

        webTestClient.post()
                .uri("/products")
                .body(productDto, ProductDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getProductsTest() {
        Flux<ProductDto> productDtoFlux = Flux.just(
                new ProductDto("101", "mobile", 1, 1200),
                new ProductDto("102", "TV", 1, 1500)
        );

        when(service.getProducts()).thenReturn(productDtoFlux);

        Flux<ProductDto> responseBody = webTestClient
                .get()
                .uri("/products")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDto("101", "mobile", 1, 1200))
                .expectNext(new ProductDto("102", "TV", 1, 1500))
                .verifyComplete();
    }

    @Test
    public void getProductTest() {
        Mono<ProductDto> productDto = Mono.just(new ProductDto("101", "mobile", 1, 1200));

        when(service.getProductById(any())).thenReturn(productDto);

        Flux<ProductDto> responseBody = webTestClient
                .get()
                .uri("/products/101")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(r -> r.getName().equals("mobile"))
                .verifyComplete();
    }

    @Test
    public void updateProductTest() {
        Mono<ProductDto> productDto = Mono.just(new ProductDto("101", "mobile", 1, 1200));

        when(service.updateProduct(productDto, "101")).thenReturn(productDto);

        webTestClient
                .put()
                .uri("/products/update/101")
                .body(Mono.just(productDto), ProductDto.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void deleteProductTest() {
        Mono<ProductDto> productDto = Mono.just(new ProductDto("101", "mobile", 1, 1200));

        given(service.deleteProduct(any())).willReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/products/delete/101")
                .exchange()
                .expectStatus()
                .isOk();

    }
}
