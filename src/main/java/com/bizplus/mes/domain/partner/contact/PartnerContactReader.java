package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartnerContactReader {

    private final PartnerContactRepository partnerContactRepository;

    public PartnerContact getById(Long id) {

        return partnerContactRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.PARTNER_CONTACT_NOT_FOUND, "id: " + id));
    }
}
