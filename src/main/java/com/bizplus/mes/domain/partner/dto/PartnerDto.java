package com.bizplus.mes.domain.partner.dto;

import com.bizplus.mes.domain.partner.PartnerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerDto {

    private Long id;
    private String code;
    private String name;
    private PartnerType type;
    private String businessNo;
    private String corporateNo;
    private String ceoName;
    private String tel;
    private String fax;
    private String email;
    private String homePage;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String remark;
}
