package com.bizplus.mes.domain.partner.contact;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactCreateDto;
import com.bizplus.mes.domain.partner.contact.dto.PartnerContactUpdateDto;

public class PartnerContactMapper {

    public static PartnerContact toEntity(Partner partner,
                                          CommonCode department,
                                          CommonCode position,
                                          PartnerContactCreateDto dto) {
        return new PartnerContact(
                partner,
                department,
                position,
                dto.getName(),
                dto.getPhone(),
                dto.getTel(),
                dto.getEmail(),
                dto.getRemark()
        );
    }

    public static void apply(PartnerContact partnerContact,
                             CommonCode department,
                             CommonCode position,
                             PartnerContactUpdateDto dto) {

        partnerContact.update(
                department,
                position,
                dto.getName(),
                dto.getPhone(),
                dto.getTel(),
                dto.getEmail(),
                dto.getRemark(),
                dto.isActive()
        );
    }
}
