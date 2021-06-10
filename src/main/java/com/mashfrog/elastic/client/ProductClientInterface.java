package com.mashfrog.elastic.client;

import com.mashfrog.elastic.document.Product;
import com.mashfrog.elastic.document.Product.documentField;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchScrollHits;

public interface ProductClientInterface {
  int SCROLL_MS = 600_000;

  SearchScrollHits<Product> startScroll(Pageable pageable);

  SearchScrollHits<Product> continueScroll(String scrollId);

  SearchScrollHits<Product> scrollBy(String productName,
      documentField field);
}
