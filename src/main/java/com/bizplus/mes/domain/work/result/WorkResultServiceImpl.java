package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import com.bizplus.mes.domain.work.order.dto.WorkStopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkResultServiceImpl implements WorkResultService {

    private final WorkResultRepository workResultRepository;

    private final WorkOrderReader workOrderReader;
    private final WorkResultReader workResultReader;

    @Override
    public void createWorkResult(Long workOrderId) {
        if (workResultRepository.existsByWorkOrderId(workOrderId)) {
            return;
        }

        WorkOrder workOrder = workOrderReader.getById(workOrderId);

        workResultRepository.save(new WorkResult(
                workOrder,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                null,
                null
        ));
    }

    @Transactional
    @Override
    public void stopWork(Long workOrderId, WorkStopDto dto) {
        WorkResult workResult = workResultReader.getByWorkOrderId(workOrderId);

        workResult.stopWork(
                dto.getProductionQuantity(),
                dto.getGoodQuantity(),
                dto.getDefectQuantity()
        );
    }
}
