package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.domain.inspection.item.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InspectionItemService {

    InspectionItemListDto getInspectionItems(InspectionItemSearchDto dto, Pageable pageable);

    InspectionItemDto getInspectionItem(Long id);

    boolean checkInspectionItemCode(Long id, String code);

    void createInspectionItem(InspectionItemCreateDto dto);

    void updateInspectionItem(Long id, InspectionItemUpdateDto dto);

    void deleteInspectionItems(List<Long> ids);
}
