package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemDto;

import java.util.List;

public interface InspectionSpecItemQueryRepository {

    List<InspectionSpecItemDto> fetchInspectionSpecItems(Long inspectionSpecId);

    List<InspectionSpecItem> findInspectionSpecItems(Long inspectionSpecId);

    Integer findNextSortOrder(Long inspectionSpecId);
}
