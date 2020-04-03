package com.carrefour.stockintegration.repository.mongodb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Document(collection = "products")
public class ProductsMongo {

    @Id
    private String id;
    private String name;
    private Collection<StocksMongo> stocks;
}
