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
    private final Department department;
    private final Position position;
    private final Role role;
    private final String remark;

    @QueryProjection
    public UserDto(Long id,
                   String userId,
                   String name,
                   String email,
                   String phone,
                   Long departmentId,
                   String departmentName,
                   Long positionId,
                   String positionName,
                   Long roleId,
                   String roleName,
                   String remark) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = new Department(departmentId, departmentName);
        this.position = new Position(positionId, positionName);
        this.role = new Role(roleId, roleName);
        this.remark = remark;
    }

    public record Department(Long id, String name) {
    }

    public record Position(Long id, String name) {
    }

    public record Role(Long id, String name) {
    }

}
