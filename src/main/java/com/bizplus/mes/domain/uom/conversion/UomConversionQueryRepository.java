package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionDto;
import com.bizplus.mes.domain.uom.conversion.dto.UomConversionSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UomConversionQueryRepository {

    Page<UomConversionDto> findUomConversions(UomConversionSearchDto dto, Pageable pageable);

    Optional<UomConversionDto> findUomConversion(Long id);

    List<ConvertibleUomDto> findConvertibleUoms(Long toUomId);
}
