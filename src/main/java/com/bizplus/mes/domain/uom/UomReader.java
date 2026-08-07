package com.bizplus.mes.domain.uom;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UomReader {

    private final UomRepository uomRepository;

    public Uom getById(Long id) {

        return uomRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.UOM_NOT_FOUND, "id: " + id));
    }
}
