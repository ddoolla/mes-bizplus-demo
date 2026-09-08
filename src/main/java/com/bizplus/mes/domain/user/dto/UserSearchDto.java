package com.bizplus.mes.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSearchDto {

    private String loginId;
    private String name;
    private Long departmentId;
    private Long positionId;
}
