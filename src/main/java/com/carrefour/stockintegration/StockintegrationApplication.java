package com.carrefour.stockintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.carrefour.stockintegration.repository.mongodb")
public class StockintegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockintegrationApplication.class, args);
	}

}
