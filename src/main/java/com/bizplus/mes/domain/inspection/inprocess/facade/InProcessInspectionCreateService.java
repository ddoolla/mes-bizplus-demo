package com.bizplus.mes.domain.inspection.inprocess.facade;

import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionRepository;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.InspectionType;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspection;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspectionRepository;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionCreateDto;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.sequence.CodeGenerator;
import com.bizplus.mes.domain.sequence.CodePrefix;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.work.result.WorkResult;
import com.bizplus.mes.domain.work.result.WorkResultReader;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InProcessInspectionCreateService {

    private final InspectionRepository inspectionRepository;
    private final InProcessInspectionRepository inProcessInspectionRepository;

    private final WorkResultReader workResultReader;
    private final InspectionSpecReader inspectionSpecReader;
    private final UomReader uomReader;

    private final InspectionResultService inspectionResultService;

    private final CodeGenerator codeGenerator;

    @Transactional
    public Long create(InProcessInspectionCreateDto dto) {
        WorkResult workResult = workResultReader.getById(dto.getWorkResultId());
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(dto.getInspectionSpecId());
        Uom uom = uomReader.getById(dto.getUomId());

        String newInspectionNo = codeGenerator.generate(CodePrefix.IN_PROCESS_INSPECTION, dto.getDate());

        // 검사 정보 생성
        Inspection newInspection = inspectionRepository.save(new Inspection(
                inspectionSpec,
                SecurityUtils.getCurrentUser().getUser(),
                uom,
                newInspectionNo,
                InspectionType.IN_PROCESS,
                InspectionStatus.PENDING,
                null,
                dto.getDate(),
                dto.getQuantity(),
                null,
                null,
                dto.getRemark()
        ));

        // 검사항목 기본 합격으로 결과 생성
        inspectionResultService.createAll(
                newInspection.getId(),
                inspectionSpec.getId()
        );

        return inProcessInspectionRepository.save(new InProcessInspection(
                        newInspection,
                        workResult
                ))
                .getId();
    }
}
