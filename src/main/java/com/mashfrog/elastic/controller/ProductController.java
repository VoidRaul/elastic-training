package com.mashfrog.elastic.controller;

import com.mashfrog.elastic.client.ProductClientInterface;
import com.mashfrog.elastic.document.Product;
import com.mashfrog.elastic.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;
  private final ProductClientInterface productClient;

  @PostMapping
  public String createProduct(@RequestBody Product product) {
    return productRepository.save(product).getId();
  }

  @GetMapping("/{productId}")
  public Product getProduct(@PathVariable String productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
  }

  @GetMapping
  public Page<Product> getProductsPage(@PageableDefault Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @GetMapping("/scroll")
  public SearchScrollHits<Product> startScrollAll(@PageableDefault Pageable pageable) {
    return productClient.startScroll(pageable);
  }

  @GetMapping("/scroll/{scrollId}")
  public SearchScrollHits<Product> continueScrollAll(@PathVariable String scrollId) {
    return productClient.continueScroll(scrollId);
  }

  @GetMapping("/search")
  public SearchScrollHits<Product> findAllProductsBy(
      @RequestParam String queryString,
      @RequestParam(defaultValue = "NAME") Product.documentField field) {
    return productClient.scrollBy(field, queryString);
  }
}
