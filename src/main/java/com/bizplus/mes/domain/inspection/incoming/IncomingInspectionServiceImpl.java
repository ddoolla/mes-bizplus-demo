package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionReader;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import com.bizplus.mes.domain.receipt.item.ReceiptItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomingInspectionServiceImpl implements IncomingInspectionService {

    private final IncomingInspectionRepository incomingInspectionRepository;

    private final InspectionReader inspectionReader;
    private final ReceiptItemReader receiptItemReader;

    @Override
    public void create(Long inspectionId, Long receiptItemId) {
        Inspection inspection = inspectionReader.getById(inspectionId);
        ReceiptItem receiptItem = receiptItemReader.getById(receiptItemId);

        incomingInspectionRepository.save(new IncomingInspection(
                inspection,
                receiptItem
        ));
    }
}
