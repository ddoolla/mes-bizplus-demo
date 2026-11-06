package com.bizplus.mes.domain.inspection.inprocess.facade;

import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionStatus;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspection;
import com.bizplus.mes.domain.inspection.inprocess.InProcessInspectionReader;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionUpdateDto;
import com.bizplus.mes.domain.inspection.result.InspectionResultService;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InProcessInspectionUpdateService {

    private final UomReader uomReader;
    private final InProcessInspectionReader inProcessInspectionReader;

    private final InspectionResultService inspectionResultService;

    @Transactional
    public void update(Long id, InProcessInspectionUpdateDto dto) {
        InProcessInspection inProcessInspection = inProcessInspectionReader.getById(id);
        Inspection inspection = inProcessInspection.getInspection();

        // 완료 상태일 경우 비고만 수정 가능
        if (inspection.getStatus() == InspectionStatus.COMPLETED) {
            inspection.updateRemark(dto.getRemark());
            return;
        }

        Uom uom = uomReader.getById(dto.getUomId());

        // 검사 정보 수정
        inspection.update(
                uom,
                dto.getDate(),
                dto.getInspectedQuantity(),
                dto.getRemark()
        );

        // 검사항목 결과 수정
        inspectionResultService.updateAll(dto.getInspectionResults());
    }
}
