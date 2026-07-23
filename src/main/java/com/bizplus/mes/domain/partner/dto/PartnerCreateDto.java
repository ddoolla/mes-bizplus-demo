package com.bizplus.mes.domain.partner.dto;

import com.bizplus.mes.domain.partner.PartnerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerCreateDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
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
