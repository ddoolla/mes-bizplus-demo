package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.domain.uom.conversion.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UomConversionService {

    UomConversionListDto getUomConversions(UomConversionSearchDto dto, Pageable pageable);

    UomConversionDto getUomConversion(Long id);

    boolean checkDuplication(Long fromUomId, Long toUomId);

    void createUomConversion(UomConversionCreateDto dto);

    void updateUomConversion(Long id, UomConversionUpdateDto dto);

    void deleteUomConversions(List<Long> ids);
}
