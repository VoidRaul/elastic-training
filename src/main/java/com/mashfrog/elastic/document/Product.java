package com.mashfrog.elastic.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@Document(indexName = "mashfrog_training_product")
@Mapping(mappingPath = "/elasticsearch/product/product_mapping.json")
@Setting(settingPath = "/elasticsearch/product/product_settings.json")
public class Product {

  public enum documentField {
    NAME, DESCRIPTION, PRICE;

    public String toLowerCase() {
      return this.name().toLowerCase();
    }
  }

  @Id
  @JsonProperty("_id")
  private String id;

  @NotBlank
  @Field(type = FieldType.Text)
  @JsonProperty("name")
  private String name;

  @Field(type = FieldType.Text)
  @JsonProperty("description")
  private String description;

  @NotNull
  @Field(type = FieldType.Float)
  @JsonProperty("price")
  private Float price;

  @Field(type = FieldType.Keyword)
  @JsonProperty("manufacturer")
  private String manufacturer;

}
