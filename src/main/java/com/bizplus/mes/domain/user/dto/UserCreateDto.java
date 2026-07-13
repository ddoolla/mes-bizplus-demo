package com.bizplus.mes.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateDto {

    @NotNull
    private Long roleId;

    private Long departmentId;

    private Long positionId;

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private String email;

    private String phone;

    private String remark;
}
