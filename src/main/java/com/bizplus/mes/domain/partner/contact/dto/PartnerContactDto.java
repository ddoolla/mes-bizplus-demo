package com.bizplus.mes.domain.partner.contact.dto;

import com.bizplus.mes.common.dto.IdNameDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PartnerContactDto {

    private final Long id;
    private final Long partnerId;
    private final IdNameDto department;
    private final IdNameDto position;
    private final String name;
    private final String phone;
    private final String tel;
    private final String email;
    private final String remark;

    @QueryProjection
    public PartnerContactDto(Long id,
                             Long partnerId,
                             Long departmentId,
                             String departmentName,
                             Long positionId,
                             String positionName,
                             String name,
                             String phone,
                             String tel,
                             String email,
                             String remark) {
        this.id = id;
        this.partnerId = partnerId;
        this.department = new IdNameDto(departmentId, departmentName);
        this.position = new IdNameDto(positionId, positionName);
        this.name = name;
        this.phone = phone;
        this.tel = tel;
        this.email = email;
        this.remark = remark;
    }
}
