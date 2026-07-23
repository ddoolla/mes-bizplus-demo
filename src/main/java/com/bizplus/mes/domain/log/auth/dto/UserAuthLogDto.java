package com.bizplus.mes.domain.log.auth.dto;

import com.bizplus.mes.domain.log.auth.LogoutType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserAuthLogDto {

    private final Long id;
    private final String userId;
    private final String userName;
    private final String ipAddress;
    private final LocalDateTime loginAt;
    private final LocalDateTime logoutAt;
    private final LogoutType logoutType;

    @QueryProjection
    public UserAuthLogDto(Long id,
                          String userId,
                          String userName,
                          String ipAddress,
                          LocalDateTime loginAt,
                          LocalDateTime logoutAt,
                          LogoutType logoutType) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.ipAddress = ipAddress;
        this.loginAt = loginAt;
        this.logoutAt = logoutAt;
        this.logoutType = logoutType;
    }
}
