package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.domain.inspection.item.dto.InspectionItemDto;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InspectionItemQueryRepository {

    Page<InspectionItemDto> findInspectionItems(InspectionItemSearchDto dto, Pageable pageable);

    Optional<InspectionItemDto> findInspectionItem(Long id);
}
