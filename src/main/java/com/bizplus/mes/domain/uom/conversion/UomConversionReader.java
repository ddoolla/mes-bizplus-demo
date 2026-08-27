package com.bizplus.mes.domain.uom.conversion;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UomConversionReader {

    private final UomConversionRepository uomConversionRepository;

    public UomConversion getById(Long id) {
        return uomConversionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.UOM_CONVERSION_NOT_FOUND, "id: " + id));
    }
}
