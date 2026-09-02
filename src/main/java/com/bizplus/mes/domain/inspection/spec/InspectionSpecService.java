package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.domain.inspection.spec.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InspectionSpecService {

    InspectionSpecListDto getInspectionSpecs(InspectionSpecSearchDto dto, Pageable pageable);

    InspectionSpecDto getInspectionSpec(Long id);

    boolean checkInspectionSpecCode(Long id, String code);

    Long createInspectionSpec(InspectionSpecCreateDto dto);

    void updateInspectionSpec(Long id, InspectionSpecUpdateDto dto);

    void deleteInspectionSpecs(List<Long> ids);
}
