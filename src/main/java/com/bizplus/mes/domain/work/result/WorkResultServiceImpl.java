package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkResultServiceImpl implements WorkResultService {

    private final WorkResultRepository workResultRepository;

    private final WorkOrderReader workOrderReader;

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
}
