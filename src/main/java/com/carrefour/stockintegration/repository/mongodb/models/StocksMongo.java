package com.carrefour.stockintegration.repository.mongodb.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StocksMongo {

    private String warehouse;
    private String integrationType;
    private LocalDateTime modificationTime;
}
