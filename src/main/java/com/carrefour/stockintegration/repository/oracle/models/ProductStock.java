package com.carrefour.stockintegration.repository.oracle.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductStock {

    private String productCode;
    private String warehouseCode;
    private String erpCode;
    private LocalDateTime modifiedTime;
}
