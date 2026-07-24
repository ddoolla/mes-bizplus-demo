package com.bizplus.mes.domain.partner.contact.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerContactUpdateDto {

    private Long departmentId;
    private Long positionId;

    @NotBlank
    private String name;
    private String phone;
    private String tel;
    private String email;
    private String remark;
    private boolean active;
}
