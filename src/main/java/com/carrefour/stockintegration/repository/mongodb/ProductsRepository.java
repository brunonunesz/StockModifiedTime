package com.carrefour.stockintegration.repository.mongodb;

import com.carrefour.stockintegration.repository.mongodb.models.ProductsMongo;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "products")
public interface ProductsRepository extends MongoRepository<ProductsMongo, String> {

}
