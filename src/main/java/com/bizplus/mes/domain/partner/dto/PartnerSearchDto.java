package com.bizplus.mes.domain.partner.dto;

import com.bizplus.mes.domain.partner.PartnerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerSearchDto {

    private String code;
    private String name;
    private PartnerType type;
}
