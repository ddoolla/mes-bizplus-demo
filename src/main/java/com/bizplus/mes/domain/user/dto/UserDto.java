package com.bizplus.mes.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
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

    @QueryProjection
    public UserDto(Long id,
                   String userId,
                   String name,
                   String email,
                   String phone,
                   String department,
                   String position) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
    }
}
