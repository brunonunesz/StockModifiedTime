package com.carrefour.stockintegration.form;

import lombok.Data;

@Data
public class WarehouseModifiedForm {

    private String warehouseCode;
    private String erpCode;
    private String stockLevelModifiedTime;
    private String apiModifiedTime;
    private long modifiedTimeAverage;
}
