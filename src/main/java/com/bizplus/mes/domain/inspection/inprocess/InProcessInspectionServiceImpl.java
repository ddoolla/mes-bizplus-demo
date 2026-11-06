package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.inspection.*;
import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionListDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSummaryDto;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.work.result.WorkResult;
import com.bizplus.mes.domain.work.result.WorkResultReader;
import com.bizplus.mes.domain.work.result.dto.WorkResultDto;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InProcessInspectionServiceImpl implements InProcessInspectionService {

    private final InspectionRepository inspectionRepository;
    private final InProcessInspectionRepository inProcessInspectionRepository;

    private final InProcessInspectionReader inProcessInspectionReader;
    private final InspectionSpecReader inspectionSpecReader;
    private final WorkResultReader workResultReader;
    private final InspectionReader inspectionReader;

    @Override
    public InProcessInspectionListDto getAll(InProcessInspectionSearchDto dto, Pageable pageable) {
        Page<InProcessInspectionSummaryDto> page = inProcessInspectionRepository.fetchAllSummaries(dto, pageable);

        return new InProcessInspectionListDto(page.getContent(), Pagination.of(page));
    }

    @Transactional
    @Override
    public InProcessInspectionDto getById(Long id) {
        InProcessInspection inProcessInspection = inProcessInspectionReader.getById(id);
        InspectionDto inspectionDto = inspectionReader.getDtoById(inProcessInspection.getInspection().getId());
        WorkResultDto workResultDto = workResultReader.getDtoById(inProcessInspection.getWorkResult().getId());

        return new InProcessInspectionDto(
                inProcessInspection.getId(),
                inspectionDto,
                workResultDto
        );
    }

    @Transactional
    @Override
    public Long autoCreate(Long workOrderId, Long inspectionSpecId, LocalDate date) {
        WorkResult workResult = workResultReader.getByWorkOrderId(workOrderId);
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(inspectionSpecId);
        User inspector = SecurityUtils.getCurrentUser().getUser();

        ProductionOrder productionOrder = workResult.getWorkOrder().getProductionOrderProcess().getProductionOrder();

        String newInspectionNo = CodeGenerator.generate(
                CodePrefix.IN_PROCESS_INSPECTION,
                date,
                inspectionRepository.findMaxInspectionNo(date, InspectionType.IN_PROCESS)
        );

        // 검사 정보 생성
        Inspection newInspection = inspectionRepository.save(new Inspection(
                inspectionSpec,
                inspector,
                productionOrder.getItem().getUom(),
                newInspectionNo,
                InspectionType.IN_PROCESS,
                InspectionStatus.PENDING,
                null,
                date,
                workResult.getGoodQuantity(),
                workResult.getGoodQuantity(),
                BigDecimal.ZERO,
                null
        ));

        inProcessInspectionRepository.save(new InProcessInspection(
                newInspection,
                workResult
        ));

        return newInspection.getId();
    }
}
