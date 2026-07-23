package com.bizplus.mes.domain.partner;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartnerReader {

    private final PartnerRepository partnerRepository;

    public Partner getById(Long id) {

        return partnerRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PARTNER_NOT_FOUND, "id: " + id));
    }
}
