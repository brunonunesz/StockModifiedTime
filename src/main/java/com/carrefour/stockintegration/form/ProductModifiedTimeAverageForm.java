package com.carrefour.stockintegration.form;

import lombok.Data;

import java.util.List;

@Data
public class ProductModifiedTimeAverageForm {

    private String productCode;
    List<WarehouseModifiedForm> warehouseModified;
}
