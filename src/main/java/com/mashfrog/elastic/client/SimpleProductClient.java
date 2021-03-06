package com.mashfrog.elastic.client;

import com.mashfrog.elastic.document.Product;
import com.mashfrog.elastic.document.Product.documentField;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
public class SimpleProductClient implements ProductClientInterface {

  @Value("${ELASTICSEARCH_INDEX:mashfrog_training_product}")
  private String MASHFROG_TRAINING_PRODUCT;

  private IndexCoordinates getCoordinates() {
    return IndexCoordinates.of(MASHFROG_TRAINING_PRODUCT);
  }

  private final ElasticsearchRestTemplate elasticsearchRestTemplate;

  @Override
  public SearchScrollHits<Product> startScroll(Pageable pageable) {
    return getSearchHits(
        new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.matchAllQuery())
            .build()
            .setPageable(pageable));
  }

  @Override
  public SearchScrollHits<Product> continueScroll(String scrollId) {
    return elasticsearchRestTemplate.searchScrollContinue(
        scrollId, SCROLL_MS, Product.class, getCoordinates());
  }

  @Override
  public SearchScrollHits<Product> scrollBy(documentField field, String query) {
    return getSearchHits(
        new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.matchQuery(field.toLowerCase(), query))
            .build());
  }

  private SearchScrollHits<Product> getSearchHits(Query query) {
    return elasticsearchRestTemplate.searchScrollStart(
        SCROLL_MS, query, Product.class, getCoordinates());
  }
}
