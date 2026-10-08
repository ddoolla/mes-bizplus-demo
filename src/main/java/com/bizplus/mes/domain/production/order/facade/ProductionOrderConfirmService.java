package com.bizplus.mes.domain.production.order.facade;

import com.bizplus.mes.domain.lot.LotNoGenerator;
import com.bizplus.mes.domain.lot.LotService;
import com.bizplus.mes.domain.production.order.ProductionOrderService;
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

    private final LotNoGenerator lotNoGenerator;

    @Transactional
    public void confirm(Long id, ProductionOrderConfirmDto dto) {
        // 로트 생성
        Long newLotId = lotService.createLot(
                dto.getItemId(),
                lotNoGenerator.generate("LOT", LocalDate.now())
        );

        // 생산지시 확정 및 로트 추가.
        productionOrderService.confirmProductionOrder(id, newLotId);
    }

}
