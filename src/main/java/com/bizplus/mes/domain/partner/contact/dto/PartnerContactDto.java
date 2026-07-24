package com.bizplus.mes.domain.partner.contact.dto;

import com.bizplus.mes.common.dto.IdNameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerContactDto {

    private Long id;
    private Long partnerId;
    private IdNameDto department;
    private IdNameDto position;
    private String name;
    private String phone;
    private String tel;
    private String email;
    private String remark;
    private boolean active;

    public PartnerContactDto(Long partnerId,
                             IdNameDto department,
                             IdNameDto position,
                             String name,
                             String phone,
                             String tel,
                             String email,
                             String remark) {
        this.partnerId = partnerId;
        this.department = department;
        this.position = position;
        this.name = name;
        this.phone = phone;
        this.tel = tel;
        this.email = email;
        this.remark = remark;
    }
}
