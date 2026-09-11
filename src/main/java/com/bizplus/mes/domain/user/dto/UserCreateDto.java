package com.bizplus.mes.domain.user.dto;

import jakarta.validation.constraints.*;
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
    @Size(min = 4, max = 20)
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "아이디는 4~20자의 영문과 숫자만 사용할 수 있습니다."
    )
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 64)
    private String password;

    @NotBlank
    private String name;

    @Email
    private String email;
    private String phone;
    private String remark;
}
