package com.carrefour.stockintegration.repository.oracle;

import com.carrefour.stockintegration.repository.oracle.models.ProductStock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ProductRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<ProductStock> getProductByCode(String productCode) {
        return jdbcTemplate.query("SELECT " +
                "sl.p_productcode as productCode, w.p_code as warehouseCode, w.p_warehouseerpcode as erpCode, sl.modifiedts as modifiedTime " +
                "FROM " +
                "HYBRISBE.stocklevels sl, " +
                "HYBRISBE.warehouses w " +
                "WHERE " +
                "sl.p_productcode = ? and " +
                "sl.p_warehouse = w.pk and " +
                "w.p_code like 'WH-E%'", new Object[]{productCode}, (rs, num) -> new ProductStock(
                        rs.getString("productCode"),
                        rs.getString("warehouseCode"),
                        rs.getString("erpCode"),
                        rs.getTimestamp("modifiedTime").toLocalDateTime()));
    }

}
