package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemCreateDto;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemDto;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemUpdateDto;

import java.util.List;

public interface InspectionSpecItemService {

    List<InspectionSpecItemDto> getInspectionSpecItems(Long inspectionSpecId);

    void createInspectionSpecItems(Long inspectionSpecId, InspectionSpecItemCreateDto dto);

    void updateInspectionSpecItems(List<InspectionSpecItemUpdateDto> dtoList);

    void deleteInspectionSpecItems(List<Long> ids);
}
