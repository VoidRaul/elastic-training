package com.mashfrog.elastic.repository;

import com.mashfrog.elastic.document.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

}
