package com.bizplus.mes.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserDto {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;
    private final String phone;
    private final String department;
    private final String position;
    private final String role;
    private final String remark;

    @QueryProjection
    public UserDto(Long id,
                   String userId,
                   String name,
                   String email,
                   String phone,
                   String department,
                   String position,
                   String role,
                   String remark) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
        this.role = role;
        this.remark = remark;
    }
}
