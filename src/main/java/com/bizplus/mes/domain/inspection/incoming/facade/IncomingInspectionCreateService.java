package com.bizplus.mes.domain.inspection.incoming.facade;

import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionRepository;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.InspectionType;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspection;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspectionRepository;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionCreateDto;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IncomingInspectionCreateService {

    private final InspectionRepository inspectionRepository;
    private final IncomingInspectionRepository incomingInspectionRepository;

    private final UomReader uomReader;
    private final ReceiptItemReader receiptItemReader;
    private final InspectionSpecReader inspectionSpecReader;

    private final InspectionResultService inspectionResultService;

    @Transactional
    public Long create(IncomingInspectionCreateDto dto) {
        ReceiptItem receiptItem = receiptItemReader.getById(dto.getReceiptItemId());
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(dto.getInspectionSpecId());
        Uom uom = uomReader.getById(dto.getUomId());

        String newInspectionNo = CodeGenerator.generate(
                CodePrefix.INCOMING_INSPECTION,
                dto.getDate(),
                inspectionRepository.findMaxInspectionNo(dto.getDate(), InspectionType.INCOMING)
        );

        // 검사 정보 생성
        Inspection newInspection = inspectionRepository.save(new Inspection(
                inspectionSpec,
                SecurityUtils.getCurrentUser().getUser(),
                uom,
                newInspectionNo,
                InspectionType.INCOMING,
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

        // 검사 + 입고 저장
        return incomingInspectionRepository.save(new IncomingInspection(
                newInspection,
                receiptItem
        )).getId();
    }
}
