package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.domain.defect.item.dto.DefectItemDto;
import com.bizplus.mes.domain.defect.item.dto.DefectItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DefectItemQueryRepository {

    Page<DefectItemDto> findDefectItems(DefectItemSearchDto dto, Pageable pageable);

    Optional<DefectItemDto> findDefectItem(Long id);
}
