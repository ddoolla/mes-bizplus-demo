package com.bizplus.mes.domain.bom;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BomReader {

    private final BomRepository bomRepository;

    public Bom getById(Long id) {
        return bomRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOM_NOT_FOUND, "id: " + id));
    }
}
