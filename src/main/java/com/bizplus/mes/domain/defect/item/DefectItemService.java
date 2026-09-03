package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.domain.defect.item.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DefectItemService {

    DefectItemListDto getDefectItems(DefectItemSearchDto dto, Pageable pageable);

    DefectItemDto getDefectItem(Long id);

    boolean checkDefectItemCode(Long id, String code);

    void createDefectItem(DefectItemCreateDto dto);

    void updateDefectItem(Long id, DefectItemUpdateDto dto);

    void deleteDefectItems(List<Long> ids);
}
