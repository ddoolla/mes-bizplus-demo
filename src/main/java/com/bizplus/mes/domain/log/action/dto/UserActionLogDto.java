package com.bizplus.mes.domain.log.action.dto;

import com.bizplus.mes.domain.log.action.ActionType;
import com.bizplus.mes.domain.log.action.LogResult;
import com.bizplus.mes.domain.menu.MenuCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserActionLogDto {

    private final Long id;
    private final String userId;
    private final String userName;
    private final MenuCode menu;
    private final ActionType type;
    private final LogResult result;
    private final String ipAddress;;
    private final LocalDateTime createdAt;

    @QueryProjection
    public UserActionLogDto(Long id,
                            String userId,
                            String userName,
                            MenuCode menu,
                            ActionType type,
                            LogResult result,
                            String ipAddress,
                            LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.menu = menu;
        this.type = type;
        this.result = result;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }
}
