package com.bizplus.mes.domain.user.dto;

import lombok.Getter;

@Getter
public class UserSearchDto {

    private String userId;
    private String name;

    // todo 직급, 부서, 권한 등 나중에 추가.
}
