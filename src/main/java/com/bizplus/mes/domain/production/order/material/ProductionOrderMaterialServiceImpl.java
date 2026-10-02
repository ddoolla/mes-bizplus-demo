package com.bizplus.mes.domain.production.order.material;

import com.bizplus.mes.domain.bom.item.BomItem;
import com.bizplus.mes.domain.bom.item.BomItemReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionOrderMaterialServiceImpl implements ProductionOrderMaterialService {

    private final ProductionOrderMaterialRepository productionOrderMaterialRepository;

    private final ItemReader itemReader;
    private final BomItemReader bomItemReader;
    private final ProductionOrderReader productionOrderReader;

    @Transactional
    @Override
    public void createProductionOrderMaterials(Long productionOrderId) {
        ProductionOrder productionOrder = productionOrderReader.getById(productionOrderId);
        List<BomItem> bomItems = bomItemReader.getByBomId(productionOrder.getBom().getId());

        bomItems.forEach(bomItem -> {
            Item item = itemReader.getById(bomItem.getItem().getId());

            productionOrderMaterialRepository.save(new ProductionOrderMaterial(
                    productionOrder,
                    item,
                    item.getUom(),
                    item.getCode(),
                    item.getName(),
                    item.getSpecification(),
                    bomItem.getQuantity(),
                    BigDecimal.ZERO,
                    ConsumptionStatus.PENDING
            ));
        });
    }
}
