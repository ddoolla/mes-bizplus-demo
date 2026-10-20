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
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LOT_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }

    public Lot getByLotNo(String lotNo) {
        return lotRepository.findByNoAndDeletedAtIsNull(lotNo)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LOT_NOT_FOUND,
                        String.format("lotNo: %s", lotNo)
                ));
    }

    public Lot getByIdOrNull(Long id) {
        return id == null
                ? null
                : lotRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.LOT_NOT_FOUND,
                        String.format("id: %d", id)
                ));
    }
}
