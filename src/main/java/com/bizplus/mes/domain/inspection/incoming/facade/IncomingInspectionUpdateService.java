package com.bizplus.mes.domain.inspection.incoming.facade;

import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspection;
import com.bizplus.mes.domain.inspection.incoming.IncomingInspectionReader;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionUpdateDto;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IncomingInspectionUpdateService {

    private final IncomingInspectionReader incomingInspectionReader;
    private final InspectionResultService inspectionResultService;

    @Transactional
    public void update(Long id, IncomingInspectionUpdateDto dto) {
        IncomingInspection incomingInspection = incomingInspectionReader.getById(id);
        Inspection inspection = incomingInspection.getInspection();

        // 완료 상태일 경우 비고만 수정 가능
//        if (inspection.getStatus() == InspectionStatus.COMPLETED) {
//            inspection.updateRemark(dto.getRemark());
//            return;
//        }

        // 검사 정보 수정
        inspection.update(
                dto.getDate(),
                dto.getInspectedQuantity(),
                dto.getRemark()
        );

        // 검사항목 결과 수정
        inspectionResultService.updateAll(dto.getInspectionResults());
    }
}
