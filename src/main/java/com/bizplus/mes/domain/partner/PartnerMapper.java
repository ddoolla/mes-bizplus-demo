package com.bizplus.mes.domain.partner;

import com.bizplus.mes.domain.partner.dto.PartnerCreateDto;
import com.bizplus.mes.domain.partner.dto.PartnerDto;
import com.bizplus.mes.domain.partner.dto.PartnerUpdateDto;

public class PartnerMapper {

    public static PartnerDto toDto(Partner partner) {
        return new PartnerDto(
                partner.getId(),
                partner.getCode(),
                partner.getName(),
                partner.getType(),
                partner.getBusinessNo(),
                partner.getCorporateNo(),
                partner.getCeoName(),
                partner.getTel(),
                partner.getFax(),
                partner.getEmail(),
                partner.getHomePage(),
                partner.getZipCode(),
                partner.getAddress(),
                partner.getAddressDetail(),
                partner.isActive(),
                partner.getRemark()
        );
    }

    public static Partner toEntity(PartnerCreateDto dto) {
        return new Partner(
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getBusinessNo(),
                dto.getCorporateNo(),
                dto.getCeoName(),
                dto.getTel(),
                dto.getFax(),
                dto.getEmail(),
                dto.getHomePage(),
                dto.getZipCode(),
                dto.getAddress(),
                dto.getAddressDetail(),
                dto.getRemark()
        );
    }

    public static void apply(Partner partner, PartnerUpdateDto dto) {

        partner.update(
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getBusinessNo(),
                dto.getCorporateNo(),
                dto.getCeoName(),
                dto.getTel(),
                dto.getFax(),
                dto.getEmail(),
                dto.getHomePage(),
                dto.getZipCode(),
                dto.getAddress(),
                dto.getAddressDetail(),
                dto.isActive(),
                dto.getRemark()
        );
    }
}
