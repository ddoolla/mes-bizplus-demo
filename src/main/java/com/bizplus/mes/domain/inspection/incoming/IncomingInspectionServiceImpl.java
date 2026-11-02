package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.common.util.CodeGenerator;
import com.bizplus.mes.common.util.CodePrefix;
import com.bizplus.mes.domain.inspection.*;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionListDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSearchDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSummaryDto;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomingInspectionServiceImpl implements IncomingInspectionService {

    private final InspectionRepository inspectionRepository;
    private final IncomingInspectionRepository incomingInspectionRepository;

    private final InspectionSpecReader inspectionSpecReader;
    private final ReceiptItemReader receiptItemReader;
    private final InspectionReader inspectionReader;
    private final IncomingInspectionReader incomingInspectionReader;

    @Override
    public IncomingInspectionListDto getAll(IncomingInspectionSearchDto dto, Pageable pageable) {
        Page<IncomingInspectionSummaryDto> page = incomingInspectionRepository.fetchAll(dto, pageable);

        return new IncomingInspectionListDto(page.getContent(), Pagination.of(page));
    }

    @Override
    public IncomingInspectionDto getById(Long id) {
        IncomingInspection incomingInspection = incomingInspectionReader.getById(id);

        return new IncomingInspectionDto(
                incomingInspection.getId(),
                inspectionReader.getDtoById(incomingInspection.getInspection().getId()),
                receiptItemReader.getDtoById(incomingInspection.getReceiptItem().getId())
        );
    }

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
                receiptItem.getUom(),
                newInspectionNo,
                InspectionType.INCOMING,
                InspectionStatus.PENDING,
                null,
                date,
                receiptItem.getQuantity(),
                receiptItem.getQuantity(),
                BigDecimal.ZERO,
                null
        ));

        // 입고검사 정보 생성 (검사 정보 + 입고 정보)
        incomingInspectionRepository.save(new IncomingInspection(
                newInspection,
                receiptItem
        ));

        return newInspection.getId();
    }

    @Transactional
    @Override
    public void deleteAll(List<Long> ids) {
        ids.forEach(id -> incomingInspectionReader.getById(id).delete());
    }
}
