package com.mashfrog.elastic.scheduler;

import com.mashfrog.elastic.document.Product;
import com.mashfrog.elastic.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreationScheduling {

  private final ProductRepository productRepository;

  private final ArrayList<String> manufacturer =
      new ArrayList<>(List.of("Nike", "Adidas", "Reebok", "New Balance"));

  public void createRandomProduct() {
    Product product = new Product();

    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString =
        random
            .ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    product.setName(generatedString);

    generatedString =
        random
            .ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength * 3)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    product.setDescription(generatedString);

    product.setPrice(new Random().nextFloat() * 100);

    product.setManufacturer(manufacturer.get(getRandomNumberUsing(0, manufacturer.size())));

    System.out.println(productRepository.save(product).getManufacturer());
  }

  public int getRandomNumberUsing(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min) + min;
  }
}
