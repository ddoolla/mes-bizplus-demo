package com.bizplus.mes.domain.lot;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LotReader {

    private final LotRepository lotRepository;

    public Lot getById(Long id) {
        return lotRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.LOT_NOT_FOUND, "id: " + id));
    }
}
