package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.LotNoGenerator;
import com.bizplus.mes.domain.lot.LotService;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.production.order.ProductionOrderReader;
import com.bizplus.mes.domain.production.order.ProductionOrderService;
import com.bizplus.mes.domain.production.order.ProductionOrderStatus;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderConfirmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductionOrderConfirmService {

    private final ProductionOrderService productionOrderService;
    private final LotService lotService;

    private final ProductionOrderReader productionOrderReader;

    private final LotNoGenerator lotNoGenerator;

    @Transactional
    public void confirm(Long id, ProductionOrderConfirmDto dto) {
        ProductionOrder productionOrder = productionOrderReader.getForUpdate(id);

        if (productionOrder.getStatus() != ProductionOrderStatus.DRAFT) {
            throw new IllegalStateException("생산지시 작성중 상태에서만 확정 가능합니다. id: " + id);
        }

        Item item = productionOrder.getItem();

        Long lotId = null;

        if (item.isLotManaged()) {
            lotId = lotService.createLot(
                    dto.getItemId(),
                    lotNoGenerator.generate("LOT", LocalDate.now())
            );
        }

        // 생산지시 확정 및 로트 추가.
        productionOrderService.confirmProductionOrder(id, lotId);
    }
}
