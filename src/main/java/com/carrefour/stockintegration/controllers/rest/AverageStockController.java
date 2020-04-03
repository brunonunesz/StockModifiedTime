package com.carrefour.stockintegration.controllers.rest;

import com.carrefour.stockintegration.dto.OrdersCode;
import com.carrefour.stockintegration.form.ProductModifiedTimeAverageForm;
import com.carrefour.stockintegration.form.WarehouseModifiedForm;
import com.carrefour.stockintegration.repository.mongodb.ProductsRepository;
import com.carrefour.stockintegration.repository.mongodb.models.ProductsMongo;
import com.carrefour.stockintegration.repository.mongodb.models.StocksMongo;
import com.carrefour.stockintegration.repository.oracle.models.ProductStock;
import com.carrefour.stockintegration.repository.oracle.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/average")
@Slf4j
public class AverageStockController {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ProductsRepository productsMongoRepo;

    @PostMapping("/stock")
    public ResponseEntity<List<ProductModifiedTimeAverageForm>> stock(@RequestBody List<OrdersCode> ordersCode) {

        log.info("Orders code {}", ordersCode);

        List<ProductModifiedTimeAverageForm> productMTA = new ArrayList<>();

        ordersCode.forEach(o -> {
            List<ProductStock> product = productRepository.getProductByCode(o.getCode());
            Optional<ProductsMongo> productsMongo = productsMongoRepo.findById(o.getCode());

            if (!productsMongo.isPresent()) {
                return;
            }

            ProductModifiedTimeAverageForm pmta = new ProductModifiedTimeAverageForm();
            List<WarehouseModifiedForm> warehouseModifiedForms = new ArrayList<>();

            product.forEach(p -> {

                Optional<StocksMongo> stock = productsMongo.get().getStocks().stream().filter(s -> s.getWarehouse().equalsIgnoreCase(p.getWarehouseCode())).findFirst();

                stock.ifPresent(stocksMongo -> {
                    Duration between = Duration.between(p.getModifiedTime(), stocksMongo.getModificationTime());

                    log.info("[MODIFIED-TIME-HYBRIS-TO-API] Product {}, warehouse {}, erp_code {}, between {} seconds", p.getProductCode(), p.getWarehouseCode(), p.getErpCode(), between.getSeconds());

                    convertTo(warehouseModifiedForms, p, stocksMongo, between);

                    if (Objects.isNull(pmta.getProductCode())) {
                        pmta.setProductCode(p.getProductCode());
                    }
                });
            });

            pmta.setWarehouseModified(warehouseModifiedForms);
            productMTA.add(pmta);
        });

        return ResponseEntity.ok(productMTA);
    }

    private void convertTo(List<WarehouseModifiedForm> warehouseModifiedForms, ProductStock p, StocksMongo stocksMongo, Duration between) {
        WarehouseModifiedForm warehouseModifiedForm = new WarehouseModifiedForm();
        warehouseModifiedForm.setModifiedTimeAverage(between.getSeconds());
        warehouseModifiedForm.setWarehouseCode(p.getWarehouseCode());
        warehouseModifiedForm.setErpCode(p.getErpCode());
        warehouseModifiedForm.setStockLevelModifiedTime(p.getModifiedTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        warehouseModifiedForm.setApiModifiedTime(stocksMongo.getModificationTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        warehouseModifiedForms.add(warehouseModifiedForm);
    }
}
