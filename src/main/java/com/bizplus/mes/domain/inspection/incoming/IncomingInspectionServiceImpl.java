package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.inspection.*;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IncomingInspectionServiceImpl implements IncomingInspectionService {

    private final InspectionRepository inspectionRepository;
    private final IncomingInspectionRepository incomingInspectionRepository;

    private final InspectionSpecReader inspectionSpecReader;
    private final ReceiptItemReader receiptItemReader;

    @Transactional
    @Override
    public Long autoCreate(Long receiptItemId, Long inspectionSpecId, LocalDate date) {
        ReceiptItem receiptItem = receiptItemReader.getById(receiptItemId);
        InspectionSpec inspectionSpec = inspectionSpecReader.getById(inspectionSpecId);
        User inspector = SecurityUtils.getCurrentUser().getUser();

        String newInspectionNo = CodeGenerator.generate(
                CodePrefix.RECEIPT,
                date,
                inspectionRepository.findMaxInspectionNo(date, InspectionType.INCOMING)
        );

        // 검사 정보 생성
        Inspection newInspection = inspectionRepository.save(new Inspection(
                inspectionSpec,
                inspector,
                newInspectionNo,
                InspectionType.INCOMING,
                InspectionStatus.COMPLETED,
                InspectionJudgement.PASS,
                date,
                null
        ));

        // 입고검사 정보 생성 (검사 정보 + 입고 정보)
        incomingInspectionRepository.save(new IncomingInspection(
                newInspection,
                receiptItem
        ));

        return newInspection.getId();
    }
}
