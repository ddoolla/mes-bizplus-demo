package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecUpdateDto;
import com.bizplus.mes.domain.inspection.spec.item.InspectionSpecItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InspectionSpecUpdateService {

    private final InspectionSpecService inspectionSpecService;
    private final InspectionSpecItemService inspectionSpecItemService;

    @Transactional
    public void update(Long inspectionSpecId, InspectionSpecUpdateDto dto) {
        inspectionSpecService.updateInspectionSpec(inspectionSpecId, dto);
        inspectionSpecItemService.updateInspectionSpecItems(dto.getInspectionSpecItems());
    }
}
