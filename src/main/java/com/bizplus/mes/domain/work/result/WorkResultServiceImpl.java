package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import com.bizplus.mes.domain.work.result.dto.WorkResultDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultListDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultSearchDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public WorkResultListDto getAll(WorkResultSearchDto dto, Pageable pageable) {
        Page<WorkResultDto> page = workResultRepository.fetchAll(dto, pageable);

        return new WorkResultListDto(page.getContent(), Pagination.of(page));
    }

    @Override
    public void create(Long workOrderId) {
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
    public void update(Long workOrderId, WorkResultUpdateDto dto) {
        WorkResult workResult = workResultReader.getByWorkOrderId(workOrderId);

        workResult.update(
                dto.getProductionQuantity(),
                dto.getGoodQuantity(),
                dto.getDefectQuantity(),
                dto.getCompletedAt(),
                dto.getRemark()
        );
    }
}
