package com.bizplus.mes.domain.worker.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WorkerDto {

    private final Long id;
    private final String code;
    private final String remark;
    private final UserInfo user;

    @QueryProjection
    public WorkerDto(Long id,
                     String code,
                     String remark,
                     Long userId,
                     String userName,
                     String department,
                     String position,
                     String phone,
                     String email) {
        this.id = id;
        this.code = code;
        this.remark = remark;
        this.user = new UserInfo(
                userId,
                userName,
                department,
                position,
                phone,
                email);
    }

    public record UserInfo(
            Long id,
            String name,
            String department,
            String position,
            String phone,
            String email
    ) {
    }

}
